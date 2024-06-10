package com.julio.CandyShop.controller;

import com.julio.CandyShop.dto.LoginRequest;
import com.julio.CandyShop.dto.LoginResponse;
import com.julio.CandyShop.entity.RoleEntity;
import com.julio.CandyShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        var user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("user or password invalid! Please try again");
        }

        var now = Instant.now();
        var accessExpiresIn = 300L;
        var refreshExpiresIn = 86400L;

        var scopes = user.get().getRoles().stream().map(RoleEntity::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(accessExpiresIn))
                .claim("scope", scopes)
                .build();

        var refreshClaims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(refreshExpiresIn))
                .claim("scope", "refresh_token")
                .build();

        var accessToken  = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        var refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, accessExpiresIn, refreshExpiresIn));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody String refreshToken) {
        JwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build();

        try {
            var jwt = jwtDecoder.decode(refreshToken);

            if (!"refresh_token".equals(jwt.getClaimAsString("scope"))) {
                throw new BadCredentialsException("Invalid refresh token");
            }

            var now = Instant.now();
            var expiresIn = 300L;

            var claims = JwtClaimsSet.builder()
                    .issuer("mybackend")
                    .subject(jwt.getSubject())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .claim("scope", jwt.getClaimAsString("scope"))
                    .build();

            var newAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return ResponseEntity.ok(new LoginResponse(newAccessToken, null, expiresIn, null));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid refresh token", e);
        }
    }
}
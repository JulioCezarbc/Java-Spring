package br.com.julio.serviceSpring.service;

import br.com.julio.serviceSpring.dto.AcessDTO;
import br.com.julio.serviceSpring.dto.AuthDTO;
import br.com.julio.serviceSpring.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtil;

    public AcessDTO login(AuthDTO authDTO){
        try{
            //Todo Cria mecanismo de credential para o spring
            UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken
                    (authDTO.getUsername(), authDTO.getPassword());

            //Todo Prepara mecanismo para autenticação
            Authentication authentication = authenticationManager.authenticate(userAuth);


            //Todo Busca usuario logado
            UserDetailsImpl userAuthenticate = (UserDetailsImpl) authentication.getPrincipal();

            String token = jwtUtil.generateTokenFromUserDetailsImpl(userAuthenticate);

            AcessDTO acessDTO = new AcessDTO(token);

            return acessDTO;

        }catch (BadCredentialsException e){
            //Todo login ou senha invalida
        }
        return new AcessDTO("Acesso negado");
    }
}

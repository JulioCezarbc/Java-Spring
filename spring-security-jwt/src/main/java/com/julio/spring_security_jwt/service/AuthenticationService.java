package com.julio.spring_security_jwt.service;

import com.julio.spring_security_jwt.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    public String authenticate(Authentication authentication){
        return jwtService.generateToke(authentication);
    }
}

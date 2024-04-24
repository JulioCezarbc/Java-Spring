package br.com.julio.serviceSpring.controller;


import br.com.julio.serviceSpring.dto.AuthDTO;
import br.com.julio.serviceSpring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO){
        return ResponseEntity.ok(authService.login(authDTO));
    }
}

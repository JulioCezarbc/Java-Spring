package br.com.julio.serviceSpring.controller;


import br.com.julio.serviceSpring.dto.AuthDTO;
import br.com.julio.serviceSpring.dto.UsuarioDTO;
import br.com.julio.serviceSpring.service.AuthService;
import br.com.julio.serviceSpring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UsuarioService usuarioService;
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO){
        return ResponseEntity.ok(authService.login(authDTO));
    }
    @PostMapping(value = "/newUser")
    public void createNewUser(@RequestBody UsuarioDTO usuarioDTO){
        usuarioService.createNewUser(usuarioDTO);
    }

    @GetMapping(value = "/verificarCadastro/{uuid}")
    public String verificarCadastro(@PathVariable("uuid") String uuid){
        return usuarioService.verificarCadastro(uuid);
    }

}

package br.com.julio.serviceSpring.controller;


import br.com.julio.serviceSpring.dto.PerfilUsuarioDTO;
import br.com.julio.serviceSpring.service.PerfilUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/perfil-usuario")
@CrossOrigin
public class PerfilUsuarioController {

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;


    @GetMapping
    public List<PerfilUsuarioDTO> findAll(){
        return perfilUsuarioService.findAll();
    }

    @PostMapping
    public void create(@RequestBody PerfilUsuarioDTO perfilUsuarioDTO){
        perfilUsuarioService.create(perfilUsuarioDTO);
    }

    @PutMapping
    public PerfilUsuarioDTO update (@RequestBody PerfilUsuarioDTO perfilUsuarioDTO){
        return perfilUsuarioService.update(perfilUsuarioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        perfilUsuarioService.delete(id);
        return ResponseEntity.ok().build();
    }

}

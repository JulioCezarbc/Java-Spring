package br.com.julio.serviceSpring.controller;


import br.com.julio.serviceSpring.dto.UsuarioDTO;
import br.com.julio.serviceSpring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private UsuarioService service;
    @GetMapping
    public List<UsuarioDTO> findAll(){
        return service.findAll();
    }
    @PostMapping
    public void create(@RequestBody UsuarioDTO usuario){
        service.create(usuario);
    }
    @PutMapping
    public UsuarioDTO update(@RequestBody UsuarioDTO usuario){
        return service.update(usuario);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}

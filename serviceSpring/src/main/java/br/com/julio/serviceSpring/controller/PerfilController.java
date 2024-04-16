package br.com.julio.serviceSpring.controller;

import br.com.julio.serviceSpring.dto.PerfilDTO;
import br.com.julio.serviceSpring.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/perfil")
@CrossOrigin
public class PerfilController {
    @Autowired
    private PerfilService perfilService;


    @GetMapping
    public List<PerfilDTO> findAll(){
        return perfilService.findAll();
    }
    @PostMapping
    public void create(@RequestBody PerfilDTO perfilDTO){
        perfilService.create(perfilDTO);
    }

    @PutMapping
    public PerfilDTO update(@RequestBody PerfilDTO perfilDTO){
        return perfilService.update(perfilDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        perfilService.delete(id);
        return ResponseEntity.ok().build();
    }
}

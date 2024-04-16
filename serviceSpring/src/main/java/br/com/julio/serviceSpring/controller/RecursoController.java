package br.com.julio.serviceSpring.controller;


import br.com.julio.serviceSpring.dto.RecursoDTO;
import br.com.julio.serviceSpring.service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/recursos")
@CrossOrigin
public class RecursoController {
    @Autowired
    private RecursoService recursoService;

    @GetMapping
    public List<RecursoDTO> findAll(){
        return recursoService.findAll();
    }

    @PostMapping
    public void create(@RequestBody RecursoDTO recursoDTO){
        recursoService.create(recursoDTO);
    }

    @PutMapping
    public RecursoDTO update(@RequestBody RecursoDTO recursoDTO){
        return recursoService.update(recursoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        recursoService.delete(id);
        return ResponseEntity.ok().build();
    }
}

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
    public List<RecursoDTO> listarTodos(){
        return recursoService.listarTodos();
    }

    @PostMapping
    public void inserir(@RequestBody RecursoDTO recursoDTO){
        recursoService.inserir(recursoDTO);
    }

    @PutMapping
    public RecursoDTO alterar(@RequestBody RecursoDTO recursoDTO){
        return recursoService.alterar(recursoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        recursoService.excluir(id);
        return ResponseEntity.ok().build();
    }
}

package br.com.julio.serviceSpring.controller;

import br.com.julio.serviceSpring.dto.PermissaoPRDTO;
import br.com.julio.serviceSpring.service.PermissaoPRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/permissao-perfil-recurso")
@CrossOrigin
public class PermissaoPRController {

    @Autowired
    private PermissaoPRService permissaoPerfilRecursoService;

    @GetMapping
    public List<PermissaoPRDTO> findAll(){
        return permissaoPerfilRecursoService.findAll();
    }

    @PostMapping
    public void create(@RequestBody PermissaoPRDTO permissaoPerfilRecurso) {
        permissaoPerfilRecursoService.create(permissaoPerfilRecurso);
    }

    @PutMapping
    public PermissaoPRDTO update(@RequestBody PermissaoPRDTO permissaoPerfilRecurso) {
        return permissaoPerfilRecursoService.update(permissaoPerfilRecurso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        permissaoPerfilRecursoService.delete(id);
        return ResponseEntity.ok().build();
    }

}

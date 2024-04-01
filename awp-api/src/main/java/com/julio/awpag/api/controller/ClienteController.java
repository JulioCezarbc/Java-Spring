package com.julio.awpag.api.controller;


import com.julio.awpag.domain.Exception.NegocioException;
import com.julio.awpag.domain.model.Cliente;
import com.julio.awpag.domain.repository.ClienteRepository;

import com.julio.awpag.domain.service.CadastroClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;


    @Autowired
    private CadastroClienteService cadastroClienteService;


    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        //if (cliente.isPresent()){
        //            return ResponseEntity.ok(cliente.get());
        //        }
        //        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cliente adicionar(@Valid @RequestBody Cliente cliente){
        return cadastroClienteService.salvar(cliente);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long id, @RequestBody Cliente cliente){
       if(!clienteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
       cliente.setId(id);
       cliente = cadastroClienteService.salvar(cliente);

       return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(!clienteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        cadastroClienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}

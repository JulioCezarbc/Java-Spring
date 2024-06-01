package com.julio.CandyShop.controller;

import com.julio.CandyShop.dto.ClientDTO;
import com.julio.CandyShop.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/client")
@CrossOrigin
public class ClientController {
    @Autowired
    private ClientService clientService;
    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(){
        List<ClientDTO> list = clientService.findAll();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
        ClientDTO client = clientService.findById(id);
        return ResponseEntity.ok().body(client);
    }
    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO client){
        client = clientService.create(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId())
                .toUri();
        return ResponseEntity.created(uri).body(client);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO client){
        client = clientService.update(id,client);
        return ResponseEntity.ok().body(client);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

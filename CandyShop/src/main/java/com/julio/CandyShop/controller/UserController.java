package com.julio.CandyShop.controller;

import com.julio.CandyShop.dto.UserDTO;
import com.julio.CandyShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        List<UserDTO> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO user){
        user = userService.create(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO user){
        user = userService.update(id,user);
        return ResponseEntity.ok().body(user);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

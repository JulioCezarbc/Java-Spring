package com.julio.whorkshop.resources;

import com.julio.whorkshop.DTO.UserDTO;
import com.julio.whorkshop.domain.Post;
import com.julio.whorkshop.domain.User;
import com.julio.whorkshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResources {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        return ResponseEntity.ok().body(service.findById(id));
    }
    @GetMapping(value = "/{id}/posts")
    public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
        User data = service.findById(id);

        return ResponseEntity.ok().body(data.getPosts());
    }
    @PostMapping
    public ResponseEntity<Void> insertUser(@RequestBody UserDTO data){
        service.insertUser(data);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(data.id()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody UserDTO data){
        service.updateUser(id,data);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}

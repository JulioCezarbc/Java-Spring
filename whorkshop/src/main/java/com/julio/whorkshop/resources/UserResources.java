package com.julio.whorkshop.resources;

import com.julio.whorkshop.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResources {


    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        User user = new User("1","ju","ju@gmail.com");
        User user1 = new User("2","juy","juy@gmail.com");
        List<User> list = new ArrayList<>(Arrays.asList(user,user1));
        return ResponseEntity.ok().body(list);
    }



}

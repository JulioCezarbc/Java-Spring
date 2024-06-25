package com.julio.spring_security_jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("private")
public class PrivateController {

    @GetMapping
    private String getMessage(){
        return "Hello from private API controller";
    }




}

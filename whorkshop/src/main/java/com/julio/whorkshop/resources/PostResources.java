package com.julio.whorkshop.resources;

import com.julio.whorkshop.domain.Post;
import com.julio.whorkshop.resources.util.URL;
import com.julio.whorkshop.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostResources {

    @Autowired
    private PostService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id){
        Post post = service.findById(id);
        return ResponseEntity.ok().body(post);
    }

    @GetMapping(value = "/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text){
        text = URL.decodeParam(text);
        List<Post> list = service.findByTitle(text);
        return ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/fullSearch")
    public ResponseEntity<List<Post>> fullSearch(@RequestParam(value = "text", defaultValue = "") String text,
                                                 @RequestParam(value = "min", defaultValue = "") String min,
                                                 @RequestParam(value = "max", defaultValue = "") String max){
        text = URL.decodeParam(text);
        Date minDate = URL.convertData(min, new Date(0L));
        Date maxDate = URL.convertData(max, new Date());

        List<Post> list = service.fullSearch(text, minDate, maxDate);
        return ResponseEntity.ok().body(list);
    }
}

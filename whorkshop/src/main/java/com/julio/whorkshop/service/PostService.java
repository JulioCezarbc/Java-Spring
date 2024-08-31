package com.julio.whorkshop.service;

import com.julio.whorkshop.domain.Post;
import com.julio.whorkshop.repository.PostRepository;
import com.julio.whorkshop.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public Post findById(String id){
        return repository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Post not found"));

    }
    public List<Post> findByTitle(String text){
        return repository.findByTitle(text);
    }

}

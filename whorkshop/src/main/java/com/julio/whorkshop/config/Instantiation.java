package com.julio.whorkshop.config;

import com.julio.whorkshop.DTO.AuthorDTO;
import com.julio.whorkshop.domain.Post;
import com.julio.whorkshop.domain.User;
import com.julio.whorkshop.repository.PostRepository;
import com.julio.whorkshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PostRepository postRepository;
    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        repository.deleteAll();
        postRepository.deleteAll();

        User jay = new User(null, "jay", "jay@gmail.com");
        User juy = new User(null, "juy", "juy@gmail.com");
        User bob = new User(null, "bob","bob@gmail.com");
        repository.saveAll(Arrays.asList(jay,juy,bob));

        Post p1 = new Post(null, sdf.parse("30/08/2024"),"Partiu viagem!","Indo para o interior", new AuthorDTO(jay.getId(), jay.getName()));
        Post p2 = new Post(null, sdf.parse("03/09/2024"),"Bom dia!","Acordei pampa hoje", new AuthorDTO(jay.getId(),jay.getName()));


        postRepository.saveAll(Arrays.asList(p1,p2));

        jay.getPosts().addAll(Arrays.asList(p1,p2));
        repository.save(jay);
    }
}

package com.julio.whorkshop.config;

import com.julio.whorkshop.domain.User;
import com.julio.whorkshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository repository;
    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();
        User jay = new User(null, "jay", "jay@gmail.com");
        User juy = new User(null, "juy", "juy@gmail.com");
        User bob = new User(null, "bob","bob@gmail.com");

        repository.saveAll(Arrays.asList(jay,juy,bob));
    }
}

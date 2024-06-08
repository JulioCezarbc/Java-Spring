package com.julio.CandyShop.controller;

import com.julio.CandyShop.entity.RoleEntity;
import com.julio.CandyShop.entity.UserEntity;
import com.julio.CandyShop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createRoles();
    }

    private void createRoles() {
        for (RoleEntity.Values value : RoleEntity.Values.values()) {
            RoleEntity role = new RoleEntity();
            role.setId(value.getRoleId());
            role.setName(value.name());
            roleRepository.save(role);
        }
    }
}
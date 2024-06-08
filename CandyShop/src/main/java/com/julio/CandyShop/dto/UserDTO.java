package com.julio.CandyShop.dto;

import com.julio.CandyShop.entity.UserEntity;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String username;
    private String password;

    public UserDTO(UserEntity userEntity){
        BeanUtils.copyProperties(userEntity, this);
    }
    public UserDTO(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

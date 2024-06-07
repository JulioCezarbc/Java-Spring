package com.julio.CandyShop.dto;

import com.julio.CandyShop.entity.ProductEntity;
import com.julio.CandyShop.entity.UserEntity;
import org.springframework.beans.BeanUtils;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;

    public UserDTO(UserEntity userEntity){
        BeanUtils.copyProperties(userEntity, this);
    }
    public UserDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

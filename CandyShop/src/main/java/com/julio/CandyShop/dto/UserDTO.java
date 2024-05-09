package com.julio.CandyShop.dto;

import com.julio.CandyShop.entity.UserEntity;
import org.springframework.beans.BeanUtils;

public class UserDTO {

    private Long id;
    private String name;
    private String address;
    public UserDTO(){
    }
    public UserDTO(UserEntity UserEntity){
        BeanUtils.copyProperties(UserEntity, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

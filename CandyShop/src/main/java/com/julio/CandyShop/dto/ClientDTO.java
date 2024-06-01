package com.julio.CandyShop.dto;

import com.julio.CandyShop.entity.ClientEntity;
import org.springframework.beans.BeanUtils;

public class ClientDTO {

    private Long id;
    private String name;
    private String number;
    public ClientDTO(){
    }
    public ClientDTO(ClientEntity ClientEntity){
        BeanUtils.copyProperties(ClientEntity, this);
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

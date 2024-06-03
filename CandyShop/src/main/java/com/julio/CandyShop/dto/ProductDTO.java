package com.julio.CandyShop.dto;

import com.julio.CandyShop.entity.ProductEntity;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private Integer quantity;

    private BigDecimal price;

    public ProductDTO(){
    }

    public ProductDTO(Long id, String name, String description, Integer quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductDTO(ProductEntity ProductEntity){
        BeanUtils.copyProperties(ProductEntity, this);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

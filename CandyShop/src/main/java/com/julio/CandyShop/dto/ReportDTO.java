package com.julio.CandyShop.dto;

import com.julio.CandyShop.entity.ProductEntity;
import com.julio.CandyShop.entity.SaleEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class SaleDTO {

    private Long id;
    private ProductEntity product;
    private Integer quantity;
    private LocalDateTime date;

    public SaleDTO(){
    }

    public SaleDTO(SaleEntity saleEntity){
        BeanUtils.copyProperties(saleEntity, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

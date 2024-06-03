package com.julio.CandyShop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.julio.CandyShop.dto.ProductDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 60)
    private String name;

    @Column(nullable = false)
    @Size(min = 3, max = 120)
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;


    public ProductEntity(){
    }
    public ProductEntity(ProductDTO ProductDTO){
        BeanUtils.copyProperties(ProductDTO, this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

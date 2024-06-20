package com.julio.Oak.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 60)
    private String name;

    @Column(nullable = false)
    @Size(min = 2, max = 120)
    private String description;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private Boolean availability;

    public Product() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2, max = 60) String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 60) String name) {
        this.name = name;
    }

    public @Size(min = 2, max = 120) String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 2, max = 120) String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}

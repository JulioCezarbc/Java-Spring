package com.julio.CandyShop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.julio.CandyShop.dto.PurchaseDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "purchase")
public class PurchaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private ClientEntity client;

    @JoinColumn(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy-HH:mm", timezone = "UTC")
    @CreationTimestamp
    private Instant date;

    @ManyToMany
    @JoinTable(
            name = "purchase_product",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products;

    @ElementCollection
    @CollectionTable(name = "purchase_product_names", joinColumns = @JoinColumn(name = "purchase_id"))
    @Column(name = "product_name")
    private List<String> productNames;


    public PurchaseEntity() {}
    public PurchaseEntity(PurchaseDTO purchaseDTO){
        BeanUtils.copyProperties(purchaseDTO, this);
    }


    public PurchaseEntity(Long id, ClientEntity client, Instant date, List<ProductEntity> products,List<String> productNames) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.products = products;
        this.productNames = productNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

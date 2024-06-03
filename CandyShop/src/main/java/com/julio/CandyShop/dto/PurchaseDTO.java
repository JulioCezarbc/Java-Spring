package com.julio.CandyShop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.julio.CandyShop.entity.PurchaseEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseDTO {

    private Long id;
    private Long idClient;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy-HH:mm", timezone = "UTC")
    private Instant date;
    private List<ProductDTO> products;


    public PurchaseDTO() {}

    public PurchaseDTO(PurchaseEntity purchaseEntity){
        BeanUtils.copyProperties(purchaseEntity, this);
        if (purchaseEntity.getProducts() != null) {
            this.products = purchaseEntity.getProducts().stream()
                    .map(ProductDTO::new)
                    .collect(Collectors.toList());
        }

        if (purchaseEntity.getClient() != null) {
            this.idClient = purchaseEntity.getClient().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}

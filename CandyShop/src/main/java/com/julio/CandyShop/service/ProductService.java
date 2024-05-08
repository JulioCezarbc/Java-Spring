package com.julio.CandyShop.service;


import com.julio.CandyShop.entity.ProductEntity;
import com.julio.CandyShop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> findAll(){
        return productRepository.findAll();
    }
    public ProductEntity findById(Long id){
        Optional<ProductEntity> prod = productRepository.findById(id);
        return prod.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));
    }
    public ProductEntity create (ProductEntity productEntity){
        return productRepository.save(productEntity);
    }
    public ProductEntity update(Long id,ProductEntity productEntity){
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        ("Product not found with ID: " + productEntity.getId()));

        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        product.setQuantity(productEntity.getQuantity());

        return productRepository.save(product);
    }
    public void delete(Long id){
        ProductEntity product = productRepository.findById(id).get();
        productRepository.delete(product);
    }
}

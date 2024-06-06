package com.julio.CandyShop.service;


import com.julio.CandyShop.dto.ProductDTO;
import com.julio.CandyShop.entity.ProductEntity;
import com.julio.CandyShop.entity.PurchaseEntity;
import com.julio.CandyShop.repository.ProductRepository;

import com.julio.CandyShop.repository.PurchaseRepository;
import com.julio.CandyShop.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<ProductDTO> findAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream().map(ProductDTO::new).toList();
    }

    public ProductDTO findById(Long id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        return productOptional
                .map(ProductDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));
    }

    @Transactional
    public ProductDTO create(ProductDTO ProductDTO) {
        ProductEntity product = new ProductEntity(ProductDTO);
        ProductEntity savedProd = productRepository.save(product);
        return new ProductDTO(savedProd);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO ProductDTO) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        ("Product with ID " + id + " not found"));

        product.setName(ProductDTO.getName());
        product.setDescription(ProductDTO.getDescription());
        product.setPrice(ProductDTO.getPrice());
        product.setQuantity(ProductDTO.getQuantity());

        ProductEntity savedProd = productRepository.save(product);
        return new ProductDTO(savedProd);
    }

    @Transactional
    public void delete(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        List<PurchaseEntity> purchases = purchaseRepository.findAll();
        for (PurchaseEntity purchase : purchases) {
            if (purchase.getProducts().contains(product)) {
                purchase.getProducts().remove(product);
                purchase.setProductNames(purchase.getProducts().stream()
                        .map(ProductEntity::getName)
                        .collect(Collectors.toList()));
                purchaseRepository.save(purchase);
            }
        }

        productRepository.delete(product);
    }
}
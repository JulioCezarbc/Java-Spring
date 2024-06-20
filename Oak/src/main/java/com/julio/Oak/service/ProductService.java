package com.julio.Oak.service;

import com.julio.Oak.domain.product.Product;
import com.julio.Oak.domain.product.ProductDTO;
import com.julio.Oak.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.name());
        product.setDescription(productDTO.description());
        product.setValue(productDTO.value());
        product.setAvailability(productDTO.availability());

        product = productRepository.save(product);

        return convertToDTO(product);
    }

    public List<ProductDTO> listProducts() {
        return productRepository.findAllByOrderByValueAsc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getValue(),
                product.getAvailability()
        );
    }
}

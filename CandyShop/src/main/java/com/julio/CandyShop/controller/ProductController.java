package com.julio.CandyShop.controller;

import com.julio.CandyShop.dto.ProductDTO;
import com.julio.CandyShop.entity.ProductEntity;
import com.julio.CandyShop.entity.UserEntity;
import com.julio.CandyShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> list = productService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO prod = productService.findById(id);
        return ResponseEntity.ok().body(prod);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product){
        product = productService.create(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(uri).body(product);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO product ){
        product = productService.update(id,product);
        return ResponseEntity.ok().body(product);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

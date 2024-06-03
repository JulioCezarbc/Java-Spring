package com.julio.CandyShop.controller;

import com.julio.CandyShop.dto.ProductDTO;
import com.julio.CandyShop.dto.PurchaseDTO;
import com.julio.CandyShop.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/purchase")
@CrossOrigin
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> findAll(){
        List<PurchaseDTO> list = purchaseService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PurchaseDTO> findById(@PathVariable Long id){
        PurchaseDTO prod = purchaseService.findById(id);
        return ResponseEntity.ok().body(prod);
    }

    @PostMapping
    public ResponseEntity<PurchaseDTO> create(@RequestBody PurchaseDTO purchase){
        purchase = purchaseService.create(purchase);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(purchase.getId())
                .toUri();
        return ResponseEntity.created(uri).body(purchase);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PurchaseDTO> update(@PathVariable Long id, @RequestBody PurchaseDTO purchase ){
        purchase = purchaseService.update(id,purchase);
        return ResponseEntity.ok().body(purchase);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        purchaseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

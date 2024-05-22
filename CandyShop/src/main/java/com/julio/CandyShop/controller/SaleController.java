package com.julio.CandyShop.controller;

import com.julio.CandyShop.dto.SaleDTO;
import com.julio.CandyShop.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/sale")
@CrossOrigin
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> findAll() {
        List<SaleDTO> list = saleService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable Long id) {
        SaleDTO sale = saleService.findById(id);
        return ResponseEntity.ok().body(sale);
    }
    @PostMapping
    public ResponseEntity<SaleDTO> create(@RequestBody SaleDTO saleDTO) {
        saleDTO = saleService.create(saleDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saleDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(saleDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SaleDTO> update(@PathVariable Long id, @RequestBody SaleDTO saleDTO) {
        saleDTO = saleService.update(id,saleDTO);
        return ResponseEntity.ok().body(saleDTO);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

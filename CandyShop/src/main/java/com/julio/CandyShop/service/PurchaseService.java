// PurchaseService.java
package com.julio.CandyShop.service;

import com.julio.CandyShop.dto.PurchaseDTO;
import com.julio.CandyShop.entity.ClientEntity;
import com.julio.CandyShop.entity.ProductEntity;
import com.julio.CandyShop.entity.PurchaseEntity;
import com.julio.CandyShop.repository.ClientRepository;
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
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<PurchaseDTO> findAll() {
        List<PurchaseEntity> purchaseEntities = purchaseRepository.findAll();
        return purchaseEntities.stream().map(PurchaseDTO::new).toList();
    }

    public PurchaseDTO findById(Long id) {
        Optional<PurchaseEntity> purchaseOptional = purchaseRepository.findById(id);
        return purchaseOptional
                .map(PurchaseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Purchase with ID " + id + " not found"));
    }
    @Transactional
    public List<PurchaseDTO> findByClientId(Long clientId) {
        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with ID " + clientId + " not found"));
        List<PurchaseEntity> purchases = purchaseRepository.findByClient(client);
        return purchases.stream().map(PurchaseDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public PurchaseDTO create(PurchaseDTO purchaseDTO) {
        PurchaseEntity purchaseEntity = new PurchaseEntity(purchaseDTO);

        ClientEntity client = clientRepository.findById(purchaseDTO.getIdClient())
                .orElseThrow(() -> new EntityNotFoundException("Client with ID " + purchaseDTO.getIdClient() + " not found"));

        List<ProductEntity> products = purchaseDTO.getProducts().stream()
                .map(productDTO -> productRepository.findById(productDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productDTO.getId() + " not found")))
                .collect(Collectors.toList());

        purchaseEntity.setProducts(products);

        List<String> productNames = products.stream()
                .map(ProductEntity::getName)
                .collect(Collectors.toList());
        purchaseEntity.setProductNames(productNames);

        purchaseEntity.setClient(client);
        purchaseEntity.setDate(purchaseDTO.getDate());


        PurchaseEntity savedPurchase = purchaseRepository.save(purchaseEntity);
        return new PurchaseDTO(savedPurchase);
    }

    @Transactional
    public PurchaseDTO update(Long id, PurchaseDTO purchaseDTO) {
        PurchaseEntity purchaseEntity = purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase with ID " + id + " not found"));

        ClientEntity client = clientRepository.findById(purchaseDTO.getIdClient())
                .orElseThrow(() -> new EntityNotFoundException("Client with ID " + id + " not found"));

        List<ProductEntity> products = purchaseDTO.getProducts().stream()
                .map(productDTO -> productRepository.findById(productDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productDTO.getId() + " not found")))
                .collect(Collectors.toList());

        purchaseEntity.setProducts(products);

        List<String> productNames = products.stream()
                .map(ProductEntity::getName)
                .collect(Collectors.toList());
        purchaseEntity.setProductNames(productNames);


        purchaseEntity.setClient(client);
        purchaseEntity.setDate(purchaseDTO.getDate());

        PurchaseEntity savedPurchase = purchaseRepository.save(purchaseEntity);
        return new PurchaseDTO(savedPurchase);
    }

    @Transactional
    public void delete(Long id) {
        PurchaseEntity purchaseEntity = purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase with ID " + id + " not found"));
        purchaseRepository.delete(purchaseEntity);
    }



}

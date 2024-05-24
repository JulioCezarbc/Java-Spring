package com.julio.CandyShop.service;

import com.julio.CandyShop.dto.SaleDTO;
import com.julio.CandyShop.entity.SaleEntity;
import com.julio.CandyShop.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public List<SaleDTO> findAll(){
        List<SaleEntity> entities = saleRepository.findAll();
        return entities.stream().map(SaleDTO::new).toList();
    }

    public SaleDTO findById(Long id){
        Optional<SaleEntity> saleOptional = saleRepository.findById(id);
        return saleOptional.map(SaleDTO::new).orElseThrow(() ->
                new EntityNotFoundException("Sale with id " + id + " not found"));
    }

    public SaleDTO create (SaleDTO saleDTO){
        SaleEntity saleEntity = new SaleEntity(saleDTO);
        SaleEntity save = saleRepository.save(saleEntity);
        return new SaleDTO(save);
    }
    public SaleDTO update (Long id, SaleDTO saleDTO){
        SaleEntity sale = saleRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Sale with id " + id + " not found"));

        sale.setProductEntity(saleDTO.getProduct());
        sale.setQuantity(saleDTO.getQuantity());
        sale.setDate(saleDTO.getDate());

        SaleEntity save = saleRepository.save(sale);
        return new SaleDTO(save);
    }
    public void delete (Long id){
        SaleEntity sale = saleRepository.findById(id).get();
        saleRepository.delete(sale);
    }




}

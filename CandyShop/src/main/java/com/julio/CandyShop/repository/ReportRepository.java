package com.julio.CandyShop.repository;

import com.julio.CandyShop.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
}

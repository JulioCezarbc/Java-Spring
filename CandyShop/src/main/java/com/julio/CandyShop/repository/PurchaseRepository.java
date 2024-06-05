package com.julio.CandyShop.repository;

import com.julio.CandyShop.entity.ClientEntity;
import com.julio.CandyShop.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

        List<PurchaseEntity> findByClient(ClientEntity client);

}

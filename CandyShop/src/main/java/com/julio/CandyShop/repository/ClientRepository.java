package com.julio.CandyShop.repository;

import com.julio.CandyShop.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    boolean existsByNumber(String number);

}

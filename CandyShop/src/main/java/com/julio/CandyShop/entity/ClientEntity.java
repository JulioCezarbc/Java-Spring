package com.julio.CandyShop.entity;


import com.julio.CandyShop.dto.ClientDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "client")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 3, max = 60)
    private String name;
    @Column(nullable = false)
    @Size(min = 3, max = 12)
    private String number;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PurchaseEntity> historyPurchase;

    public ClientEntity(){
    }

    public ClientEntity(ClientDTO clientDTO){
        BeanUtils.copyProperties(clientDTO, this);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public @Size(min = 3, max = 12) String getNumber() {
        return number;
    }

    public void setNumber(@Size(min = 3, max = 12) String number) {
        this.number = number;
    }

    public List<PurchaseEntity> getHistoryPurchase() {
        return historyPurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

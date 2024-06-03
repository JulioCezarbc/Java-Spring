package com.julio.CandyShop.dto;

import com.julio.CandyShop.entity.ClientEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {

    private Long id;

    @NotBlank(message = "Por favor insira o nome")
    private String name;
    @NotBlank(message = "Por favor insira seu telefone")
    private String number;

    private List<PurchaseDTO> historyPurchase;



    public ClientDTO(){
    }
    public ClientDTO(ClientEntity clientEntity) {
        BeanUtils.copyProperties(clientEntity, this);
        if (clientEntity.getHistoryPurchase() != null) {
            this.historyPurchase = clientEntity.getHistoryPurchase().stream()
                    .map(PurchaseDTO::new)
                    .collect(Collectors.toList());
        }
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<PurchaseDTO> getHistoryPurchase() {
        return historyPurchase;
    }
    public void setHistoryPurchase(List<PurchaseDTO> historyPurchase) {
        this.historyPurchase = historyPurchase;
    }
}

package com.julio.CandyShop.service;

import com.julio.CandyShop.dto.ClientDTO;
import com.julio.CandyShop.entity.ClientEntity;
import com.julio.CandyShop.repository.ClientRepository;

import com.julio.CandyShop.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientDTO> findAll(){
        List<ClientEntity> clientEntities = clientRepository.findAll();
        return clientEntities.stream().map(ClientDTO::new).toList();
    }

    public ClientDTO findById(Long id){
        Optional<ClientEntity> client = clientRepository.findById(id);
        return client.map(ClientDTO::new).orElseThrow(() -> new EntityNotFoundException("client with ID " + id + " not found"));
    }

    @Transactional
    public ClientDTO create (ClientDTO client){
        ClientEntity clientEntity = new ClientEntity(client);
        ClientEntity savedClient = clientRepository.save(clientEntity);
        return new ClientDTO(savedClient);
    }
    @Transactional
    public ClientDTO update(Long id,ClientDTO clientDTO){
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with ID " + id + " not found"));

    client.setName(clientDTO.getName());
    client.setNumber(clientDTO.getNumber());

    ClientEntity savedClient = clientRepository.save(client);
    return new ClientDTO(savedClient);
    }
    @Transactional
    public void delete (Long id){
        ClientEntity client = clientRepository.findById(id).get();
        clientRepository.delete(client);
    }

}

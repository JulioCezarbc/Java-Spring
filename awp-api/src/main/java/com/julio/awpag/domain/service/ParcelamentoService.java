package com.julio.awpag.domain.service;


import com.julio.awpag.domain.Exception.NegocioException;
import com.julio.awpag.domain.model.Cliente;
import com.julio.awpag.domain.model.Parcelamento;
import com.julio.awpag.domain.repository.ClienteRepository;
import com.julio.awpag.domain.repository.ParcelamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class ParcelamentoService {

    private ParcelamentoRepository parcelamentoRepository;
    private CadastroClienteService cadastroClienteService;

    @Transactional
    public Parcelamento cadastrar(Parcelamento parcelamento){
        if (parcelamento.getId() != null){
            throw new NegocioException("Parcelamento a ser criado nao deve ter codigo! ");
        }

        Cliente cliente = cadastroClienteService.buscar(parcelamento.getCliente().getId());

        parcelamento.setCliente(cliente);
        parcelamento.setDataCriacao(OffsetDateTime.now());

        return parcelamentoRepository.save(parcelamento);
    }

}

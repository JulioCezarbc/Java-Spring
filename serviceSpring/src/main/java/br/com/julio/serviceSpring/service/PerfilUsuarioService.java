package br.com.julio.serviceSpring.service;


import br.com.julio.serviceSpring.dto.PerfilUsuarioDTO;
import br.com.julio.serviceSpring.entity.PerfilUsuarioEntity;
import br.com.julio.serviceSpring.repository.PerfilUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilUsuarioService {
    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;

    public List<PerfilUsuarioDTO> findAll(){
        List<PerfilUsuarioEntity> perfilUsuarioEntities = perfilUsuarioRepository.findAll();
        return perfilUsuarioEntities.stream().map(PerfilUsuarioDTO::new).toList();
    }

    public void create(PerfilUsuarioDTO perfilUsuarioDTO){
        PerfilUsuarioEntity perfilUsuarioEntities = new PerfilUsuarioEntity(perfilUsuarioDTO);
        perfilUsuarioRepository.save(perfilUsuarioEntities);
    }

    public PerfilUsuarioDTO update(PerfilUsuarioDTO perfilUsuarioDTO){
        PerfilUsuarioEntity perfilUsuarioEntities = new PerfilUsuarioEntity(perfilUsuarioDTO);
        return new PerfilUsuarioDTO(perfilUsuarioRepository.save(perfilUsuarioEntities));
    }


    public void delete(Long id){
        PerfilUsuarioEntity perfilUsuarioEntities = perfilUsuarioRepository.findById(id).get();
        perfilUsuarioRepository.delete(perfilUsuarioEntities);
    }

    public PerfilUsuarioDTO findById(Long id){
        return new PerfilUsuarioDTO(perfilUsuarioRepository.findById(id).get());
    }
}

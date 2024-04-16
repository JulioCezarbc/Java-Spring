package br.com.julio.serviceSpring.service;


import br.com.julio.serviceSpring.dto.PerfilDTO;
import br.com.julio.serviceSpring.entity.PerfilEntity;
import br.com.julio.serviceSpring.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public List<PerfilDTO> findAll(){
        List<PerfilEntity> perfilEntities = perfilRepository.findAll();
        return perfilEntities.stream().map(PerfilDTO:: new).toList();
    }

    public void create(PerfilDTO perfilDTO){
        PerfilEntity perfilEntity = new PerfilEntity(perfilDTO);
        perfilRepository.save(perfilEntity);
    }

    public PerfilDTO update(PerfilDTO perfilDTO){
        PerfilEntity perfilEntity = new PerfilEntity(perfilDTO);
        return new PerfilDTO(perfilRepository.save(perfilEntity));
    }

    public void delete(Long id){
        PerfilEntity perfilEntity = perfilRepository.findById(id).get();
        perfilRepository.delete(perfilEntity);
    }

    public PerfilDTO findById(Long id){
        return new PerfilDTO(perfilRepository.findById(id).get());
    }


}

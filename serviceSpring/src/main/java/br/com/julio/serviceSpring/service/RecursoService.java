package br.com.julio.serviceSpring.service;


import br.com.julio.serviceSpring.dto.RecursoDTO;
import br.com.julio.serviceSpring.entity.RecursoEntity;
import br.com.julio.serviceSpring.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecursoService {
    @Autowired
    private RecursoRepository recursoRepository;

    public List<RecursoDTO> findAll(){
        List<RecursoEntity> recursoEntities = recursoRepository.findAll();
        return recursoEntities.stream().map(RecursoDTO::new).toList();
    }
    public void create(RecursoDTO recursoDTO){
        RecursoEntity recursoEntity = new RecursoEntity(recursoDTO);
        recursoRepository.save(recursoEntity);
    }
    public RecursoDTO update(RecursoDTO recursoDTO){
        RecursoEntity recursoEntity = new RecursoEntity(recursoDTO);
        return new RecursoDTO(recursoRepository.save(recursoEntity));
    }

    public void delete(Long id){
        RecursoEntity recursoEntity = recursoRepository.findById(id).get();
        recursoRepository.delete(recursoEntity);
    }
    public RecursoDTO findById(Long id){
        return new RecursoDTO(recursoRepository.findById(id).get());
    }
}

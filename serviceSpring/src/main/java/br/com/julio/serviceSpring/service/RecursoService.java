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

    public List<RecursoDTO> listarTodos(){
        List<RecursoEntity> recursoEntities = recursoRepository.findAll();
        return recursoEntities.stream().map(RecursoDTO::new).toList();
    }
    public void inserir(RecursoDTO recursoDTO){
        RecursoEntity recursoEntity = new RecursoEntity(recursoDTO);
        recursoRepository.save(recursoEntity);
    }
    public RecursoDTO alterar(RecursoDTO recursoDTO){
        RecursoEntity recursoEntity = new RecursoEntity(recursoDTO);
        return new RecursoDTO(recursoRepository.save(recursoEntity));
    }

    public void excluir(Long id){
        RecursoEntity recursoEntity = recursoRepository.findById(id).get();
        recursoRepository.delete(recursoEntity);
    }
    public RecursoDTO buscarPorId(Long id){
        return new RecursoDTO(recursoRepository.findById(id).get());
    }
}

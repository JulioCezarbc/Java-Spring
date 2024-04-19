package br.com.julio.serviceSpring.service;

import br.com.julio.serviceSpring.dto.PermissaoPRDTO;
import br.com.julio.serviceSpring.entity.PermissaoPREntity;
import br.com.julio.serviceSpring.repository.PermissaoPRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissaoPRService {
    @Autowired
    private PermissaoPRRepository PermissaoPRRepository;

    public List<PermissaoPRDTO> findAll(){
        List<PermissaoPREntity> permissaoPerfilRecusos = PermissaoPRRepository.findAll();
        return permissaoPerfilRecusos.stream().map(PermissaoPRDTO::new).toList();
    }

    public void create(PermissaoPRDTO permissaoPerfilRecurso) {
        PermissaoPREntity permissaoPerfilRecursoEntity = new PermissaoPREntity(permissaoPerfilRecurso);
        PermissaoPRRepository.save(permissaoPerfilRecursoEntity);
    }

    public PermissaoPRDTO update(PermissaoPRDTO permissaoPerfilRecurso) {
        PermissaoPREntity permissaoPerfilRecursoEntity = new PermissaoPREntity(permissaoPerfilRecurso);
        return new PermissaoPRDTO(PermissaoPRRepository.save(permissaoPerfilRecursoEntity));
    }

    public void delete(Long id) {
        PermissaoPREntity permissaoPerfilRecurso = PermissaoPRRepository.findById(id).get();
        PermissaoPRRepository.delete(permissaoPerfilRecurso);
    }

    public PermissaoPRDTO findById(Long id) {
        return new PermissaoPRDTO(PermissaoPRRepository.findById(id).get());
    }
}

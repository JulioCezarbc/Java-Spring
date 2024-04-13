package br.com.julio.serviceSpring.service;


import br.com.julio.serviceSpring.dto.UsuarioDTO;
import br.com.julio.serviceSpring.entity.UsuarioEntity;
import br.com.julio.serviceSpring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> findAll(){
        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();
        return usuarioEntities.stream().map(UsuarioDTO::new).toList();
    }

    public void create(UsuarioDTO usuarioDTO){
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuarioDTO);
        usuarioRepository.save(usuarioEntity);
    }

    public UsuarioDTO update(UsuarioDTO usuarioDTO){
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuarioDTO);
        return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
    }

    public void delete(Long id){
        UsuarioEntity usuario = usuarioRepository.findById(id).get();
        usuarioRepository.delete(usuario);
    }

    public UsuarioDTO findById(Long id){
        return new UsuarioDTO(usuarioRepository.findById(id).get());
    }

}

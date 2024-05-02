package br.com.julio.serviceSpring.service;


import br.com.julio.serviceSpring.dto.UsuarioDTO;
import br.com.julio.serviceSpring.entity.UsuarioEntity;
import br.com.julio.serviceSpring.entity.UsuarioVerificadorEntity;
import br.com.julio.serviceSpring.entity.enums.TipoSituacaoUsuario;
import br.com.julio.serviceSpring.repository.UsuarioRepository;
import br.com.julio.serviceSpring.repository.UsuarioVerificadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioVerificadorRepository verificadorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    public List<UsuarioDTO> findAll(){
        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();
        return usuarioEntities.stream().map(UsuarioDTO::new).toList();
    }
    public void create(UsuarioDTO usuarioDTO){
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuarioDTO);
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
        usuarioRepository.save(usuarioEntity);
    }

    public void createNewUser(UsuarioDTO usuarioDTO){
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuarioDTO);
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
        usuarioEntity.setSituacaoUsuario(TipoSituacaoUsuario.PENDENTE);
        usuarioEntity.setId(null);
        usuarioRepository.save(usuarioEntity);

        UsuarioVerificadorEntity verificador = new UsuarioVerificadorEntity();
        verificador.setUsuario(usuarioEntity);
        verificador.setUuid(UUID.randomUUID());
        verificador.setDataExpiracao(Instant.now().plusMillis(900000));
        verificadorRepository.save(verificador);

        emailService.enviarEmailTexto(usuarioDTO.getEmail(), "Novo usuario cadastrado",
                "Voce esta recebendo o email de cadastro, o numero para validação é " +
                        verificador.getUuid());
    }

    public String verificarCadastro(String uuid){
        UsuarioVerificadorEntity usuarioVerif =
                verificadorRepository.findByUuid(UUID.fromString(uuid)).get();

        if (usuarioVerif != null){
            if( usuarioVerif.getDataExpiracao().compareTo(Instant.now()) >= 0){

                UsuarioEntity u = usuarioVerif.getUsuario();
                u.setSituacaoUsuario(TipoSituacaoUsuario.ATIVO);

                usuarioRepository.save(u);

                return "Usuario verificado";
            }else {
                verificadorRepository.delete(usuarioVerif);
                return "Tempo expirado";
            }
        }
        else {
            return "Usuario nao verificado";
        }
    }

    public UsuarioDTO update(UsuarioDTO usuarioDTO){
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuarioDTO);
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
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

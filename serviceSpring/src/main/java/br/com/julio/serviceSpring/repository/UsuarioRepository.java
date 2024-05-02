package br.com.julio.serviceSpring.repository;


import br.com.julio.serviceSpring.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByLogin(String login);
}

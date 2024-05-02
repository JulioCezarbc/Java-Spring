package br.com.julio.serviceSpring.repository;

import br.com.julio.serviceSpring.entity.UsuarioEntity;
import br.com.julio.serviceSpring.entity.UsuarioVerificadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioVerificadorRepository extends JpaRepository<UsuarioVerificadorEntity,Long> {
    public Optional<UsuarioVerificadorEntity> findByUuid(UUID uuid);
}

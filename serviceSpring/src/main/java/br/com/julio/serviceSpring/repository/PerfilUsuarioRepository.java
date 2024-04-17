package br.com.julio.serviceSpring.repository;

import br.com.julio.serviceSpring.entity.PerfilUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuarioEntity, Long> {
}

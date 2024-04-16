package br.com.julio.serviceSpring.repository;

import br.com.julio.serviceSpring.entity.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {
}

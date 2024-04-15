package br.com.julio.serviceSpring.repository;

import br.com.julio.serviceSpring.entity.RecursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecursoRepository extends JpaRepository<RecursoEntity, Long> {
}

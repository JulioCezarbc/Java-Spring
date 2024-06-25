package com.julio.spring_security_jwt.repository;

import com.julio.spring_security_jwt.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,String> {
    Optional<User> findByUsername(String username);
}

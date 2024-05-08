package com.julio.CandyShop.service;

import com.julio.CandyShop.entity.UserEntity;
import com.julio.CandyShop.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public UserEntity findById(Long id){
        Optional<UserEntity> user = userRepository.findById(id);
        return user.orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
    }

    public UserEntity create (UserEntity user){
        return userRepository.save(user);
    }

    public UserEntity update(Long id,UserEntity user){
        UserEntity user1 = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + user.getId() + " not found"));

    user1.setName(user.getName());
    user1.setAddress(user.getAddress());
    return userRepository.save(user1);
    }
    public void delete (Long id){
        UserEntity user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

}

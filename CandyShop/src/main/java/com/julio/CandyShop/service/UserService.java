package com.julio.CandyShop.service;

import com.julio.CandyShop.dto.UserDTO;
import com.julio.CandyShop.entity.UserEntity;
import com.julio.CandyShop.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll(){
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(UserDTO::new).toList();
    }

    public UserDTO findById(Long id){
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(UserDTO::new).orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
    }

    public UserDTO create (UserDTO user){
        UserEntity userEntity = new UserEntity(user);
        UserEntity savedUser = userRepository.save(userEntity);
        return new UserDTO(savedUser);
    }

    public UserDTO update(Long id,UserDTO userDTO){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));

    user.setName(userDTO.getName());
    user.setAddress(userDTO.getAddress());

    UserEntity savedUser = userRepository.save(user);
    return new UserDTO(savedUser);
    }
    public void delete (Long id){
        UserEntity user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

}

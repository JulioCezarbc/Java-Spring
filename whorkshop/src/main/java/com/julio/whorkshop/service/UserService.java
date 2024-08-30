package com.julio.whorkshop.service;

import com.julio.whorkshop.DTO.UserDTO;
import com.julio.whorkshop.domain.User;
import com.julio.whorkshop.repository.UserRepository;
import com.julio.whorkshop.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<UserDTO> findAll(){
        List<User> list = repository.findAll();
        return list.stream().map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail())).toList();
    }
    public UserDTO findById(String id){
        User user = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        return new UserDTO(user.getId(),user.getName(),user.getEmail());
    }

}

package com.julio.CandyShop.service;

import com.julio.CandyShop.dto.UserDTO;
import com.julio.CandyShop.entity.RoleEntity;
import com.julio.CandyShop.entity.UserEntity;
import com.julio.CandyShop.repository.RoleRepository;
import com.julio.CandyShop.repository.UserRepository;
import com.julio.CandyShop.service.exceptions.EntityExistsException;
import com.julio.CandyShop.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {


    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<UserDTO> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).toList();
    }
    public UserDTO findById(UUID id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(UserDTO::new).orElseThrow(() -> new EntityNotFoundException("User with id " + id +  " not found"));
    }

    @Transactional
    public UserDTO create(UserDTO userDTO) {

        RoleEntity basicRole = roleRepository.findByName(RoleEntity.Values.BASIC.name());
        var userFromDb = userRepository.findByUsername(userDTO.getUsername());

        if(userFromDb.isPresent()) {
            throw new EntityExistsException("Username already exists");
        }

        var user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);
        return new UserDTO(user);
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDTO update(UUID id, UserDTO userDTO) {
        RoleEntity basicRole = roleRepository.findByName(RoleEntity.Values.BASIC.name());
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new EntityNotFoundException("User with  id " + id + " not found");
        }
        String name = userDTO.getUsername();
        if (user.get().getUsername().equals(name)) {
            throw new EntityExistsException("Username already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userEntity.setRoles(Set.of(basicRole));

        userRepository.save(userEntity);
        return new UserDTO(userEntity);

    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {
        UUID userId = UUID.nameUUIDFromBytes(id.toString().getBytes());
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user with ID " + id + " not found"));
        userRepository.delete(user);
    }

}

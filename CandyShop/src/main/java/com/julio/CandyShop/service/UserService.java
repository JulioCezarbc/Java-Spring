package com.julio.CandyShop.service;

import com.julio.CandyShop.dto.UserDTO;
import com.julio.CandyShop.entity.RoleEntity;
import com.julio.CandyShop.entity.UserEntity;
import com.julio.CandyShop.repository.RoleRepository;
import com.julio.CandyShop.repository.UserRepository;
import com.julio.CandyShop.service.exceptions.EntityExistsException;
import com.julio.CandyShop.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<UserDTO> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).toList();
    }
    public UserDTO findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(UUID.nameUUIDFromBytes
                (id.toString().getBytes()));
        return user.map(UserDTO::new).orElseThrow(() -> new EntityNotFoundException("User with id " + id + "nao encontrado"));
    }

    @Transactional

    public UserDTO create(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity(userDTO);
        userRepository.save(userEntity);
        return new UserDTO(userEntity);
    }
    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {
        UUID userId = UUID.nameUUIDFromBytes(id.toString().getBytes());
        var basicRole =roleRepository.findByName(RoleEntity.Values.BASIC.name());


        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new EntityNotFoundException("User with  id " + id + " not found");
        }
        String name = userDTO.getUsername();
        if (user.get().getUsername().equals(name)) {
            throw new EntityExistsException("Username already exists");
        }

        String email = userDTO.getEmail();
        if (user.get().getEmail().equals(name)) {
            throw new EntityExistsException("Email already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setRoles(Set.of(basicRole));

        userRepository.save(userEntity);
        return new UserDTO(userEntity);

    }
    @Transactional
//    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public void delete(Long id) {
        UUID userId = UUID.nameUUIDFromBytes(id.toString().getBytes());
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user with ID " + id + " not found"));
        userRepository.delete(user);
    }

}

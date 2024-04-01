package com.julioCord.todosimple.services;

import com.julioCord.todosimple.models.User;
import com.julioCord.todosimple.models.enums.ProfileEnum;
import com.julioCord.todosimple.repositories.UserRepository;
import com.julioCord.todosimple.services.exceptions.DataBindingViolationException;
import com.julioCord.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserService {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado!" +
                " id: " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional
    public User createUser(User obj){
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User updateUser(User obj){
        User newUser = findById(obj.getId());
        newUser.setPassword(obj.getPassword());
        newUser.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newUser);
    }

    public void deleteUser(Long id){
       findById(id);
       try  {
           this.userRepository.deleteById(id);
       }catch (Exception e){
           throw new DataBindingViolationException("Não é possivel excluir, pois há entidades relacionadas! ");
       }
    }
}

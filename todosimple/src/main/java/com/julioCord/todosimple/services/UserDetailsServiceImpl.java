package com.julioCord.todosimple.services;


import com.julioCord.todosimple.models.User;
import com.julioCord.todosimple.repositories.UserRepository;
import com.julioCord.todosimple.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("Usuario nao encontrado " + username);
        }
        return new UserSecurity(user.getId(), user.getUsername(), user.getPassword(),user.getProfiles());


    }
}

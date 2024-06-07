package com.julio.CandyShop.entity;

import com.julio.CandyShop.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    public UserEntity(UserDTO userDTO){
        BeanUtils.copyProperties(userDTO, this);
    }

    public UserEntity(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

//    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
//        return passwordEncoder.matches(loginRequest.password(), this.password);
//    }
}

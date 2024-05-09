package com.julio.CandyShop.entity;


import com.julio.CandyShop.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 3, max = 60)
    private String name;
    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String address;
    public UserEntity(){
    }

    public UserEntity(UserDTO UserDTO){
        BeanUtils.copyProperties(UserDTO, this);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

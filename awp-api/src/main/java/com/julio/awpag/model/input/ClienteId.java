package com.julio.awpag.model.input;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteId {


    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

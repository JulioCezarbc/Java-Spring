package com.julioCord.todosimple.models.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ProfileEnum {

    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");


    private Integer code;
    private String description;

    public static ProfileEnum toEnum(Integer code) {
        if (Objects.isNull(code)) return null;

        for (ProfileEnum xEnum : ProfileEnum.values()){
            if (code.equals(xEnum.getCode())) return xEnum;
        }
        throw new IllegalArgumentException("Invalid code " + code);

    }
}

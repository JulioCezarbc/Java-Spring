package com.julio.Oak.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductDTO(
        @NotBlank
        @Size(min = 2, max = 60)
        String name,

        @NotBlank
        @Size(min = 2, max = 120)
        String description,

        @NotNull
        BigDecimal value,

        @NotNull
        Boolean availability
) {}
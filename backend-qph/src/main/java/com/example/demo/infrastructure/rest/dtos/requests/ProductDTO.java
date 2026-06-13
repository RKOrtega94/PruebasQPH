package com.example.demo.infrastructure.rest.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDTO(
        Long id,
    @NotBlank(message = "El nombre es requerido")
    String nombre,
    @NotNull
    @Positive(message = "El precio debe ser positivo")
        BigDecimal precio,
    @NotNull
    @Min(0)
        Integer stock
) {
}

package com.example.demo.infrastructure.rest.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record UpdateSaleRequest(@NotNull @Positive Long productId, @NotNull @Min(0) Integer quantity,
                                @NotNull LocalDate date) {
}

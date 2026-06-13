package com.example.demo.infrastructure.rest.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterSaleRequest(@NotNull @Positive Long productId, @NotNull @Min(0) Integer quantity) {
}

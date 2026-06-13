package com.example.demo.infrastructure.rest.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleResponse(Long id, String product, String seller, Integer quantity, LocalDate date,
                           BigDecimal total) {
}

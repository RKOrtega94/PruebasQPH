package com.example.demo.data.services.results;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleResult(Long id, String product, String seller, Integer quantity, LocalDate date,
                         BigDecimal total) {
}

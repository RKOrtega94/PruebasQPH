package com.example.demo.data.services.results;

import java.time.LocalDate;

public record SaleResult(Long id, String product, String user, Integer quantity, LocalDate date) {
}

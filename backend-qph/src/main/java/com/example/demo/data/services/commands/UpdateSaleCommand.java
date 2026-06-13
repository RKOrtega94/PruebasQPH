package com.example.demo.data.services.commands;

import java.time.LocalDate;

public record UpdateSaleCommand(Long id, Long productId, Integer quantity, LocalDate date) {
}

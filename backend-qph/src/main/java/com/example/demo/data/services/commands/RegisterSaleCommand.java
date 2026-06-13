package com.example.demo.data.services.commands;

public record RegisterSaleCommand(Long productId, String subject, Integer quantity) {
}

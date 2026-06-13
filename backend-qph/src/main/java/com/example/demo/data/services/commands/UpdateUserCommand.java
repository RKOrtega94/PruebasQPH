package com.example.demo.data.services.commands;

public record UpdateUserCommand(
        Long id,
        String username,
        String password,
        Boolean active
) {
}

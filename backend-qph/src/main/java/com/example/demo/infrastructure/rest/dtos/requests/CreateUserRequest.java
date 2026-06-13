package com.example.demo.infrastructure.rest.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateUserRequest(
        @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be blank")
        @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,
        @NotNull(message = "Password cannot be null")
        @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password
) {
}

package com.example.demo.infrastructure.rest.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdateUserRequest(
        @NotNull @NotEmpty @Length(min = 3, max = 20)
        String username,
        @NotNull @NotEmpty @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password,
        Boolean active
) {
}

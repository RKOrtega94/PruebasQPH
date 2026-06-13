package com.example.demo.infrastructure.rest.mappers;

import com.example.demo.data.services.commands.CreateUserCommand;
import com.example.demo.data.services.commands.UpdateUserCommand;
import com.example.demo.data.services.results.UserResult;
import com.example.demo.infrastructure.rest.dtos.requests.CreateUserRequest;
import com.example.demo.infrastructure.rest.dtos.requests.UpdateUserRequest;
import com.example.demo.infrastructure.rest.dtos.responses.UserResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class UserRestMapper {
    public CreateUserCommand toCommand(@Valid CreateUserRequest request) {
        return new CreateUserCommand(request.username(), request.password());
    }

    public UserResponse toResponse(UserResult result) {
        return new UserResponse(result.id(), result.username(), result.active());
    }

    public UpdateUserCommand toCommand(Long id, UpdateUserRequest request) {
        return new UpdateUserCommand(id, request.username(), request.password(), request.active());
    }
}

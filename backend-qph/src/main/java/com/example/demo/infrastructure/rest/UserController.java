package com.example.demo.infrastructure.rest;

import com.example.demo.data.services.UserService;
import com.example.demo.infrastructure.rest.dtos.requests.CreateUserRequest;
import com.example.demo.infrastructure.rest.dtos.requests.UpdateUserRequest;
import com.example.demo.infrastructure.rest.dtos.responses.UserResponse;
import com.example.demo.infrastructure.rest.mappers.UserRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final UserRestMapper mapper;

    public UserController(UserService service, UserRestMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        var result = service.getAllUsers();
        return ResponseEntity.ok(result.stream().map(mapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        var response = service.getUserById(id);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        var result = service.createUser(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        var result = service.updateUser(mapper.toCommand(id, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.demo.infrastructure.rest.dtos.requests;


import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}

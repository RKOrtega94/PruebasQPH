package com.example.demo.data.services;

import com.example.demo.data.services.commands.CreateUserCommand;
import com.example.demo.data.services.commands.UpdateUserCommand;
import com.example.demo.data.services.results.UserResult;

import java.util.List;

public interface UserService {
    List<UserResult> getAllUsers();

    UserResult getUserById(Long id);

    UserResult updateUser(UpdateUserCommand command);

    UserResult createUser(CreateUserCommand command);

    void deleteUser(Long id);
}

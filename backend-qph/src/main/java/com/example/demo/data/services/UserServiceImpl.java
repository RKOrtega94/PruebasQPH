package com.example.demo.data.services;

import com.example.demo.data.mappers.UserMapper;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.data.services.commands.CreateUserCommand;
import com.example.demo.data.services.commands.UpdateUserCommand;
import com.example.demo.data.services.results.UserResult;
import com.example.demo.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    private static final String USER_NOT_FOUND = "User not found with id: %s";

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<UserResult> getAllUsers() {
        return repository.findAll().stream().map(mapper::toResult).toList();
    }

    @Override
    public UserResult getUserById(Long id) {
        return repository.findById(id).map(mapper::toResult).orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    @Override
    public UserResult updateUser(UpdateUserCommand command) {
        var user = repository.findById(command.id()).orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, command.id())));
        mapper.update(command, user);
        return mapper.toResult(repository.save(user));
    }

    @Override
    public UserResult createUser(CreateUserCommand command) {
        var user = repository.save(mapper.toEntity(command));
        return mapper.toResult(user);
    }

    @Override
    public void deleteUser(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {
            throw new ResourceNotFoundException(String.format(USER_NOT_FOUND, id));
        });
    }
}

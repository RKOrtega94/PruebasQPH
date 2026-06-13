package com.example.demo.data.mappers;

import com.example.demo.data.services.commands.CreateUserCommand;
import com.example.demo.data.services.commands.UpdateUserCommand;
import com.example.demo.data.services.results.UserResult;
import com.example.demo.domain.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder encoder;

    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public UserResult toResult(User user) {
        return new UserResult(user.getId(), user.getUsername(), user.getActive());
    }

    public User toEntity(CreateUserCommand command) {
        return User.builder().username(command.username()).password(encoder.encode(command.password())).build();
    }

    public void update(UpdateUserCommand command, User user) {
        if (command == null) return;
        if (command.username() != null) user.setUsername(command.username());
        if (command.password() != null) user.setPassword(encoder.encode(command.password()));
        if (command.active() != null) user.setActive(command.active());
    }
}

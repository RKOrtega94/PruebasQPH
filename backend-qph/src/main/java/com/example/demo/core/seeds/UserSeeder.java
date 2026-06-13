package com.example.demo.core.seeds;

import com.example.demo.data.repositories.UserRepository;
import com.example.demo.domain.entities.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "seed.users")
public class UserSeeder implements CommandLineRunner {
    private static final Log log = LogFactory.getLog(UserSeeder.class);
    private final UserRepository repository;
    private final PasswordEncoder encoder;


    @Value("${seed.users.username}")
    private String username;

    @Value("${seed.users.password}")
    private String password;

    public UserSeeder(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        repository.findByUsername(username).ifPresentOrElse(
                ignore -> log.info("User already exists"),
                () -> repository.save(User.builder().username(username).password(encoder.encode(password)).build()));
    }
}
package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Builder.Default
    private Boolean active = Boolean.TRUE;
    @OneToMany(mappedBy = "user")
    private Set<Sale> sales = new LinkedHashSet<>();
}
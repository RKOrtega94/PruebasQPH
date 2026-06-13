package com.example.demo.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @Column(nullable = false)
    @NotNull
    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precio;

    @Column(nullable = false)
    @NotNull
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    @OneToMany(mappedBy = "product")
    private Set<Sale> sales = new LinkedHashSet<>();
}

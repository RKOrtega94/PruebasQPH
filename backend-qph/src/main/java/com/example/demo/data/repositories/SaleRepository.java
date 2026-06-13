package com.example.demo.data.repositories;

import com.example.demo.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByUser_Username(String userUsername);
}

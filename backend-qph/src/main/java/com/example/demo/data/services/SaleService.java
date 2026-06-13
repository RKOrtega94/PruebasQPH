package com.example.demo.data.services;

import com.example.demo.data.services.commands.RegisterSaleCommand;
import com.example.demo.data.services.commands.UpdateSaleCommand;
import com.example.demo.data.services.results.SaleResult;

import java.util.List;

public interface SaleService {
    SaleResult create(RegisterSaleCommand command);

    List<SaleResult> getAll();

    List<SaleResult> findBySubject(String subject);

    SaleResult getById(Long id);

    SaleResult update(UpdateSaleCommand command);

    void deleteById(Long id);
}

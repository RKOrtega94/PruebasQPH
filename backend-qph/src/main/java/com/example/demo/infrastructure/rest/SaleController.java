package com.example.demo.infrastructure.rest;

import com.example.demo.data.services.SaleService;
import com.example.demo.infrastructure.rest.dtos.requests.RegisterSaleRequest;
import com.example.demo.infrastructure.rest.dtos.requests.UpdateSaleRequest;
import com.example.demo.infrastructure.rest.dtos.responses.SaleResponse;
import com.example.demo.infrastructure.rest.mappers.SaleRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.core.security.SessionUtils.getSubject;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
    private final SaleService service;
    private final SaleRestMapper mapper;

    public SaleController(SaleService service, SaleRestMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<SaleResponse> registerSale(@RequestBody @Valid RegisterSaleRequest request) {
        var result = service.create(mapper.toCommand(request, getSubject()));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> getAllSales() {
        var response = service.getAll().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<SaleResponse>> getMySales() {
        var result = service.findBySubject(getSubject()).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id) {
        var result = service.getById(id);
        return ResponseEntity.ok(mapper.toResponse(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponse> updateSale(@PathVariable Long id, @RequestBody @Valid UpdateSaleRequest request) {
        var result = service.update(mapper.toCommand(id, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

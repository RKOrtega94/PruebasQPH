package com.example.demo.data.services;

import com.example.demo.data.mappers.SaleMapper;
import com.example.demo.data.repositories.ProductRepository;
import com.example.demo.data.repositories.SaleRepository;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.data.services.commands.RegisterSaleCommand;
import com.example.demo.data.services.commands.UpdateSaleCommand;
import com.example.demo.data.services.results.SaleResult;
import com.example.demo.domain.entities.Product;
import com.example.demo.domain.entities.Sale;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    private final SaleRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleMapper mapper;

    public SaleServiceImpl(SaleRepository repository, UserRepository userRepository, ProductRepository productRepository, SaleMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public SaleResult create(RegisterSaleCommand command) {
        var user = userRepository.findByUsername(command.subject())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        var sale = Sale.builder()
                .product(product)
                .user(user)
                .quantity(command.quantity())
                .date(LocalDate.now())
                .build();

        return mapper.toResult(repository.save(sale));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResult> getAll() {
        return repository.findAll().stream().map(mapper::toResult).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResult> findBySubject(String subject) {
        return repository.findAllByUser_Username(subject).stream().map(mapper::toResult).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResult getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResult)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
    }

    @Override
    public SaleResult update(UpdateSaleCommand command) {
        var sale = repository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        if (command.productId() != null) {
            var product = productRepository.findById(command.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            sale.setProduct(product);
        }
        mapper.update(command, sale);

        return mapper.toResult(repository.save(sale));
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Sale not found");
        }
        repository.deleteById(id);
    }
}

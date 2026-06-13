package com.example.demo.data.services;

import com.example.demo.data.mappers.SaleMapper;
import com.example.demo.data.repositories.ProductRepository;
import com.example.demo.data.repositories.SaleRepository;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.data.services.commands.RegisterSaleCommand;
import com.example.demo.data.services.commands.UpdateSaleCommand;
import com.example.demo.data.services.results.SaleResult;
import com.example.demo.domain.entities.Sale;
import com.example.demo.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

        if (product.getStock() < command.quantity()) {
            throw new ResourceNotFoundException("Insufficient stock for product: " + product.getNombre());
        }

        var total = product.getPrecio().multiply(BigDecimal.valueOf(command.quantity()));

        var sale = Sale.builder()
                .product(product)
                .user(user)
                .quantity(command.quantity())
                .total(total)
                .date(LocalDate.now())
                .build();

        product.setStock(product.getStock() - command.quantity());
        productRepository.save(product);

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

        var product = sale.getProduct();
        if (command.productId() != null) {
            product = productRepository.findById(command.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            sale.setProduct(product);
        }

        var oldQuantity = sale.getQuantity();
        mapper.update(command, sale);

        var quantity = sale.getQuantity() != null ? sale.getQuantity() : 0;
        var total = product.getPrecio().multiply(BigDecimal.valueOf(quantity));
        sale.setTotal(total);

        var stockDiff = quantity - oldQuantity;
        var newStock = product.getStock() - stockDiff;
        if (newStock < 0) {
            throw new ResourceNotFoundException("Insufficient stock for product: " + product.getNombre());
        }
        product.setStock(newStock);
        productRepository.save(product);

        return mapper.toResult(repository.save(sale));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.findById(id).ifPresentOrElse(sale -> {
            var product = sale.getProduct();
            product.setStock(product.getStock() + sale.getQuantity());
            productRepository.save(product);
            repository.delete(sale);
        }, () -> {
            throw new ResourceNotFoundException("Sale not found");
        });
    }
}

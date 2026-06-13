package com.example.demo.data.services;

import com.example.demo.data.mappers.ProductMapper;
import com.example.demo.data.repositories.ProductRepository;
import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.infrastructure.rest.dtos.requests.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        var product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con id: " + id));
        return mapper.toDTO(product);
    }

    @Override
    public ProductDTO create(ProductDTO dto) {
        var product = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(product);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con id: " + id));
        mapper.update(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}

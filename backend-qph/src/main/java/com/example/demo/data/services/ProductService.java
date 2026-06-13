package com.example.demo.data.services;

import com.example.demo.infrastructure.rest.dtos.requests.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll();

    ProductDTO findById(Long id);

    ProductDTO create(ProductDTO dto);

    ProductDTO update(Long id, ProductDTO dto);

    void delete(Long id);
}

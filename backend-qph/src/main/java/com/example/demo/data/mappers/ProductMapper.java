package com.example.demo.data.mappers;

import com.example.demo.domain.entities.Product;
import com.example.demo.infrastructure.rest.dtos.requests.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .nombre(product.getNombre())
                .precio(product.getPrecio())
                .stock(product.getStock())
                .build();
    }

    public Product toEntity(ProductDTO dto) {
        return Product.builder()
                .nombre(dto.nombre())
                .precio(dto.precio())
                .stock(dto.stock())
                .build();
    }

    public void update(ProductDTO dto, Product product) {
        if (dto == null) return;
        if (dto.nombre() != null) product.setNombre(dto.nombre());
        if (dto.precio() != null) product.setPrecio(dto.precio());
        if (dto.stock() != null) product.setStock(dto.stock());
    }
}

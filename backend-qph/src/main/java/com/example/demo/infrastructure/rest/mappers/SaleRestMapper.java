package com.example.demo.infrastructure.rest.mappers;

import com.example.demo.data.services.commands.RegisterSaleCommand;
import com.example.demo.data.services.commands.UpdateSaleCommand;
import com.example.demo.data.services.results.SaleResult;
import com.example.demo.infrastructure.rest.dtos.requests.RegisterSaleRequest;
import com.example.demo.infrastructure.rest.dtos.requests.UpdateSaleRequest;
import com.example.demo.infrastructure.rest.dtos.responses.SaleResponse;
import org.springframework.stereotype.Component;

@Component
public class SaleRestMapper {

    public RegisterSaleCommand toCommand(RegisterSaleRequest request, String subject) {
        return new RegisterSaleCommand(request.productId(), subject, request.quantity());
    }

    public SaleResponse toResponse(SaleResult result) {
        return new SaleResponse(result.id(), result.product(), result.user(), result.quantity(), result.date());
    }

    public UpdateSaleCommand toCommand(Long id, UpdateSaleRequest request) {
        return new UpdateSaleCommand(id, request.productId(), request.quantity(), request.date());
    }
}

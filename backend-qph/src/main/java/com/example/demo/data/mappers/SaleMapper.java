package com.example.demo.data.mappers;

import com.example.demo.data.services.commands.UpdateSaleCommand;
import com.example.demo.data.services.results.SaleResult;
import com.example.demo.domain.entities.Sale;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

    public SaleResult toResult(Sale sale) {
        return new SaleResult(sale.getId(), sale.getProduct().getNombre(), sale.getUser().getUsername(), sale.getQuantity(), sale.getDate());
    }

    public void update(UpdateSaleCommand command, Sale sale) {
        if (command.quantity() != null) sale.setQuantity(command.quantity());
        if (command.date() != null) sale.setDate(command.date());
    }
}

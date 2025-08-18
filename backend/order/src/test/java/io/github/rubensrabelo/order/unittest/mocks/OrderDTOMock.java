package io.github.rubensrabelo.order.unittest.mocks;

import io.github.rubensrabelo.order.application.dto.order.OrderCreateDTO;
import io.github.rubensrabelo.order.application.dto.order.OrderResponseDTO;
import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderDTOMock {

    public static OrderCreateDTO createDTO() {
        return new OrderCreateDTO(Set.of(1L, 2L));
    }

    public static OrderResponseDTO createResponse() {
        OrderResponseDTO dto = new OrderResponseDTO(1L, LocalDateTime.now(), 100.0);
        dto.setProducts(Set.of(
                new ProductResponseDTO(1L, "Product 1", "Description 1", 50.0),
                new ProductResponseDTO(2L, "Product 2", "Description 2", 50.0)
        ));
        return dto;
    }
}

package io.github.rubensrabelo.order.unitest.mocks;

import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;

public class ProductDTOMock {
    public static ProductResponseDTO product1() {
        return new ProductResponseDTO(1L, "Product 1", "Description 1", 50.0);
    }

    public static ProductResponseDTO product2() {
        return new ProductResponseDTO(2L, "Product 2", "Description 2", 50.0);
    }
}

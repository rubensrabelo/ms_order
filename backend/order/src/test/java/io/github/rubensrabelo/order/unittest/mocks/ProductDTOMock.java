package io.github.rubensrabelo.order.unittest.mocks;

import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;

public class ProductDTOMock {
    public static ProductResponseDTO createDTO(Integer id) {
        return new ProductResponseDTO(
                id.longValue(),
                "Product " + id,
                "Description" + id,
                id * 10
        );
    }
}

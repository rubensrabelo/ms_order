package io.github.rubensrabelo.order.application.dto.product;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        double price
) {
}

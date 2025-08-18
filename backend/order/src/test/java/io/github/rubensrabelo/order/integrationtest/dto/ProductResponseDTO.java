package io.github.rubensrabelo.order.integrationtest.dto;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        double price
) {
}

package io.github.rubensrabelo.product.application.dto.order;

import java.util.Set;

public record OrderCreatedEvent (
        Long id,
        Set<Long> productsId,
        double total
) {
}

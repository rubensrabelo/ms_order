package io.github.rubensrabelo.order.application.dto.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OrderCreateDTO {

    private Set<Long> products = new HashSet<>();

    public OrderCreateDTO() {
    }

    public Set<Long> getProducts() {
        return products;
    }

    public void setProducts(Set<Long> products) {
        this.products = products;
    }
}

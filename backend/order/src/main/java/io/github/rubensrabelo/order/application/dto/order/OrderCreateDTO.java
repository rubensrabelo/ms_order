package io.github.rubensrabelo.order.application.dto.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OrderCreateDTO {

    private LocalDateTime created;
    private double totalAmount;

    private Set<Long> products = new HashSet<>();

    public OrderCreateDTO() {
    }

    public OrderCreateDTO(LocalDateTime created, double totalAmount) {
        this.created = created;
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<Long> getProducts() {
        return products;
    }

    public void setProducts(Set<Long> products) {
        this.products = products;
    }
}

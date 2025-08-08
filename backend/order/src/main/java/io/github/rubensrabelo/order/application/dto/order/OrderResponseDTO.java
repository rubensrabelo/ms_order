package io.github.rubensrabelo.order.application.dto.order;

import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class OrderResponseDTO {

    private Long id;
    private LocalDateTime created;
    private double totalAmount;

    private Set<ProductResponseDTO> products = new HashSet<>();

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Long id, LocalDateTime created, double totalAmount) {
        this.id = id;
        this.created = created;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<ProductResponseDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductResponseDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderResponseDTO order = (OrderResponseDTO) o;
        return Double.compare(totalAmount, order.totalAmount) == 0 && Objects.equals(id, order.id) && Objects.equals(created, order.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, totalAmount);
    }
}

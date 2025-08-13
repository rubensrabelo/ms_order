package io.github.rubensrabelo.order.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    private double totalAmount;

    @ElementCollection
    @CollectionTable(
            name = "tb_order_products",
            joinColumns = @JoinColumn(name = "order_id")
    )
    @Column(name = "product_id")
    private Set<Long> productsId = new HashSet<>();

    public Order() {
    }

    public Order(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @PrePersist
    public void onCreate() {
        this.created = LocalDateTime.now();
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

    public Set<Long> getProductsId() {
        return productsId;
    }

    public void setProductsId(Set<Long> productsId) {
        this.productsId = productsId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(totalAmount, order.totalAmount) == 0 && Objects.equals(id, order.id) && Objects.equals(created, order.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, totalAmount);
    }
}

package io.github.rubensrabelo.order.application.dto.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OrderCreateDTO {

    private Set<Long> productsId = new HashSet<>();

    public OrderCreateDTO() {
    }

    public Set<Long> getProductsId() {
        return productsId;
    }

    public void setProductsId(Set<Long> productsId) {
        this.productsId = productsId;
    }
}

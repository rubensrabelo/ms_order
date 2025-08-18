package io.github.rubensrabelo.order.unitest.mocks;

import io.github.rubensrabelo.order.domain.Order;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderEntityMock {

    public static Order createEntity() {
        Order order = new Order(100.0);
        order.setId(1L);
        order.setCreated(LocalDateTime.now());
        order.setProductsId(Set.of(1L, 2L));
        return order;
    }

}

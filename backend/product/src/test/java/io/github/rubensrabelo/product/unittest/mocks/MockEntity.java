package io.github.rubensrabelo.product.unittest.mocks;

import io.github.rubensrabelo.product.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class MockEntity {

    public Product mockEntity(int id) {
        Product prod = new Product();
        prod.setId((long) id);
        prod.setName("Name " + id);
        prod.setDescription("Description " + id);
        prod.setPrice((double) id * 10);
        return prod;
    }

    public List<Product> mockListEntities(int qtd) {
        List<Product> listEntities = new ArrayList<>();
        for(int i = 0; i < qtd; i++)
            listEntities.add(mockEntity(i));
        return listEntities;
    }

}

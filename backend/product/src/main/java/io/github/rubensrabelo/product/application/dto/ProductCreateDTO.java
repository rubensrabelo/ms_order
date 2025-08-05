package io.github.rubensrabelo.product.application.dto;

import java.util.Objects;

public class ProductCreateDTO {
    private String name;
    private Double price;

    public ProductCreateDTO() {
    }

    public ProductCreateDTO(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

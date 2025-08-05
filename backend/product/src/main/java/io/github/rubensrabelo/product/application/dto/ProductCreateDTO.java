package io.github.rubensrabelo.product.application.dto;

import java.util.Objects;

public class ProductCreateDTO {
    private String name;
    private Double price;
    private String description;

    public ProductCreateDTO() {
    }

    public ProductCreateDTO(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

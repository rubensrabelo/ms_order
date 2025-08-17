package io.github.rubensrabelo.product.integrationtest.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rubensrabelo.product.integrationtest.dto.ProductResponseDTO;

import java.io.Serializable;
import java.util.List;

public class ProductEmbeddedDTO  implements Serializable {

    @JsonProperty("productDTOList")
    private List<ProductResponseDTO> products;

    public ProductEmbeddedDTO() {}

    public List<ProductResponseDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDTO> products) {
        this.products = products;
    }
}

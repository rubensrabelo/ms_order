package io.github.rubensrabelo.product.integrationtest.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperProductDTO implements Serializable {

    @JsonProperty("_embedded")
    private ProductEmbeddedDTO embedded;

    public WrapperProductDTO() {}

    public ProductEmbeddedDTO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(ProductEmbeddedDTO embedded) {
        this.embedded = embedded;
    }
}
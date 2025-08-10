package io.github.rubensrabelo.product.unittest.mocks;

import io.github.rubensrabelo.product.application.dto.ProductResponseDTO;
import io.github.rubensrabelo.product.domain.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MockDTO {

    public ProductResponseDTO mockDTO() {
        return mockDTO(1);
    }

    public List<ProductResponseDTO> mockListDTOs(int qtd) {
        List<ProductResponseDTO> listDTOs = new ArrayList<>();
        for(int i = 0; i < qtd; i++)
            listDTOs.add(mockDTO(i));
        return listDTOs;
    }

    private ProductResponseDTO mockDTO(int id) {
        ProductResponseDTO prod = new ProductResponseDTO();
        prod.setId((long) id);
        prod.setName("Name " + id);
        prod.setDescription("Description " + id);
        prod.setPrice((double) id * 10);
        return prod;
    }
}

package io.github.rubensrabelo.product.unittest.mocks;

import io.github.rubensrabelo.product.application.dto.ProductCreateDTO;
import io.github.rubensrabelo.product.application.dto.ProductResponseDTO;
import io.github.rubensrabelo.product.application.dto.ProductUpdateDTO;
import io.github.rubensrabelo.product.domain.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MockDTO {

    public ProductResponseDTO mockDTOResponse(int id) {
        ProductResponseDTO dtoResponse = new ProductResponseDTO();
        dtoResponse.setId((long) id);
        dtoResponse.setName("Name " + id);
        dtoResponse.setDescription("Description " + id);
        dtoResponse.setPrice((double) id * 10);
        return dtoResponse;
    }

    public ProductCreateDTO mockDTOCreate(int id) {
        ProductCreateDTO dtoCreate = new ProductCreateDTO();
        dtoCreate.setName("Name " + id);
        dtoCreate.setDescription("Description " + id);
        dtoCreate.setPrice((double) id * 10);
        return dtoCreate;
    }

    public ProductUpdateDTO mockDTOUpdate(int id) {
        ProductUpdateDTO dtoUpdate = new ProductUpdateDTO();
        dtoUpdate.setName("Name Update " + id);
        dtoUpdate.setDescription("Description Update " + id);
        dtoUpdate.setPrice((double) id * 20);
        return dtoUpdate;
    }

    public List<ProductResponseDTO> mockListDTOResponse(int qtd) {
        List<ProductResponseDTO> listDTOs = new ArrayList<>();
        for(int i = 0; i < qtd; i++)
            listDTOs.add(mockDTOResponse(i));
        return listDTOs;
    }
}

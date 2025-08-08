package io.github.rubensrabelo.order.infra.clients;

import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msproduct", path = "/products")
public interface ProductResourceClient {

    @GetMapping(value = "/{id}")
    ProductResponseDTO findById(@PathVariable Long id);
}

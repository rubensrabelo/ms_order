package io.github.rubensrabelo.order.infra.clients;

import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;
import org.apache.coyote.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msproduct", path = "/products")
public interface ProductResourceClient {

    @GetMapping(value = "/{id}")
    ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id);
}

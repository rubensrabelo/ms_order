package io.github.rubensrabelo.order.application;

import feign.FeignException;
import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;
import io.github.rubensrabelo.order.application.handler.exceptions.IntegrationException;
import io.github.rubensrabelo.order.application.handler.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.order.infra.clients.ProductResourceClient;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductResourceClient productClient;

    public ProductService(ProductResourceClient productClient) {
        this.productClient = productClient;
    }

    public ProductResponseDTO findProductById(Long id) {
        try {
            return productClient.findById(id).getBody();
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        } catch (FeignException e) {
            throw new IntegrationException(
                    "Error communicating with Product Service: " + e.status() + " " + e.getMessage(),
                    e
            );
        }
    }
}

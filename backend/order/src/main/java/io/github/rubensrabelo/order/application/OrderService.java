package io.github.rubensrabelo.order.application;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.rubensrabelo.order.application.dto.order.OrderCreateDTO;
import io.github.rubensrabelo.order.application.dto.order.OrderResponseDTO;
import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;
import io.github.rubensrabelo.order.application.handler.exceptions.IntegrationException;
import io.github.rubensrabelo.order.application.handler.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.order.domain.Order;
import io.github.rubensrabelo.order.infra.clients.ProductResourceClient;
import io.github.rubensrabelo.order.infra.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductResourceClient productClient;
    private final ModelMapper modelMapper;

    public OrderService(
            OrderRepository repository,
            ModelMapper modelMapper,
            ProductResourceClient productClient
    ) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.productClient = productClient;
    }

    public Page<OrderResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(entity -> {
                    OrderResponseDTO dto = modelMapper.map(entity, OrderResponseDTO.class);
                    Set<ProductResponseDTO> products = entity.getProductsId().stream()
                            .map(this::findProductById)
                            .collect(Collectors.toSet());
                    dto.setProducts(products);
                    return dto;
                });
    }

    public OrderResponseDTO findById(Long id) {
        Order entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        OrderResponseDTO dto = modelMapper.map(entity, OrderResponseDTO.class);
        Set<ProductResponseDTO> products = entity.getProductsId().stream()
                .map(this::findProductById)
                .collect(Collectors.toSet());
        dto.setProducts(products);
        return dto;
    }

    public OrderResponseDTO create(OrderCreateDTO dtoCreate) {
        Set<ProductResponseDTO> productsDTO = dtoCreate.getProductsId().stream()
                .map(this::findProductById)
                .collect(Collectors.toSet());
        double total = productsDTO.stream()
                .mapToDouble(ProductResponseDTO::price)
                .sum();

        Order entity = new Order(total);
        entity.setProductsId(dtoCreate.getProductsId());

        Order savedOrder = repository.save(entity);

        OrderResponseDTO dtoResponse = modelMapper.map(savedOrder, OrderResponseDTO.class);
        dtoResponse.setProducts(productsDTO);

        return dtoResponse;
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackFindProductById")
    @Retry(name = "productService")
    private ProductResponseDTO findProductById(Long id) {
        try {
            return productClient.findById(id).getBody();
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        } catch (FeignException e) {
            throw new IntegrationException(
                    "Error communicating with Product Service: " + e.status() + " " + e.getMessage()
            );
        }
    }

    private ProductResponseDTO fallbackFindProductById(Long id, Throwable ex) {
        return new ProductResponseDTO(
                id,
                "Product Unavailable",
                "This product could not be loaded.",
                0.0
        );
    }
}

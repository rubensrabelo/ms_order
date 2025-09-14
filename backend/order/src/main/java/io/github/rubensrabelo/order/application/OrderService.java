package io.github.rubensrabelo.order.application;

import io.github.rubensrabelo.order.application.dto.order.OrderCreateDTO;
import io.github.rubensrabelo.order.application.dto.order.OrderResponseDTO;
import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;
import io.github.rubensrabelo.order.application.handler.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.order.domain.Order;
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
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public OrderService(
            OrderRepository repository,
            ProductService productService,
            ModelMapper modelMapper
    ) {
        this.repository = repository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    public Page<OrderResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(entity -> {
                    OrderResponseDTO dto = modelMapper.map(entity, OrderResponseDTO.class);
                    Set<ProductResponseDTO> products = entity.getProductsId().stream()
                            .map(productService::findProductById)
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
                .map(productService::findProductById)
                .collect(Collectors.toSet());
        dto.setProducts(products);
        return dto;
    }

    public OrderResponseDTO create(OrderCreateDTO dtoCreate) {
        Set<ProductResponseDTO> productsDTO = dtoCreate.getProductsId().stream()
                .map(productService::findProductById)
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
}

package io.github.rubensrabelo.order.application;

import io.github.rubensrabelo.order.application.dto.order.OrderCreateDTO;
import io.github.rubensrabelo.order.application.dto.order.OrderResponseDTO;
import io.github.rubensrabelo.order.application.handler.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.order.domain.Order;
import io.github.rubensrabelo.order.infra.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Page<OrderResponseDTO> findAll(Pageable pageable) {
        Page<Order> entities = repository.findAll(pageable);
        return entities.map(
                entity -> modelMapper.map(entity, OrderResponseDTO.class)
        );
    }

    public OrderResponseDTO findById(Long id) {
        Order entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        return modelMapper.map(entity, OrderResponseDTO.class);
    }

    public OrderService create(OrderCreateDTO dtoCreate) {
        return null;
    }
}

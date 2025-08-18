package io.github.rubensrabelo.order.unittest.application;

import feign.FeignException;
import io.github.rubensrabelo.order.application.OrderService;
import io.github.rubensrabelo.order.application.dto.order.OrderCreateDTO;
import io.github.rubensrabelo.order.application.dto.order.OrderResponseDTO;
import io.github.rubensrabelo.order.application.handler.exceptions.IntegrationException;
import io.github.rubensrabelo.order.application.handler.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.order.domain.Order;
import io.github.rubensrabelo.order.infra.clients.ProductResourceClient;
import io.github.rubensrabelo.order.infra.repository.OrderRepository;
import io.github.rubensrabelo.order.unitest.mocks.OrderDTOMock;
import io.github.rubensrabelo.order.unitest.mocks.OrderEntityMock;
import io.github.rubensrabelo.order.unitest.mocks.ProductDTOMock;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderRepository repository;
    private ProductResourceClient productClient;
    private ModelMapper modelMapper;
    private OrderService service;

    @BeforeEach
    void setup() {
        repository = mock(OrderRepository.class);
        productClient = mock(ProductResourceClient.class);
        modelMapper = new ModelMapper();
        service = new OrderService(repository, modelMapper, productClient);
    }

    @Test
    void findAll() {
        Order order = OrderEntityMock.createEntity();
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(order)));

        when(productClient.findById(1L)).thenReturn(ResponseEntity.ok(ProductDTOMock.product1()));
        when(productClient.findById(2L)).thenReturn(ResponseEntity.ok(ProductDTOMock.product2()));

        Page<OrderResponseDTO> result = service.findAll(Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(2, result.getContent().get(0).getProducts().size());
    }

    @Test
    void findById() {
        Order order = OrderEntityMock.createEntity();
        when(repository.findById(1L)).thenReturn(Optional.of(order));
        when(productClient.findById(1L)).thenReturn(ResponseEntity.ok(ProductDTOMock.product1()));
        when(productClient.findById(2L)).thenReturn(ResponseEntity.ok(ProductDTOMock.product2()));

        OrderResponseDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(2, result.getProducts().size());
    }

    @Test
    void testFindByIdWithIdDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void create() {
        OrderCreateDTO createDTO = OrderDTOMock.createDTO();

        when(productClient.findById(1L)).thenReturn(ResponseEntity.ok(ProductDTOMock.product1()));
        when(productClient.findById(2L)).thenReturn(ResponseEntity.ok(ProductDTOMock.product2()));

        when(repository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        OrderResponseDTO response = service.create(createDTO);

        assertNotNull(response);
        assertEquals(2, response.getProducts().size());
        assertEquals(100.0, response.getTotalAmount());
    }

    @Test
    void testFindProductWithProductNotFound() {
        OrderCreateDTO createDTO = OrderDTOMock.createDTO();
        when(productClient.findById(1L)).thenThrow(mock(FeignException.NotFound.class));

        assertThrows(ResourceNotFoundException.class, () -> service.create(createDTO));
    }

    @Test
    void testeWhenIntegrationFails() {
        OrderCreateDTO createDTO = OrderDTOMock.createDTO();
        when(productClient.findById(1L)).thenThrow(mock(FeignException.class));

        assertThrows(IntegrationException.class, () -> service.create(createDTO));
    }
}

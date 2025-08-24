package io.github.rubensrabelo.order.unittest.application;

import feign.FeignException;
import feign.Request;
import io.github.rubensrabelo.order.application.OrderService;
import io.github.rubensrabelo.order.application.dto.order.OrderCreateDTO;
import io.github.rubensrabelo.order.application.dto.order.OrderResponseDTO;
import io.github.rubensrabelo.order.application.handler.exceptions.IntegrationException;
import io.github.rubensrabelo.order.application.handler.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.order.domain.Order;
import io.github.rubensrabelo.order.infra.clients.ProductResourceClient;
import io.github.rubensrabelo.order.infra.repository.OrderRepository;
import io.github.rubensrabelo.order.unittest.mocks.OrderDTOMock;
import io.github.rubensrabelo.order.unittest.mocks.OrderEntityMock;
import io.github.rubensrabelo.order.unittest.mocks.ProductDTOMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository repository;

    @Mock
    private ProductResourceClient productClient;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new OrderService(repository, modelMapper, productClient);
    }

    @Test
    void findAll() {
        Order order = OrderEntityMock.createEntity();
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(order)));

        when(productClient.findById(1L)).thenReturn(ResponseEntity.ok(ProductDTOMock.createDTO(1)));
        when(productClient.findById(2L)).thenReturn(ResponseEntity.ok(ProductDTOMock.createDTO(2)));

        when(modelMapper.map(any(Order.class), eq(OrderResponseDTO.class)))
                .thenAnswer(invocation -> {
                    Order o = invocation.getArgument(0);
                    return new OrderResponseDTO(o.getId(), o.getCreated(), o.getTotalAmount());
                });

        Page<OrderResponseDTO> result = service.findAll(Pageable.unpaged());

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getContent().size());
        assertEquals(2, result.getContent().get(0).getProducts().size());

        verify(repository, times(1)).findAll(any(Pageable.class));
        verify(productClient, times(2)).findById(anyLong());
        verify(modelMapper, times(result.getContent().size())).map(any(Order.class), eq(OrderResponseDTO.class));
    }

    @Test
    void findById() {
        Order order = OrderEntityMock.createEntity();
        when(repository.findById(1L)).thenReturn(Optional.of(order));
        when(productClient.findById(1L)).thenReturn(ResponseEntity.ok(ProductDTOMock.createDTO(1)));
        when(productClient.findById(2L)).thenReturn(ResponseEntity.ok(ProductDTOMock.createDTO(2)));
        when(modelMapper.map(any(Order.class), eq(OrderResponseDTO.class)))
                .thenAnswer(invocation -> {
                    Order o = invocation.getArgument(0);
                    return new OrderResponseDTO(o.getId(), o.getCreated(), o.getTotalAmount());
                });

        OrderResponseDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(2, result.getProducts().size());

        verify(repository, times(1)).findById(1L);
        verify(productClient, times(2)).findById(anyLong());
        verify(modelMapper, times(1)).map(any(Order.class), eq(OrderResponseDTO.class));
    }

    @Test
    void testFindByIdWithIdDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void create() {
        OrderCreateDTO createDTO = OrderDTOMock.createDTO();

        when(productClient.findById(1L)).thenReturn(ResponseEntity.ok(ProductDTOMock.createDTO(1)));
        when(productClient.findById(2L)).thenReturn(ResponseEntity.ok(ProductDTOMock.createDTO(2)));

        when(repository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        when(modelMapper.map(any(Order.class), eq(OrderResponseDTO.class)))
                .thenAnswer(invocation -> {
                    Order o = invocation.getArgument(0);
                    return new OrderResponseDTO(o.getId(), o.getCreated(), o.getTotalAmount());
                });

        OrderResponseDTO response = service.create(createDTO);

        assertNotNull(response);
        assertEquals(2, response.getProducts().size());
        assertEquals(30, response.getTotalAmount());

        verify(productClient, times(2)).findById(any(Long.class));
        verify(repository, times(1)).save(any(Order.class));
        verify(modelMapper, times(1)).map(any(Order.class), eq(OrderResponseDTO.class));
    }

    @Test
    void testFindProductWithProductNotFound() {
        OrderCreateDTO createDTO = OrderDTOMock.createDTO();

        Request request = Request.create(Request.HttpMethod.GET, "/products/1", Map.of(), null, Charset.defaultCharset(), null);
        FeignException.NotFound notFoundException = new FeignException.NotFound("Not Found", request, null, null);

        when(productClient.findById(anyLong())).thenThrow(notFoundException);

        assertThrows(ResourceNotFoundException.class, () -> service.create(createDTO));
    }

    @Test
    void testWhenIntegrationFails() {
        OrderCreateDTO createDTO = OrderDTOMock.createDTO();

        Request request = Request.create(Request.HttpMethod.GET, "/products/1", Map.of(), null, Charset.defaultCharset(), null);
        FeignException feignException = new FeignException.InternalServerError("Server Error", request, null, null);

        when(productClient.findById(anyLong())).thenThrow(feignException);

        assertThrows(IntegrationException.class, () -> service.create(createDTO));
    }

}

package io.github.rubensrabelo.product.unittest.application;

import io.github.rubensrabelo.product.application.ProductService;
import io.github.rubensrabelo.product.application.dto.ProductResponseDTO;
import io.github.rubensrabelo.product.domain.Product;
import io.github.rubensrabelo.product.infra.repository.ProductRepository;
import io.github.rubensrabelo.product.unittest.mocks.MockDTO;
import io.github.rubensrabelo.product.unittest.mocks.MockEntity;
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
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    MockEntity dataEntity;
    MockDTO dataDTO;

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        dataEntity = new MockEntity();
        dataDTO = new MockDTO();

        service = new ProductService(repository, modelMapper);
    }

    @Test
    void findAll() {
        List<Product> entities = dataEntity.mockListEntities(5);
        List<ProductResponseDTO> dtos = dataDTO.mockListDTOs(5);

        Page<Product> page = new PageImpl<>(
                entities,
                PageRequest.of(0, 10),
                entities.size()
        );

        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        for (int i = 0; i < entities.size(); i++) {
            when(modelMapper.map(entities.get(i), ProductResponseDTO.class)).thenReturn(dtos.get(i));
        }

        var result = service.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(5, result.getContent().size());

        result.getContent().forEach(dto -> {
            assertNotNull(dto);
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getPrice());
        });

        verify(repository, times(1)).findAll(any(PageRequest.class));
        verify(modelMapper, times(5)).map(any(Product.class), eq(ProductResponseDTO.class));
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
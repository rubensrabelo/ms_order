package io.github.rubensrabelo.product.unittest.application;

import io.github.rubensrabelo.product.application.ProductService;
import io.github.rubensrabelo.product.application.dto.ProductCreateDTO;
import io.github.rubensrabelo.product.application.dto.ProductResponseDTO;
import io.github.rubensrabelo.product.application.dto.ProductUpdateDTO;
import io.github.rubensrabelo.product.application.handler.exceptions.ResourceNotFoundException;
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
import java.util.Optional;

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
        List<ProductResponseDTO> listDTO = dataDTO.mockListDTOResponse(5);

        Page<Product> page = new PageImpl<>(
                entities,
                PageRequest.of(0, 10),
                entities.size()
        );

        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        for (int i = 0; i < entities.size(); i++) {
            when(modelMapper.map(entities.get(i), ProductResponseDTO.class)).thenReturn(listDTO.get(i));
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
        Product entity = dataEntity.mockEntity(1);
        ProductResponseDTO dto = dataDTO.mockDTOResponse(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, ProductResponseDTO.class)).thenReturn(dto);

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getPrice(), result.getPrice());

        verify(repository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(entity, ProductResponseDTO.class);
    }

    @Test
    void testFindByIdWithIdDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(1L)
        );

        String expectedMessage = "Product not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repository, times(1)).findById(1L);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void create() {
        ProductResponseDTO dtoResponse = dataDTO.mockDTOResponse(1);
        Product persisted = dataEntity.mockEntity(1);

        ProductCreateDTO dtoCreate = dataDTO.mockDTOCreate(1);

        when(modelMapper.map(dtoCreate, Product.class)).thenReturn(persisted);
        when(repository.save(persisted)).thenReturn(persisted);
        when(modelMapper.map(persisted, ProductResponseDTO.class)).thenReturn(dtoResponse);

        var result = service.create(dtoCreate);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(dtoResponse.getName(), result.getName());
        assertEquals(dtoResponse.getDescription(), result.getDescription());
        assertEquals(dtoResponse.getPrice(), result.getPrice());

        verify(modelMapper, times(1)).map(dtoCreate, Product.class);
        verify(repository, times(1)).save(persisted);
        verify(modelMapper, times(1)).map(persisted, ProductResponseDTO.class);
    }

    @Test
    void update() {
        ProductResponseDTO dtoResponse = dataDTO.mockDTOResponse(1);
        Product entity = dataEntity.mockEntity(1);

        ProductUpdateDTO dtoUpdate = dataDTO.mockDTOUpdate(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(modelMapper.map(entity, ProductResponseDTO.class)).thenReturn(dtoResponse);

        var result = service.update(1L, dtoUpdate);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(dtoResponse.getName(), result.getName());
        assertEquals(dtoResponse.getDescription(), result.getDescription());
        assertEquals(dtoResponse.getPrice(), result.getPrice());


        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(entity);
        verify(modelMapper, times(1)).map(entity, ProductResponseDTO.class);
    }

    @Test
    void testUpdateWithIdDoesNotExist() {
        ProductUpdateDTO dtoUpdate = dataDTO.mockDTOUpdate(1);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.update(1L, dtoUpdate)
        );

        String expectedMessage = "Product not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repository, times(1)).findById(1L);
        verifyNoMoreInteractions(repository, modelMapper);
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(1)).deleteById(1L);
    }
}
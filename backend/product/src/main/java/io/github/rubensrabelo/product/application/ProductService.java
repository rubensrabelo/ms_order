package io.github.rubensrabelo.product.application;

import io.github.rubensrabelo.product.application.dto.ProductCreateDTO;
import io.github.rubensrabelo.product.application.dto.ProductResponseDTO;
import io.github.rubensrabelo.product.application.dto.ProductUpdateDTO;
import io.github.rubensrabelo.product.application.handler.exceptions.DatabaseException;
import io.github.rubensrabelo.product.application.handler.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.product.domain.Product;
import io.github.rubensrabelo.product.infra.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        Page<Product> entities = repository.findAll(pageable);
        return entities.map(
                entity -> modelMapper.map(entity, ProductResponseDTO.class)
        );
    }

    public ProductResponseDTO findById(Long id) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        return modelMapper.map(entity, ProductResponseDTO.class);
    }

    public ProductResponseDTO create(ProductCreateDTO dtoCreate) {
        Product entity = modelMapper.map(dtoCreate, Product.class);
        entity = repository.save(entity);
        return modelMapper.map(entity, ProductResponseDTO.class);
    }

    public ProductResponseDTO update(Long id, ProductUpdateDTO dtoUpdate) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
        updateData(dtoUpdate, entity);
        repository.save(entity);
        return modelMapper.map(entity, ProductResponseDTO.class);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Product not found.");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private void updateData(ProductUpdateDTO dtoUpdate, Product entity) {
        entity.setName(dtoUpdate.getName());
        entity.setPrice(dtoUpdate.getPrice());
        entity.setDescription(dtoUpdate.getDescription());
    }
}

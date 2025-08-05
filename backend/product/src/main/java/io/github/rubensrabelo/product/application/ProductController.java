package io.github.rubensrabelo.product.application;

import io.github.rubensrabelo.product.application.dto.ProductCreateDTO;
import io.github.rubensrabelo.product.application.dto.ProductResponseDTO;
import io.github.rubensrabelo.product.application.dto.ProductUpdateDTO;
import io.github.rubensrabelo.product.application.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public ResponseEntity<Page<ProductResponseDTO>> findAll(Pageable pageable) {
        Page<ProductResponseDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<ProductResponseDTO> findById(Long id) {
        return null;
    }

    public ResponseEntity<ProductResponseDTO> create(ProductCreateDTO dtoCreate) {
        return null;
    }

    public ResponseEntity<ProductResponseDTO> update(Long id, ProductUpdateDTO dtoUpdate) {
        return null;
    }
}

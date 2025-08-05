package io.github.rubensrabelo.product.application;

import io.github.rubensrabelo.product.application.dto.ProductCreateDTO;
import io.github.rubensrabelo.product.application.dto.ProductResponseDTO;
import io.github.rubensrabelo.product.application.dto.ProductUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
        ProductResponseDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<ProductResponseDTO> create(ProductCreateDTO dtoCreate) {
        ProductResponseDTO dto = service.create(dtoCreate);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    public ResponseEntity<ProductResponseDTO> update(Long id, ProductUpdateDTO dtoUpdate) {
        ProductResponseDTO dto = service.update(id, dtoUpdate);
        return ResponseEntity.ok().body(dto);
    }
}

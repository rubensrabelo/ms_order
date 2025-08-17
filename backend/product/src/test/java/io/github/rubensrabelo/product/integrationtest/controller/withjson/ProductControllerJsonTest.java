package io.github.rubensrabelo.product.integrationtest.controller.withjson;

import io.github.rubensrabelo.product.integrationtest.dto.ProductResponseDTO;
import io.github.rubensrabelo.product.config.TestConfigs;
import io.github.rubensrabelo.product.integrationtest.dto.wrappers.json.PageProductDTO;
import io.github.rubensrabelo.product.integrationtest.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static ProductResponseDTO product;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        product = new ProductResponseDTO();
    }

    @Test
    @Order(1)
    void create() throws IOException {
        mockProduct();

        specification = new RequestSpecBuilder()
                .setBasePath("/products")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(product)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        ProductResponseDTO createdProduct = objectMapper.readValue(content, ProductResponseDTO.class);
        product = createdProduct;

        assertNotNull(createdProduct.getId());
        assertNotNull(createdProduct.getName());
        assertNotNull(createdProduct.getDescription());
        assertNotNull(createdProduct.getPrice());

        Assertions.assertTrue(createdProduct.getId() > 0);

        assertEquals("Product 01", createdProduct.getName());
        assertEquals("Description Product 01", createdProduct.getDescription());
        assertEquals(10.00, createdProduct.getPrice());
    }

    @Test
    @Order(2)
    void update() throws IOException {
        product.setName("Product Updated");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(product)
                .when()
                .put("/{id}", product.getId())
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        ProductResponseDTO createdProduct = objectMapper.readValue(content, ProductResponseDTO.class);
        product = createdProduct;

        assertNotNull(createdProduct.getId());
        assertNotNull(createdProduct.getName());
        assertNotNull(createdProduct.getDescription());
        assertNotNull(createdProduct.getPrice());

        Assertions.assertTrue(createdProduct.getId() > 0);

        assertEquals("Product Updated", createdProduct.getName());
        assertEquals("Description Product 01", createdProduct.getDescription());
        assertEquals(10.00, createdProduct.getPrice());
    }

    @Test
    @Order(3)
    void findById() throws IOException {
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParams("id", product.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        ProductResponseDTO createdProduct = objectMapper.readValue(content, ProductResponseDTO.class);

        assertNotNull(createdProduct.getId());
        assertNotNull(createdProduct.getName());
        assertNotNull(createdProduct.getDescription());
        assertNotNull(createdProduct.getPrice());

        Assertions.assertTrue(createdProduct.getId() > 0);

        assertEquals("Product Updated", createdProduct.getName());
        assertEquals("Description Product 01", createdProduct.getDescription());
        assertEquals(10.00, createdProduct.getPrice());
    }

    @Test
    @Order(4)
    void delete() {
        given(specification)
                .pathParam("id", product.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    void findAll() throws IOException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam("direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PageProductDTO page = objectMapper.readValue(content, PageProductDTO.class);
        var products = page.getContent();

        assertEquals(0, page.getNumber());
        assertEquals(10, page.getSize());
        assertEquals(5, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
        assertEquals(5, page.getNumberOfElements());
        assertTrue(page.isFirst());
        assertTrue(page.isLast());

        ProductResponseDTO productOne = products.get(0);

        assertNotNull(productOne.getId());
        assertTrue(productOne.getId() > 0);
        assertEquals("Laptop Stand", productOne.getName());
        assertEquals(89.0, productOne.getPrice());
        assertEquals("Aluminum stand for laptops up to 17 inches.", productOne.getDescription());

        ProductResponseDTO productFour = products.get(3);

        assertNotNull(productFour.getId());
        assertTrue(productFour.getId() > 0);
        assertEquals("USB-C Hub", productFour.getName());
        assertEquals(129.5, productFour.getPrice());
        assertEquals("7-in-1 USB-C hub with HDMI and Ethernet.", productFour.getDescription());
    }

    private void mockProduct() {
        product.setName("Product 01");
        product.setDescription("Description Product 01");
        product.setPrice((double) 10);
    }
}

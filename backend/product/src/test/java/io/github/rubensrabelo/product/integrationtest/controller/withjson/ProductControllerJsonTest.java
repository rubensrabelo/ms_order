package io.github.rubensrabelo.product.integrationtest.controller.withjson;

import io.github.rubensrabelo.product.application.dto.ProductResponseDTO;
import io.github.rubensrabelo.product.config.TestConfigs;
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
        assertNotNull(createdProduct.getPrice());

        Assertions.assertTrue(createdProduct.getId() > 0);

        assertEquals("Product 01", createdProduct.getName());
        assertEquals("Description Product 01", createdProduct.getDescription());
        assertEquals(10.00, createdProduct.getPrice());
    }

    @Test
    @Order(2)
    void update() {
    }

    @Test
    @Order(3)
    void findById() {
    }

    @Test
    @Order(4)
    void delete() {
    }

    @Test
    @Order(5)
    void findAll() {
    }

    private void mockProduct() {
        product.setName("Product 01");
        product.setDescription("Description Product 01");
        product.setPrice((double) 10);
    }
}

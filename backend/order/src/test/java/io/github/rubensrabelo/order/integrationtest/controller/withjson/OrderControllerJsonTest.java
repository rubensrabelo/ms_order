package io.github.rubensrabelo.order.integrationtest.controller.withjson;

import io.github.rubensrabelo.order.application.dto.order.OrderCreateDTO;
import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;
import io.github.rubensrabelo.order.infra.clients.ProductResourceClient;
import io.github.rubensrabelo.order.integrationtest.dto.OrderResponseDTO;
import io.github.rubensrabelo.order.integrationtest.dto.wrappers.json.PageOrderDTO;
import io.github.rubensrabelo.order.integrationtest.testcontainers.AbstractIntegrationTest;
import io.github.rubensrabelo.order.config.TestConfigs;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerJsonTest extends AbstractIntegrationTest {

    @MockBean
    private ProductResourceClient productResourceClient; // mock atualizado

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static OrderResponseDTO order;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/orders")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @BeforeEach
    void setupMock() {
        // Configura o mock do ProductResourceClient
        ProductResponseDTO product = new ProductResponseDTO(1L, "Product 01", "Description Product 01", 150.0);
        Mockito.when(productResourceClient.findById(1L))
                .thenReturn(ResponseEntity.ok(product));
    }

    @Test
    @Order(1)
    void createOrder() throws IOException {
        OrderCreateDTO createDTO = new OrderCreateDTO(Set.of(1L));

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        OrderResponseDTO createdOrder = objectMapper.readValue(content, OrderResponseDTO.class);
        order = createdOrder;

        assertNotNull(createdOrder.getId());
        assertNotNull(createdOrder.getCreated());
        assertNotNull(createdOrder.getProducts());

        assertTrue(createdOrder.getId() > 0);
        assertEquals(150.0, createdOrder.getTotalAmount());

        ProductResponseDTO product = createdOrder.getProducts().iterator().next();
        assertEquals(1L, product.id());
        assertEquals("Product 01", product.name());
    }

    @Test
    @Order(2)
    void findById() throws IOException {
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", order.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        OrderResponseDTO foundOrder = objectMapper.readValue(content, OrderResponseDTO.class);

        assertNotNull(foundOrder.getId());
        assertNotNull(foundOrder.getCreated());
        assertEquals(order.getId(), foundOrder.getId());
        assertEquals(150.0, foundOrder.getTotalAmount());

        assertFalse(foundOrder.getProducts().isEmpty());
    }

    @Test
    @Order(3)
    void findAllOrders() throws IOException {
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

        PageOrderDTO page = objectMapper.readValue(content, PageOrderDTO.class);
        var orders = page.getContent();

        assertEquals(0, page.getNumber());
        assertEquals(10, page.getSize());
        assertTrue(page.getTotalElements() >= 1);

        OrderResponseDTO firstOrder = orders.get(0);
        assertNotNull(firstOrder.getId());
        assertTrue(firstOrder.getId() > 0);
        assertNotNull(firstOrder.getProducts());
    }
}

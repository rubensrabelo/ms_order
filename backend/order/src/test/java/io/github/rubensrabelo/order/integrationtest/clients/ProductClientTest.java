package io.github.rubensrabelo.order.integrationtest.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.github.rubensrabelo.order.application.dto.product.ProductResponseDTO;
import io.github.rubensrabelo.order.infra.clients.ProductResourceClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        "device.service.url=http://localhost:8090",
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password="
})
class ProductClientTest {

    @Autowired
    private ProductResourceClient client;

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8090);
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void getProductById() {
        WireMock.stubFor(WireMock.get("/products/1")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                    {
                      "id": 1,
                      "name": "Product Test",
                      "description": "Description about Product Test",
                      "price": 99.99
                    }
                """)));

        ProductResponseDTO product = client.findById(1L).getBody();

        assertEquals(1L, product.id());
        assertEquals("Product Test", product.name());
        assertEquals("Description about Product Test", product.description());
        assertEquals(99.99, product.price());
    }
}
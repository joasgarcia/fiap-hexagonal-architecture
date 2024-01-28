package com.fiap.restaurant.api.product;

import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.types.dto.product.ProductDTO;
import com.fiap.restaurant.util.ProductTestUtil;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ProductRestControllerIT {

    private static final String PATH = "/product";
    private static final String SCHEMA_LOCATION = "./schemas/api/product";

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterEach
    public void tearDown() {
        productJpaRepository.deleteAll();
    }

    @Nested
    class Write {

        @Test
        void mustSaveProduct() {
            ProductDTO productDTO = ProductTestUtil.generateDTO("Drink 1", "Description 1", "DRINK", 7.5);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productDTO)
            .when()
                    .post(PATH + "/")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("id"))
                    .body("$", hasKey("name"))
                    .body("$", hasKey("description"))
                    .body("$", hasKey("price"))
                    .body("$", hasKey("category"))
                    .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductSaveSchema.json"));
        }
        
        @Test
        void mustUpdateProduct() {
            final String name = "Product 1";
            final String description = "Description 1";
            final Double price = 4.99;
            final String category = "DRINK";
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa(name, description, category, price));

            ProductDTO productDTO = ProductTestUtil.generateDTO(name, description, category, price);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productDTO)
            .when()
                    .put(PATH + "/{id}", productJpa.getId())
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("id"))
                    .body("$", hasKey("name"))
                    .body("$", hasKey("description"))
                    .body("$", hasKey("price"))
                    .body("$", hasKey("category"))
                    .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductSaveSchema.json"));
        }

        @Test
        void mustThrowExceptionIdNotFoundOnUpdateProduct() {
            final String name = "Product 1";
            final String description = "Description 1";
            final Double price = 4.99;
            final String category = "DRINK";
            productJpaRepository.save(ProductTestUtil.generateJpa(name, description, category, price));

            ProductDTO productDTO = ProductTestUtil.generateDTO(name, description, category, price);
            final Long nonexistentProductId = 0L;

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productDTO)
            .when()
                    .put(PATH + "/{id}", nonexistentProductId)
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(equalTo("Produto não encontrado"));
        }

        @Test
        void mustDeleteProduct() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 4.99));

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .delete(PATH + "/{id}", productJpa.getId())
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductDeleteSchema.json"));
        }

        @Test
        void mustThrowExceptionIdNotFoundOnDeleteProduct() {
            productJpaRepository.save(ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 4.99));

            final Long nonexistentProductId = 0L;

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .delete(PATH + "/{id}", nonexistentProductId)
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    class Read {

        @Test
        void mustFindAllProducts() {
            productJpaRepository.save(ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 5.0));
            productJpaRepository.save(ProductTestUtil.generateJpa("Product 2", "Description 2", "DRINK", 10.0));
            productJpaRepository.save(ProductTestUtil.generateJpa("Product 3", "Description 3", "SNACK", 15.0));

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .get(PATH + "/")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductListSchema.json"));
        }

        @Test
        void mustFindAllProductByCategory() {
            productJpaRepository.save(ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 5.0));
            productJpaRepository.save(ProductTestUtil.generateJpa("Product 2", "Description 2", "DRINK", 10.0));
            productJpaRepository.save(ProductTestUtil.generateJpa("Product 3", "Description 3", "SNACK", 15.0));

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .get(PATH + "/listByCategory/{category}", "DRINK")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductListSchema.json"));
        }
    }
}

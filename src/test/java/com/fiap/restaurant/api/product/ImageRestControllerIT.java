package com.fiap.restaurant.api.product;

import com.fiap.restaurant.external.db.product.ImageJpa;
import com.fiap.restaurant.external.db.product.ImageJpaRepository;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.types.dto.product.SaveProductImageDTO;
import com.fiap.restaurant.types.dto.product.UpdateImageDTO;
import com.fiap.restaurant.util.ImageTestUtil;
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
public class ImageRestControllerIT {

    private static final String PATH = "/image";
    private static final String SCHEMA_LOCATION = "./schemas/api/product";

    @Autowired
    private ImageJpaRepository imageJpaRepository;

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
        imageJpaRepository.deleteAll();
        productJpaRepository.deleteAll();
    }

    @Nested
    class Write {

        @Test
        void mustSaveProductImage() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 4.99));

            SaveProductImageDTO saveProductImageDTO = ImageTestUtil.generateSaveProductImageDTO(productJpa.getId(), "http://image.test");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(saveProductImageDTO)
                    .when()
            .post(PATH + "/")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("id"))
                    .body("$", hasKey("src"))
                    .body("$", hasKey("product"))
                    .body("$", hasKey("item"))
                    .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ImageSaveSchema.json"));
        }

        @Test
        void mustThrowExceptionProductNotFoundOnSaveProductImage() {
            final Long nonexistentProductId = 0L;
            SaveProductImageDTO saveProductImageDTO = ImageTestUtil.generateSaveProductImageDTO(nonexistentProductId, "http://image.test");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(saveProductImageDTO)
            .when()
                    .post(PATH + "/")
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(equalTo("Produto não encontrado."));
        }

        @Test
        void mustUpdateImage() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 4.99));
            ImageJpa imageJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image.test"));

            UpdateImageDTO updateImageDTO = ImageTestUtil.generateUpdateImageDTO("http://image2.test");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(updateImageDTO)
            .when()
                    .put(PATH + "/{id}", imageJpa.getId())
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("id"))
                    .body("$", hasKey("src"))
                    .body("$", hasKey("product"))
                    .body("$", hasKey("item"))
                    .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ImageSaveSchema.json"));
        }

        @Test
        void mustThrowExceptionImageNotFoundOnUpdateImage() {
            final Long nonexistentImageId = 0L;
            UpdateImageDTO updateImageDTO = ImageTestUtil.generateUpdateImageDTO("http://image2.test");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(updateImageDTO)
            .when()
                    .put(PATH + "/{id}", nonexistentImageId)
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(equalTo("Imagem não encontrada"));
        }

        @Test
        void mustDeleteImage() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 4.99));
            ImageJpa imageJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image.test"));

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .delete(PATH + "/{id}", imageJpa.getId())
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ImageDeleteSchema.json"));
        }

        @Test
        void mustThrowExceptionImageNotFoundOnDeleteImage() {
            final Long nonexistentImageId = 0L;

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .delete(PATH + "/{id}", nonexistentImageId)
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(equalTo("Imagem não encontrada"));
        }
    }

    @Nested
    class Read {

        @Test
        void mustFindAllImageByProductId() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 4.99));
            imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image1.test"));
            imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image2.test"));

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .get(PATH + "/productId={productId}", productJpa.getId())
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ImageListSchema.json"));
        }
    }
}

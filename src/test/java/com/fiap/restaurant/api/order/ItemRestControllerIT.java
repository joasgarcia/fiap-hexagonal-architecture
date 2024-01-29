package com.fiap.restaurant.api.order;

import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.order.ItemJpaRepository;
import com.fiap.restaurant.external.db.order.ItemProductRepository;
import com.fiap.restaurant.external.db.product.ImageJpaRepository;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.types.dto.IdDTO;
import com.fiap.restaurant.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.types.dto.product.ImageSrcDTO;
import com.fiap.restaurant.util.ImageTestUtil;
import com.fiap.restaurant.util.ItemProductTestUtil;
import com.fiap.restaurant.util.ItemTestUtil;
import com.fiap.restaurant.util.ProductTestUtil;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ItemRestControllerIT {

    private static final String PATH = "/item";
    private static final String SCHEMA_LOCATION = "./schemas/api/order";

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ItemProductRepository itemProductRepository;

    @Autowired
    private ImageJpaRepository imageJpaRepository;

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
        itemProductRepository.deleteAll();
        productJpaRepository.deleteAll();
        itemJpaRepository.deleteAll();
    }

    @Test
    void mustSaveItem() {
        ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));

        SaveItemDTO saveItemDTO = ItemTestUtil.generateSaveItemDTO("Item 1", "Item Description 1", 19.9);
        saveItemDTO.getProductIdList().add(new IdDTO(productJpa.getId()));
        saveItemDTO.getImageSrcList().add(new ImageSrcDTO("http://image1.test"));
        saveItemDTO.getImageSrcList().add(new ImageSrcDTO("http://image2.test"));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(saveItemDTO)
        .when()
                .post(PATH + "/")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ItemSaveSchema.json"));
    }

    @Test
    void mustThrowExceptionProductListNotFoundOnSaveItem() {
        SaveItemDTO saveItemDTO = ItemTestUtil.generateSaveItemDTO("Item 1", "Item Description 1", 19.9);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(saveItemDTO)
        .when()
                .post(PATH + "/")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("O(s) produto(s) do item não informado(s)"));
    }

    @Test
    void mustDeleteItem() {
        ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
        ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 6.5));
        itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa));
        imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image.test"));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
                .delete(PATH + "/{id}", itemJpa.getId())
        .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ItemDeleteSchema.json"));
    }

    @Test
    void mustThrowExceptionIdNotFoundOnDeleteItem() {
        final Long nonexistentItemId = 0L;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
                .delete(PATH + "/{id}", nonexistentItemId)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Item não encontrado"));
    }
}

package com.fiap.restaurant.api.order;

import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.customer.CustomerJpaRepository;
import com.fiap.restaurant.external.db.order.*;
import com.fiap.restaurant.external.db.product.ImageJpaRepository;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.types.dto.order.UpdateOrderStatusDTO;
import com.fiap.restaurant.types.dto.order.UpdatePaymentStatusDTO;
import com.fiap.restaurant.util.*;
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

import java.util.Date;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class OrderRestControllerIT {

    private static final String PATH = "/order";
    private static final String SCHEMA_LOCATION = "./schemas/api/order";

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ItemProductRepository itemProductRepository;

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private OrderItemJpaRepository orderItemJpaRepository;

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
        orderItemJpaRepository.deleteAll();
        itemJpaRepository.deleteAll();
        orderJpaRepository.deleteAll();
        customerJpaRepository.deleteAll();
    }

    @Test
    void mustUpdateOrderStatus() {
        CustomerJpa customerJpa = customerJpaRepository.save(CustomerTestUtil.generateJpa("John User", "johndoe@email.com", CustomerTestUtil.randomCpf()));
        ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
        ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 6.5));
        itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa));
        imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image.test"));
        OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new Date()));
        orderItemJpaRepository.save(OrderItemTestUtil.generateJpa(orderJpa, itemJpa, "Observation"));

        UpdateOrderStatusDTO updateOrderStatusDTO = OrderTestUtil.generateUpdateStatusDTO(OrderStatus.READY);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateOrderStatusDTO)
        .when()
                .post(PATH + "/{id}/status", orderJpa.getId())
        .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/OrderUpdateStatusSchema.json"));
    }

    @Test
    void mustThrowExceptionOrderNotFoundOnUpdateOrderStatus() {
        final Long nonexistentOrderId = 0L;
        UpdateOrderStatusDTO updateOrderStatusDTO = OrderTestUtil.generateUpdateStatusDTO(OrderStatus.READY);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateOrderStatusDTO)
        .when()
                .post(PATH + "/{id}/status", nonexistentOrderId)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Pedido não encontrado"));
    }

    @Test
    void mustUpdateOrderPaymentStatus() {
        CustomerJpa customerJpa = customerJpaRepository.save(CustomerTestUtil.generateJpa("John User", "johndoe@email.com", CustomerTestUtil.randomCpf()));
        ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
        ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 6.5));
        itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa));
        imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image.test"));
        OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new Date()));
        orderItemJpaRepository.save(OrderItemTestUtil.generateJpa(orderJpa, itemJpa, "Observation"));

        UpdatePaymentStatusDTO updatePaymentStatusDTO = OrderTestUtil.generateUpdatePaymentStatusDTO(orderJpa.getId(), OrderPaymentStatus.APPROVED);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updatePaymentStatusDTO)
        .when()
                .post(PATH + "/webhookPaymentStatus")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/OrderUpdatePaymentStatusSchema.json"));
    }

    @Test
    void mustThrowExceptionOrderNotFoundOnUpdateOrderPaymentStatus() {
        final Long nonexistentOrderId = 0L;
        UpdatePaymentStatusDTO updatePaymentStatusDTO = OrderTestUtil.generateUpdatePaymentStatusDTO(nonexistentOrderId, OrderPaymentStatus.APPROVED);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updatePaymentStatusDTO)
        .when()
                .post(PATH + "/webhookPaymentStatus")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Pedido não encontrado"));
    }

    @Test
    void mustFindOrderById() {
        CustomerJpa customerJpa = customerJpaRepository.save(CustomerTestUtil.generateJpa("John User", "johndoe@email.com", CustomerTestUtil.randomCpf()));
        ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
        ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 6.5));
        itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa));
        imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image.test"));
        OrderJpa orderJpa = orderJpaRepository.save(OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new Date()));
        orderItemJpaRepository.save(OrderItemTestUtil.generateJpa(orderJpa, itemJpa, "Observation"));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
                .get(PATH + "/{id}", orderJpa.getId())
        .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/OrderFindSchema.json"));
    }

    @Test
    void mustThrowExceptionOrderIdNotFound() {
        final Long nonexistentOrderId = 0L;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
                .get(PATH + "/{id}", nonexistentOrderId)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Pedido não encontrado"));
    }
}

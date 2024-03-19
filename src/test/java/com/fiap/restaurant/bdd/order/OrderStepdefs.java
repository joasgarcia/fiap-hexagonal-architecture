package com.fiap.restaurant.bdd.order;

import com.fiap.restaurant.bdd.customer.CustomerStepdefs;
import com.fiap.restaurant.bdd.product.ProductStepdefs;
import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.types.dto.IdDTO;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;
import com.fiap.restaurant.types.dto.order.UpdateOrderStatusDTO;
import com.fiap.restaurant.types.dto.order.UpdatePaymentStatusDTO;
import com.fiap.restaurant.types.dto.product.ProductDTO;
import com.fiap.restaurant.util.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static com.fiap.restaurant.bdd.RequestHelper.DEFAULT_CONTENT_TYPE;
import static com.fiap.restaurant.bdd.RequestHelper.ENDPOINT_HOST;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class OrderStepdefs {

    private static final String ENDPOINT = ENDPOINT_HOST + "/order";
    private static final String SCHEMA_LOCATION = "./schemas/api/order";

    private Response response;
    private SaveCustomerDTO saveCustomerDTO;
    private Integer savedProductId;
    private Integer savedItemId;
    private Integer savedOrderId;

    @Given("the customer is already registered")
    public void theCustomerIsAlreadyRegistered() {
        String customerCpf = CustomerTestUtil.randomCpf();
        String customerEmail = customerCpf + "@email.com";

        saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", customerEmail, customerCpf);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveCustomerDTO)
                .when().post(CustomerStepdefs.ENDPOINT + "/");
    }

    @And("the order product is already registered")
    public void theProductIsAlreadyRegistered() {
        ProductDTO productDTO = ProductTestUtil.generateDTO("Product 1", "Description 1", "DRINK", 7.5);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(productDTO)
                .when().post(ProductStepdefs.ENDPOINT + "/");

        savedProductId = response.getBody().jsonPath().get("id");
    }

    @And("the order item is already registered")
    public void theItemIsAlreadyRegistered() {
        List<IdDTO> productIdList = new ArrayList<>();
        productIdList.add(new IdDTO(savedProductId.longValue()));

        SaveItemDTO saveItemDTO = ItemTestUtil.generateSaveItemDTO("Item 1", "Description 1", 19.9, productIdList, new ArrayList<>());

        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveItemDTO)
                .when().post(ItemStepdefs.ENDPOINT + "/");

        savedItemId = response.getBody().jsonPath().get("id");
    }

    @When("save a new order")
    public void saveANewOrder() {
        SaveOrderDTO saveOrderDTO = OrderTestUtil.generateSaveDTO(saveCustomerDTO.getCpf(), OrderItemTestUtil.generateDTO(savedItemId.longValue(), "Observation Item 1"));

        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveOrderDTO)
                .when().post(ENDPOINT + "/checkout");

        savedOrderId = response.getBody().jsonPath().get("id");
    }

    @Then("the order is successfully saved")
    public void theOrderIsSuccessfullySaved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/OrderSaveSchema.json"));
    }

    @And("the order is already registered")
    public void theOrderIsAlreadyRegistered() {
        saveANewOrder();
    }

    @When("an order by id is requested")
    public void anOrderByIdIsRequested() {
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().get(ENDPOINT + "/{id}", savedOrderId);
    }

    @Then("the order is successfully displayed")
    public void theOrderIsSuccessfullyDisplayed() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/OrderFindSchema.json"));
    }

    @When("an non existent order by id is requested")
    public void anNonExistentOrderByIdIsRequested() {
        final Long nonexistentOrderId = 0L;

        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().get(ENDPOINT + "/{id}", nonexistentOrderId);
    }

    @Then("an error is displayed indicating that order not found")
    public void anErrorIsDisplayedIndicatingThatOrderNotFound() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Pedido não encontrado"));
    }
}

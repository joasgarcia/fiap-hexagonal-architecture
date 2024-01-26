package com.fiap.restaurant.bdd;

import com.fiap.restaurant.types.dto.product.ProductDTO;
import com.fiap.restaurant.util.ProductTestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static com.fiap.restaurant.api.RequestHelper.DEFAULT_CONTENT_TYPE;
import static com.fiap.restaurant.api.RequestHelper.ENDPOINT_HOST;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class ProductStepdefs {

    private static final String ENDPOINT = ENDPOINT_HOST + "/product";
    private static final String SCHEMA_LOCATION = "./schemas/api/product";

    private Response response;
    private ProductDTO productDTO;
    private Integer savedProductId;

    @When("save a new product")
    public Integer saveANewProduct() {
        productDTO = ProductTestUtil.generateDTO("Product 1", "Description 1", "DRINK", 7.5);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(productDTO)
                .when().post(ENDPOINT + "/");

        return response.getBody().jsonPath().get("id");
    }

    @Then("the product is successfully saved")
    public void theProductIsSuccessfullySaved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductSaveSchema.json"));

//        savedProductId = response.getBody().jsonPath().get("id");
    }

    @Given("the product already registered")
    public void theProductAlreadyRegistered() {
        savedProductId = saveANewProduct();
    }

    @When("update a product")
    public void updateAProduct() {
        productDTO = ProductTestUtil.generateDTO("Product 1", "Description 1", "DRINK", 12.5);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(productDTO)
                .when().put(ENDPOINT + "/{id}", savedProductId);
    }

    @Then("the product is successfully updated")
    public void theProductIsSuccessfullyUpdated() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductSaveSchema.json"));
    }

    @When("update a nonexistent product")
    public void updateAnInexistentProduct() {
        final Long nonexistentProductId = 0L;
        productDTO = ProductTestUtil.generateDTO("Product 1", "Description 1", "DRINK", 12.5);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(productDTO)
                .when().put(ENDPOINT + "/{id}", nonexistentProductId);
    }

    @Then("an error is displayed indicating that product not found")
    public void anErrorIsDisplayedIndicatingThatProductNotFound() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Produto não encontrado"));
    }
}

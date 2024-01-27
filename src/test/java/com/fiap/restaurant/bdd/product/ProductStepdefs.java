package com.fiap.restaurant.bdd.product;

import com.fiap.restaurant.types.dto.product.ProductDTO;
import com.fiap.restaurant.util.ProductTestUtil;
import io.cucumber.java.en.And;
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

    public static final String ENDPOINT = ENDPOINT_HOST + "/product";
    private static final String SCHEMA_LOCATION = "./schemas/api/product";

    private Response response;
    private ProductDTO productDTO;
    private Integer savedProductId;

    @When("save a new product")
    public void saveANewProduct() {
        productDTO = ProductTestUtil.generateDTO("Product 1", "Description 1", "DRINK", 7.5);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(productDTO)
                .when().post(ENDPOINT + "/");

        savedProductId = response.getBody().jsonPath().get("id");
    }

    @Then("the product is successfully saved")
    public void theProductIsSuccessfullySaved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductSaveSchema.json"));
    }

    @Given("the product already registered")
    public void theProductAlreadyRegistered() {
        saveANewProduct();
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
    public void updateANonexistentProduct() {
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

    @When("delete a product")
    public void deleteAProduct() {
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().delete(ENDPOINT + "/{id}", savedProductId);
    }

    @Then("the product is successfully deleted")
    public void theProductIsSuccessfullyDeleted() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductDeleteSchema.json"))
                .body(equalTo("true"));
    }

    @When("delete a nonexistent product")
    public void deleteANonexistentProduct() {
        final Long nonexistentProductId = 0L;
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().delete(ENDPOINT + "/{id}", nonexistentProductId);
    }

    @And("a another product is saved")
    public void aAnotherProductIsSaved() {
        saveANewProduct();
    }

    @When("a product list is requested")
    public void aProductListIsRequested() {
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().get(ENDPOINT + "/");
    }

    @Then("the product list is displayed")
    public void theProductListIsDisplayed() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ProductListSchema.json"));
    }

    @When("all products by category is requested")
    public void allProductsByCategoryIsRequested() {
        final String category = "DRINK";
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().get(ENDPOINT + "/listByCategory/${category}", category);
    }

}

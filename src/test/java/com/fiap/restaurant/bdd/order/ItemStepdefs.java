package com.fiap.restaurant.bdd.order;

import com.fiap.restaurant.bdd.product.ProductStepdefs;
import com.fiap.restaurant.types.dto.IdDTO;
import com.fiap.restaurant.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.types.dto.product.ProductDTO;
import com.fiap.restaurant.util.ItemTestUtil;
import com.fiap.restaurant.util.ProductTestUtil;
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

public class ItemStepdefs {

    public static final String ENDPOINT = ENDPOINT_HOST + "/item";
    private static final String SCHEMA_LOCATION = "./schemas/api/order";

    private Response response;
    private SaveItemDTO saveItemDTO;
    private Integer savedProductId;
    private Integer savedItemId;

    @Given("the product is already registered")
    public void theProductIsAlreadyRegistered() {
        ProductDTO productDTO = ProductTestUtil.generateDTO("Product 1", "Description 1", "DRINK", 7.5);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(productDTO)
                .when().post(ProductStepdefs.ENDPOINT + "/");

        savedProductId = response.getBody().jsonPath().get("id");
    }

    @When("save a new item")
    public void saveANewItem() {
        List<IdDTO> productIdList = new ArrayList<>();
        productIdList.add(new IdDTO(savedProductId.longValue()));

        saveItemDTO = ItemTestUtil.generateSaveItemDTO("Item 1", "Description 1", 19.9, productIdList, new ArrayList<>());

        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveItemDTO)
                .when().post(ENDPOINT + "/");

        savedItemId = response.getBody().jsonPath().get("id");
    }

    @Then("the item is successfully saved")
    public void theItemIsSuccessfullySaved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ItemSaveSchema.json"));
    }

    @When("save a new item with no product list")
    public void saveANewItemWithNoProductList() {
        saveItemDTO = ItemTestUtil.generateSaveItemDTO("Item 1", "Description 1", 19.9);

        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveItemDTO)
                .when().post(ENDPOINT + "/");
    }

    @Then("an error is displayed indicating that product list not found")
    public void anErrorIsDisplayedIndicatingThatProductListNotFound() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("O(s) produto(s) do item não informado(s)"));
    }

    @And("the item is already registered")
    public void theItemIsAlreadyRegistered() {
        saveANewItem();
    }

    @When("delete an existent item")
    public void deleteAnExistentItem() {
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().delete(ENDPOINT + "/{id}", savedItemId);
    }

    @Then("the item is successfully deleted")
    public void theItemIsSuccessfullyDeleted() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ItemDeleteSchema.json"))
                .body(equalTo("true"));
    }

    @When("delete a nonexistent item")
    public void deleteANonexistentItem() {
        final Long nonexistentItemId = 0L;

        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().delete(ENDPOINT + "/{id}", nonexistentItemId);
    }

    @Then("an error is displayed indicating that item not found")
    public void anErrorIsDisplayedIndicatingThatItemNotFound() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Item não encontrado"));
    }
}

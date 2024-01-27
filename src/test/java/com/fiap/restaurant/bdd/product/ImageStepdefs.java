package com.fiap.restaurant.bdd.product;

import com.fiap.restaurant.types.dto.product.ProductDTO;
import com.fiap.restaurant.types.dto.product.SaveProductImageDTO;
import com.fiap.restaurant.types.dto.product.UpdateImageDTO;
import com.fiap.restaurant.util.ImageTestUtil;
import com.fiap.restaurant.util.ProductTestUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static com.fiap.restaurant.bdd.RequestHelper.DEFAULT_CONTENT_TYPE;
import static com.fiap.restaurant.bdd.RequestHelper.ENDPOINT_HOST;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class ImageStepdefs {

    private static final String ENDPOINT = ENDPOINT_HOST + "/image";
    private static final String SCHEMA_LOCATION = "./schemas/api/product";

    private Response response;
    private SaveProductImageDTO saveProductImageDTO;
    private UpdateImageDTO updateImageDTO;
    private Integer savedImageId;
    private Integer savedProductId;

    @Given("the product image is already registered")
    public void theProductIsAlreadyRegistered() {
        ProductDTO productDTO = ProductTestUtil.generateDTO("Product 1", "Description 1", "DRINK", 7.5);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(productDTO)
                .when().post(ProductStepdefs.ENDPOINT + "/");

        savedProductId = response.getBody().jsonPath().get("id");
    }

    @When("save a new image")
    public void saveANewImage() {
        saveProductImageDTO = ImageTestUtil.generateSaveProductImageDTO(savedProductId.longValue(), "http://image.test");

        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveProductImageDTO)
                .when().post(ENDPOINT + "/");

        savedImageId = response.getBody().jsonPath().get("id");
    }

    @Then("the image is successfully saved")
    public void theImageIsSuccessfullySaved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ImageSaveSchema.json"));
    }

    @And("the image is already registered")
    public void theImageIsAlreadyRegistered() {
        saveANewImage();
    }

    @When("update a image")
    public void updateAImage() {
        updateImageDTO = ImageTestUtil.generateUpdateImageDTO("http://image2.test");

        response = given().contentType(DEFAULT_CONTENT_TYPE).body(updateImageDTO)
                .when().put(ENDPOINT + "/{id}", savedImageId);
    }

    @Then("the image is successfully updated")
    public void theImageIsSuccessfullyUpdated() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ImageSaveSchema.json"));
    }

    @When("update a nonexistent image")
    public void updateANonexistentImage() {
        updateImageDTO = ImageTestUtil.generateUpdateImageDTO("http://image2.test");
        final Long nonexistentImageId = 0L;

        response = given().contentType(DEFAULT_CONTENT_TYPE).body(updateImageDTO)
                .when().put(ENDPOINT + "/{id}", nonexistentImageId);
    }

    @Then("an error is displayed indicating that image not found")
    public void anErrorIsDisplayedIndicatingThatImageNotFound() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Imagem não encontrada"));
    }

    @When("delete a image")
    public void deleteAImage() {
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().delete(ENDPOINT + "/{id}", savedImageId);
    }

    @Then("the image is successfully deleted")
    public void theImageIsSuccessfullyDeleted() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ImageDeleteSchema.json"))
                .body(equalTo("true"));
    }

    @When("delete a nonexistent image")
    public void deleteANonexistentImage() {
        final Long nonexistentImageId = 0L;

        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().delete(ENDPOINT + "/{id}", nonexistentImageId);
    }

    @And("save another new image")
    public void saveAnotherNewImage() {
        saveProductImageDTO = ImageTestUtil.generateSaveProductImageDTO(savedProductId.longValue(), "http://image2.test");

        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveProductImageDTO)
                .when().post(ENDPOINT + "/");
    }

    @When("all images by product id is requested")
    public void allImagesByProductIdIsRequested() {
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().get(ENDPOINT + "/productId={productId}", savedProductId);
    }

    @Then("the image list is displayed")
    public void theImageListIsDisplayed() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/ImageListSchema.json"));
    }
}

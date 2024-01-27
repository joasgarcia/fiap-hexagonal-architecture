package com.fiap.restaurant.bdd.customer;

import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.util.CustomerTestUtil;
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

public class CustomerStepdefs {

    public static final String ENDPOINT = ENDPOINT_HOST + "/customer";
    private static final String SCHEMA_LOCATION = "./schemas/api/customer";

    private SaveCustomerDTO saveCustomerDTO;
    private Response response;
    private String customerCpf;
    private String customerEmail;

    @When("save a new customer")
    public void saveANewCustomer() {
        customerCpf = CustomerTestUtil.randomCpf();
        customerEmail = customerCpf + "@email.com";

        saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", customerEmail, customerCpf);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveCustomerDTO)
                .when().post(ENDPOINT + "/");
    }

    @Then("the customer is successfully saved")
    public void theCustomerIsSuccessfullySaved() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/CustomerSaveSchema.json"))
                .body(equalTo("true"));
    }

    @Given("the customer already registered")
    public void theCustomerAlreadyRegistered() {
        saveANewCustomer();
    }

    @When("do a search by CPF")
    public void doASearchByCPF() {
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().get(ENDPOINT + "/{cpf}", saveCustomerDTO.getCpf());
    }

    @Then("the customer is displayed")
    public void theCustomerIsDisplayed() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/CustomerFindByCpfSchema.json"));
    }

    @When("save another customer with same e-mail")
    public void saveAnotherCustomerWithSameEMail() {
        saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", customerEmail, CustomerTestUtil.CPF);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveCustomerDTO)
                .when().post(ENDPOINT + "/");
    }

    @Then("an error is displayed indicating that e-mail already registered")
    public void anErrorIsDisplayedIndicatingThatEMailAlreadyRegistered() {
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Cliente já cadastrado com o e-mail informado"));
    }

    @When("save another customer with same CPF")
    public void saveAnotherCustomerWithSameCPF() {
        saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", "johndoe@test.com", customerCpf);
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveCustomerDTO)
                .when().post(ENDPOINT + "/");
    }

    @Then("an error is displayed indicating that CPF already registered")
    public void anErrorIsDisplayedIndicatingThatCPFAlreadyRegistered() {
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Cliente já cadastrado com o CPF informado"));
    }
}

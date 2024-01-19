package com.fiap.restaurant.bdd;

import com.fiap.restaurant.api.customer.CustomerRequestHelper;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.fiap.restaurant.api.RequestHelper.DEFAULT_CONTENT_TYPE;
import static com.fiap.restaurant.api.RequestHelper.ENDPOINT_HOST;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CustomerStepdefs {

    private static final String ENDPOINT = ENDPOINT_HOST + "/customer";
    private static final String SCHEMA_LOCATION = "./schemas/api/customer";

    private SaveCustomerDTO saveCustomerDTO;
    private Response response;

    @Quando("registrar um novo cliente")
    public void registrarUmNovoCliente() {
        saveCustomerDTO = CustomerRequestHelper.buildSaveRequest();
        response = given().contentType(DEFAULT_CONTENT_TYPE).body(saveCustomerDTO)
                .when().post(ENDPOINT + "/");
    }

    @Então("o cliente é registrado com sucesso")
    public void oClienteÉRegistradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/CustomerSaveSchema.json"))
                .body(equalTo("true"));
    }

    @Dado("que o cliente já foi registrado")
    public void queOClienteJáFoiRegistrado() {
        registrarUmNovoCliente();
    }

    @Quando("realizar uma busca por CPF")
    public void realizarUmaBuscaPorCPF() {
        response = given().contentType(DEFAULT_CONTENT_TYPE)
                .when().get(ENDPOINT + "/{cpf}", saveCustomerDTO.getCpf());
    }

    @Então("o cliente é exibido")
    public void oClienteÉExibido() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath(SCHEMA_LOCATION + "/CustomerFindByCpfSchema.json"));

    }
}

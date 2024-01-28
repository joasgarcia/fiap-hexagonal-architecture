package com.fiap.restaurant.api.customer;

import com.fiap.restaurant.external.db.customer.CustomerJpaRepository;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.util.CustomerTestUtil;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CustomerRestControllerIT {

    public static final String PATH = "/customer";

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterEach
    public void tearDown() {
        customerJpaRepository.deleteAll();
    }

    @Nested
    class Write {

        @Test
        void mustSaveCustomer() {
            SaveCustomerDTO saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", "johndoe@email.com", CustomerTestUtil.randomCpf());

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(saveCustomerDTO)
            .when()
                    .post(PATH + "/")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(equalTo("true"));
        }

        @Test
        void mustThrowExceptionEmailAlreadyInUse() {
            final String customerEmail = "johndoe@email.com";
            customerJpaRepository.save(CustomerTestUtil.generateJpa("John User", customerEmail, CustomerTestUtil.randomCpf()));

            SaveCustomerDTO saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", customerEmail, CustomerTestUtil.randomCpf());

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(saveCustomerDTO)
            .when()
                    .post(PATH + "/")
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("Cliente já cadastrado com o e-mail informado"));
        }

        @Test
        void mustThrowExceptionCpfAlreadyInUse() {
            final String customerCpf = CustomerTestUtil.randomCpf();
            customerJpaRepository.save(CustomerTestUtil.generateJpa("John User", "johnuser@email.com", customerCpf));

            SaveCustomerDTO saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", "johndoe@email.com", customerCpf);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(saveCustomerDTO)
            .when()
                    .post(PATH + "/")
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("Cliente já cadastrado com o CPF informado"));
        }
    }

    @Nested
    class Read {

        @Test
        void mustFindCustomerByCpf() {
            final String customerCpf = CustomerTestUtil.randomCpf();
            customerJpaRepository.save(CustomerTestUtil.generateJpa("John User", "johnuser@email.com", customerCpf));

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .get(PATH + "/{cpf}", customerCpf)
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("id"))
                    .body("$", hasKey("name"))
                    .body("$", hasKey("email"))
                    .body("$", hasKey("cpf"));
        }

        @Test
        void mustThrowExceptionCustomerNotFoundByCpf() {
            final String customerCpf = CustomerTestUtil.randomCpf();
            customerJpaRepository.save(CustomerTestUtil.generateJpa("John User", "johnuser@email.com", CustomerTestUtil.randomCpf()));

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                    .get(PATH + "/{cpf}", customerCpf)
            .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body(equalTo("Cliente não encontrado"));
        }
    }
}

package com.fiap.restaurant.usecase.customer;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.customer.CustomerJpaRepository;
import com.fiap.restaurant.gateway.customer.CustomerGateway;
import com.fiap.restaurant.gateway.customer.ICustomerGateway;
import com.fiap.restaurant.types.dto.customer.SaveCustomerDTO;
import com.fiap.restaurant.types.exception.BusinessException;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.util.CustomerTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class CustomerUseCaseIT {

    @Autowired
    private CustomerDatabaseConnection customerDatabaseConnection;

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveCustomer() {
            ICustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);
            SaveCustomerDTO saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", "johndoe@email.com", CustomerTestUtil.randomCpf());
            Customer customer = CustomerUseCase.save(saveCustomerDTO, customerGateway);

            assertThat(customer).isNotNull();
        }

        @Test
        @Rollback
        void mustThrowExceptionEmailAlreadyInUse() {
            ICustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);
            SaveCustomerDTO saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", "johndoe@email.com", CustomerTestUtil.randomCpf());

            Customer customer = CustomerUseCase.save(saveCustomerDTO, customerGateway);

            SaveCustomerDTO secondSaveCustomerDTO = new SaveCustomerDTO();
            saveCustomerDTO.setName("User Test");
            saveCustomerDTO.setEmail(customer.getEmail());
            saveCustomerDTO.setCpf(CustomerTestUtil.CPF);
            secondSaveCustomerDTO.setEmail(saveCustomerDTO.getEmail());

            assertThatThrownBy(() -> CustomerUseCase.save(secondSaveCustomerDTO, customerGateway))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("Cliente já cadastrado com o e-mail informado");
        }

        @Test
        @Rollback
        void mustThrowExceptionCpfAlreadyInUse() {
            ICustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);
            SaveCustomerDTO saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", "johndoe@email.com", CustomerTestUtil.randomCpf());

            Customer customer = CustomerUseCase.save(saveCustomerDTO, customerGateway);

            SaveCustomerDTO secondSaveCustomerDTO = new SaveCustomerDTO();
            secondSaveCustomerDTO.setName("User Test");
            secondSaveCustomerDTO.setEmail("user.test@email.com");
            secondSaveCustomerDTO.setCpf(customer.getCpf());

            assertThatThrownBy(() -> CustomerUseCase.save(secondSaveCustomerDTO, customerGateway))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("Cliente já cadastrado com o CPF informado");
        }

        @Test
        @Rollback
        void mustAnonymizeCustomer() {
            ICustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);

            final String customerName = "John Doe";
            final String customerEmail = "johndoe@email.com";
            final String customerCpf = CustomerTestUtil.randomCpf();

            CustomerJpa customerJpa = CustomerTestUtil.generateJpa(customerName, customerEmail, customerCpf);
            customerJpa = customerJpaRepository.save(customerJpa);

            CustomerUseCase.anonymize(customerCpf, customerGateway);

            customerJpa = customerJpaRepository.getReferenceById(customerJpa.getId());

            assertThat(customerJpa).isNotNull();
            assertThat(customerJpa.getName()).isNotEqualTo(customerName);
            assertThat(customerJpa.getEmail()).isNotEqualTo(customerEmail);
            assertThat(customerJpa.getCpf()).isNotEqualTo(customerCpf);
        }

        @Test
        @Rollback
        void mustThrowExceptionCustomerNotFoundOnAnonymizeByCpf() {
            ICustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);
            final String customerCpf = CustomerTestUtil.CPF;

            assertThatThrownBy(() -> CustomerUseCase.anonymize(customerCpf, customerGateway))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Cliente não encontrado");
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustFindCustomerByCpf() {
            ICustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);
            SaveCustomerDTO saveCustomerDTO = CustomerTestUtil.generateSaveCustomerDTO("John Doe", "johndoe@email.com", CustomerTestUtil.randomCpf());
            CustomerUseCase.save(saveCustomerDTO, customerGateway);

            Customer customer = CustomerUseCase.findByCpf(saveCustomerDTO.getCpf(), customerGateway);
            assertThat(customer).isNotNull();
            assertThat(customer.getEmail()).isEqualTo(saveCustomerDTO.getEmail());
        }

        @Test
        @Rollback
        void mustThrowExceptionCustomerNotFoundByCpf() {
            ICustomerGateway customerGateway = new CustomerGateway(customerDatabaseConnection);
            final String customerCpf = CustomerTestUtil.CPF;

            assertThatThrownBy(() -> CustomerUseCase.findByCpf(customerCpf, customerGateway))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Cliente não encontrado");
        }
    }
}

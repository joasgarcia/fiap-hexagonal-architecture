package com.fiap.restaurant.usecase.product;

import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.gateway.product.IProductGateway;
import com.fiap.restaurant.gateway.product.ProductGateway;
import com.fiap.restaurant.types.dto.product.ProductDTO;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.util.ProductTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ProductUseCaseIT {

    @Autowired
    private ProductDatabaseConnection productDatabaseConnection;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private IProductGateway productGateway;

    @BeforeEach
    void setup() {
        this.productGateway = new ProductGateway(productDatabaseConnection);
    }

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveProduct() {
            ProductDTO productDTO = ProductTestUtil.generateDTO("Drink 1", "Description 1", "DRINK", 7.5);

            Product product = ProductUseCase.save(productDTO, productGateway);
            assertThat(product).isNotNull();
            assertThat(product.getName()).isEqualTo(productDTO.getName());
            assertThat(product.getDescription()).isEqualTo(productDTO.getDescription());
            assertThat(product.getCategory()).isEqualTo(productDTO.getCategory());
            assertThat(product.getPrice()).isEqualTo(productDTO.getPrice());
        }

        @Test
        @Rollback
        void mustUpdateProduct() {
            ProductDTO productDTO = ProductTestUtil.generateDTO("Drink 1", "Description 1", "DRINK", 7.5);
            Product product = ProductUseCase.save(productDTO, productGateway);

            final String productDescription = "Product Description Update";
            productDTO.setDescription(productDescription);

            Product updatedProduct = ProductUseCase.update(product.getId(), productDTO, productGateway);
            assertThat(updatedProduct).isNotNull();
            assertThat(updatedProduct.getName()).isEqualTo(productDTO.getName());
            assertThat(updatedProduct.getDescription()).isEqualTo(productDescription);
            assertThat(updatedProduct.getCategory()).isEqualTo(productDTO.getCategory());
            assertThat(updatedProduct.getPrice()).isEqualTo(productDTO.getPrice());
        }

        @Test
        @Rollback
        void mustThrowExceptionIdNotFoundOnUpdateProduct() {
            ProductDTO productDTO = ProductTestUtil.generateDTO("Drink 1", "Description 1", "DRINK", 7.5);
            ProductUseCase.save(productDTO, productGateway);

            final Long nonexistentProductId = 0L;
            final String productDescription = "Product Description Update";
            productDTO.setDescription(productDescription);

            assertThatThrownBy(() -> ProductUseCase.update(nonexistentProductId, productDTO, productGateway))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Produto não encontrado");
        }

        @Test
        @Rollback
        void mustDeleteProduct() {
            ProductDTO productDTO = ProductTestUtil.generateDTO("Drink 1", "Description 1", "DRINK", 7.5);
            Product product = ProductUseCase.save(productDTO, productGateway);

            ProductUseCase.delete(product.getId(), productGateway);

            Optional<ProductJpa> optionalProductJpa = productJpaRepository.findById(product.getId());
            assertThat(optionalProductJpa).isNotPresent();
        }

        @Test
        @Rollback
        void mustThrowExceptionIdNotFoundOnDeleteProduct() {
            ProductDTO productDTO = ProductTestUtil.generateDTO("Drink 1", "Description 1", "DRINK", 7.5);
            Product product = ProductUseCase.save(productDTO, productGateway);

            final Long nonexistentProductId = 0L;
            assertThatThrownBy(() -> ProductUseCase.delete(nonexistentProductId, productGateway))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Produto não encontrado");
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustFindAllProducts() {
            ProductUseCase.save(ProductTestUtil.generateDTO("Drink 1", "Description 1", "DRINK", 7.5), productGateway);
            ProductUseCase.save(ProductTestUtil.generateDTO("Drink 2", "Description 2", "DRINK", 5.5), productGateway);
            ProductUseCase.save(ProductTestUtil.generateDTO("Snack 1", "Description 3", "SNACK", 10.5), productGateway);

            List<Product> productList = ProductUseCase.list(productGateway);
            assertThat(productList).isNotEmpty().hasSize(3);
        }

        @Test
        @Rollback
        void mustFindAllProductByCategory() {
            ProductUseCase.save(ProductTestUtil.generateDTO("Drink 1", "Description 1", "DRINK", 7.5), productGateway);
            ProductUseCase.save(ProductTestUtil.generateDTO("Drink 2", "Description 2", "DRINK", 5.5), productGateway);
            ProductUseCase.save(ProductTestUtil.generateDTO("Snack 1", "Description 3", "SNACK", 10.5), productGateway);

            List<Product> productDrinkList = ProductUseCase.findAllByCategory("DRINK", productGateway);
            List<Product> productSnackList = ProductUseCase.findAllByCategory("SNACK", productGateway);
            List<Product> productDessertList = ProductUseCase.findAllByCategory("DESSERT", productGateway);

            assertThat(productDrinkList).isNotEmpty().hasSize(2);
            assertThat(productSnackList).isNotEmpty().hasSize(1);
            assertThat(productDessertList).isEmpty();
        }
    }
}

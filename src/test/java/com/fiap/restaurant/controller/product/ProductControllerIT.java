package com.fiap.restaurant.controller.product;

import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.types.dto.product.ProductDTO;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.util.ProductTestUtil;
import jakarta.transaction.Transactional;
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
public class ProductControllerIT {

    @Autowired
    private ProductDatabaseConnection productDatabaseConnection;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveProduct() {
            ProductDTO productDTO = ProductTestUtil.generateDTO("Drink 1", "Description 1", "DRINK", 7.5);
            Product product = ProductController.save(productDTO, productDatabaseConnection);
            assertThat(product).isNotNull();
            assertThat(product.getName()).isEqualTo(productDTO.getName());
            assertThat(product.getDescription()).isEqualTo(productDTO.getDescription());
            assertThat(product.getPrice()).isEqualTo(productDTO.getPrice());
            assertThat(product.getCategory()).isEqualTo(productDTO.getCategory());
        }

        @Test
        @Rollback
        void mustUpdateProduct() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ProductDTO productDTO = ProductTestUtil.generateDTO("Drink", "Description", "DRINK", 10.5);

            Product product = ProductController.update(productJpa.getId(), productDTO, productDatabaseConnection);
            assertThat(product).isNotNull();
            assertThat(product.getName()).isEqualTo(productDTO.getName());
            assertThat(product.getDescription()).isEqualTo(productDTO.getDescription());
            assertThat(product.getPrice()).isEqualTo(productDTO.getPrice());
            assertThat(product.getCategory()).isEqualTo(productDTO.getCategory());
        }

        @Test
        @Rollback
        void mustThrowExceptionIdNotFoundOnUpdateProduct() {
            final Long nonexistentProductId = 0L;
            ProductDTO productDTO = ProductTestUtil.generateDTO("Drink", "Description", "DRINK", 10.5);

            assertThatThrownBy(() -> ProductController.update(nonexistentProductId, productDTO, productDatabaseConnection))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Produto não encontrado");
        }

        @Test
        @Rollback
        void mustDeleteProduct() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ProductController.delete(productJpa.getId(), productDatabaseConnection);

            Optional<ProductJpa> optionalProductJpa = productJpaRepository.findById(productJpa.getId());
            assertThat(optionalProductJpa).isNotPresent();
        }

        @Test
        @Rollback
        void mustThrowExceptionIdNotFoundOnDeleteProduct() {
            final Long nonexistentProductId = 0L;

            assertThatThrownBy(() -> ProductController.delete(nonexistentProductId, productDatabaseConnection))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Produto não encontrado");
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustListProducts() {
            productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 9.5));
            productJpaRepository.save(ProductTestUtil.generateJpa("Snack 2", "Description 3", "SNACK", 19.9));

            List<Product> productList = ProductController.list(productDatabaseConnection);
            assertThat(productList).isNotEmpty().hasSize(3);
        }

        @Test
        @Rollback
        void mustFindAllProductsByCategory() {
            final String productName1 = "Drink 1";
            final String productDescription1 = "Description 1";
            final Double productPrice1 = 7.5;
            final String productCategory1 = "DRINK";
            productJpaRepository.save(ProductTestUtil.generateJpa(productName1, productDescription1, productCategory1, productPrice1));

            final String productName2 = "Drink 2";
            final String productDescription2 = "Description 2";
            final Double productPrice2 = 9.5;
            final String productCategory2 = "DRINK";
            productJpaRepository.save(ProductTestUtil.generateJpa(productName2, productDescription2, productCategory2, productPrice2));

            final String productName3 = "Snack 1";
            final String productDescription3 = "Description 3";
            final Double productPrice3 = 19.9;
            final String productCategory3 = "SNACK";
            productJpaRepository.save(ProductTestUtil.generateJpa(productName3, productDescription3, productCategory3, productPrice3));

            List<Product> productDrinkList = ProductController.findAllByCategory("DRINK", productDatabaseConnection);
            List<Product> productSnackList = ProductController.findAllByCategory("SNACK", productDatabaseConnection);

            assertThat(productDrinkList).isNotEmpty().hasSize(2);
            assertThat(productSnackList).isNotEmpty().hasSize(1);
            assertThat(productSnackList.get(0).getName()).isEqualTo(productName3);
            assertThat(productSnackList.get(0).getDescription()).isEqualTo(productDescription3);
            assertThat(productSnackList.get(0).getCategory()).isEqualTo(productCategory3);
            assertThat(productSnackList.get(0).getPrice()).isEqualTo(productPrice3);
        }

        @Test
        @Rollback
        void mustNotFindAnyProductsByCategory() {
            List<Product> productList = ProductController.findAllByCategory("SNACK", productDatabaseConnection);
            assertThat(productList).isEmpty();
        }
    }
}

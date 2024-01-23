package com.fiap.restaurant.external.db.product;

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

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ProductJpaRepositoryIT {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveProduct() {
            ProductJpa productJpa = ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5);
            ProductJpa persistedProductJpa = productJpaRepository.save(productJpa);

            assertThat(persistedProductJpa).isNotNull();
            assertThat(persistedProductJpa.getId()).isEqualTo(productJpa.getId());
            assertThat(persistedProductJpa.getName()).isEqualTo(productJpa.getName());
            assertThat(persistedProductJpa.getDescription()).isEqualTo(productJpa.getDescription());
            assertThat(persistedProductJpa.getPrice()).isEqualTo(productJpa.getPrice());
        }

        @Test
        @Rollback
        void mustDeleteProduct() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            assertThat(productJpa).isNotNull();

            productJpaRepository.delete(productJpa);

            Optional<ProductJpa> optionalProductJpa = productJpaRepository.findById(productJpa.getId());
            assertThat(optionalProductJpa).isEmpty();
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustFindAllProducts() {
            productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 5.5));
            productJpaRepository.save(ProductTestUtil.generateJpa("Snack 1", "Description 3", "SNACK", 5.5));

            List<ProductJpa> productJpaList = productJpaRepository.findAll();
            assertThat(productJpaList).hasSize(3);
        }

        @Test
        @Rollback
        void mustFindAllProductsByCategory() {
            ProductJpa productJpa1 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ProductJpa productJpa2 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 5.5));
            ProductJpa productJpa3 = productJpaRepository.save(ProductTestUtil.generateJpa("Snack 1", "Description 3", "SNACK", 5.5));

            List<ProductJpa> productJpaList = productJpaRepository.findAllByCategory("DRINK");
            assertThat(productJpaList).isNotEmpty().hasSize(2);

            productJpaList = productJpaRepository.findAllByCategory("SNACK");
            assertThat(productJpaList).isNotEmpty().hasSize(1);
            assertThat(productJpaList.get(0).getId()).isEqualTo(productJpa3.getId());
            assertThat(productJpaList.get(0).getName()).isEqualTo(productJpa3.getName());
            assertThat(productJpaList.get(0).getDescription()).isEqualTo(productJpa3.getDescription());
            assertThat(productJpaList.get(0).getPrice()).isEqualTo(productJpa3.getPrice());
        }

        @Test
        @Rollback
        void mustFindAnyProductsByCategory() {
            productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 5.5));
            productJpaRepository.save(ProductTestUtil.generateJpa("Snack 1", "Description 3", "SNACK", 5.5));

            List<ProductJpa> productJpaList = productJpaRepository.findAllByCategory("DESSERT");
            assertThat(productJpaList).isEmpty();
        }

        @Test
        @Rollback
        void mustFindProductById() {
            ProductJpa productJpa1 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ProductJpa productJpa2 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 5.5));

            Optional<ProductJpa> optionalProductJpa1 = productJpaRepository.findById(productJpa1.getId());
            assertThat(optionalProductJpa1).isPresent();
            assertThat(optionalProductJpa1.get().getId()).isEqualTo(productJpa1.getId());
            assertThat(optionalProductJpa1.get().getName()).isEqualTo(productJpa1.getName());
            assertThat(optionalProductJpa1.get().getDescription()).isEqualTo(productJpa1.getDescription());
            assertThat(optionalProductJpa1.get().getPrice()).isEqualTo(productJpa1.getPrice());

            Optional<ProductJpa> optionalProductJpa2 = productJpaRepository.findById(productJpa2.getId());
            assertThat(optionalProductJpa2).isPresent();
            assertThat(optionalProductJpa2.get().getId()).isEqualTo(productJpa2.getId());
            assertThat(optionalProductJpa2.get().getName()).isEqualTo(productJpa2.getName());
            assertThat(optionalProductJpa2.get().getDescription()).isEqualTo(productJpa2.getDescription());
            assertThat(optionalProductJpa2.get().getPrice()).isEqualTo(productJpa2.getPrice());

            Optional<ProductJpa> optionalProductJpa3 = productJpaRepository.findById(0L);
            assertThat(optionalProductJpa3).isEmpty();
        }

        @Test
        @Rollback
        void mustVerifyIfProductExistsById() {
            ProductJpa productJpa1 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            assertThat(productJpaRepository.existsById(productJpa1.getId())).isTrue();

            ProductJpa productJpa2 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 5.5));
            assertThat(productJpaRepository.existsById(productJpa2.getId())).isTrue();

            assertThat(productJpaRepository.existsById(0L)).isFalse();
        }
    }
}

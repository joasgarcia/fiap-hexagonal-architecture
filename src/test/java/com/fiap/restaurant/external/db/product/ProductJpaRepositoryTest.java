package com.fiap.restaurant.external.db.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private ProductJpa productJpa1;
    private ProductJpa productJpa2;
    private ProductJpa productJpa3;

    @BeforeEach
    void setup() {
        productJpa1 = new ProductJpa();
        productJpa1.setName("Drink 1");
        productJpa1.setDescription("Description 1");
        productJpa1.setCategory("DRINK");
        productJpa1.setPrice(7.5);
        productJpaRepository.save(productJpa1);

        productJpa2 = new ProductJpa();
        productJpa2.setName("Drink 2");
        productJpa2.setDescription("Description 2");
        productJpa2.setCategory("DRINK");
        productJpa2.setPrice(7.5);
        productJpaRepository.save(productJpa2);

        productJpa3 = new ProductJpa();
        productJpa3.setName("Snack 1");
        productJpa3.setDescription("Description 3");
        productJpa3.setCategory("SNACK");
        productJpa3.setPrice(7.5);
        productJpaRepository.save(productJpa3);
    }

    @Nested
    class FindProduct {

        @Test
        void mustFindAllProductsByCategory() {
            List<ProductJpa> productJpaList = productJpaRepository.findAllByCategory("DRINK");
            assertThat(productJpaList).isNotEmpty().hasSize(2);

            productJpaList = productJpaRepository.findAllByCategory("SNACK");
            assertThat(productJpaList).isNotEmpty().hasSize(1);
            assertThat(productJpaList.get(0).getId()).isEqualTo(productJpa3.getId());
            assertThat(productJpaList.get(0).getName()).isEqualTo(productJpa3.getName());
            assertThat(productJpaList.get(0).getDescription()).isEqualTo(productJpa3.getDescription());
            assertThat(productJpaList.get(0).getPrice()).isEqualTo(productJpa3.getPrice());

            productJpaList = productJpaRepository.findAllByCategory("DESSERT");
            assertThat(productJpaList).isEmpty();
        }
    }
}

package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.util.ItemProductTestUtil;
import com.fiap.restaurant.util.ItemTestUtil;
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
public class ItemProductRepositoryIT {

    @Autowired
    private ItemProductRepository itemProductRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveItemProduct() {
            ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 17.5));
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));

            ItemProductJpa itemProductJpa = ItemProductTestUtil.generateJpa(itemJpa, productJpa);
            ItemProductJpa persistedItemProductJpa = itemProductRepository.save(itemProductJpa);

            assertThat(persistedItemProductJpa).isNotNull();
            assertThat(persistedItemProductJpa.getItem().getId()).isEqualTo(itemProductJpa.getItem().getId());
            assertThat(persistedItemProductJpa.getProduct().getId()).isEqualTo(itemProductJpa.getProduct().getId());
        }

        @Test
        @Rollback
        void mustDeleteItemProduct() {
            ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 17.5));
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 6.0));
            ItemProductJpa itemProductJpa = itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa));

            itemProductRepository.delete(itemProductJpa);

            Optional<ItemProductJpa> optionalItemProductJpa = itemProductRepository.findById(itemProductJpa.getId());
            assertThat(optionalItemProductJpa).isEmpty();
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
        void mustFindProductById() {
            ProductJpa productJpa1 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            Optional<ProductJpa> optionalProductJpa1 = productJpaRepository.findById(productJpa1.getId());

            ProductJpa productJpa2 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 5.5));
            Optional<ProductJpa> optionalProductJpa2 = productJpaRepository.findById(productJpa2.getId());

            assertThat(optionalProductJpa1).isPresent();
            assertThat(optionalProductJpa2).isPresent();
        }

        @Test
        @Rollback
        void mustNotFindProductById() {
            Optional<ProductJpa> optionalProductJpa3 = productJpaRepository.findById(0L);
            assertThat(optionalProductJpa3).isEmpty();
        }

        @Test
        @Rollback
        void mustVerifyIfProductExistsById() {
            ProductJpa productJpa1 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ProductJpa productJpa2 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 5.5));

            assertThat(productJpaRepository.existsById(productJpa1.getId())).isTrue();
            assertThat(productJpaRepository.existsById(productJpa2.getId())).isTrue();
            assertThat(productJpaRepository.existsById(0L)).isFalse();
        }

        @Test
        @Rollback
        void mustFindAllItemProductByItemId() {
            ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 17.5));
            ProductJpa productJpa1 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa1));
            ProductJpa productJpa2 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 5.5));
            itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa2));
            ProductJpa productJpa3 = productJpaRepository.save(ProductTestUtil.generateJpa("Snack 1", "Description 3", "SNACK", 5.5));
            itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa3));

            List<ItemProductJpa> itemProductJpaList = itemProductRepository.findAllByItemId(itemJpa.getId());
            assertThat(itemProductJpaList).isNotEmpty().hasSize(3);
        }

        @Test
        @Rollback
        void mustNotFindAnyItemProductByItemId() {
            List<ItemProductJpa> itemProductJpaList = itemProductRepository.findAllByItemId(0L);
            assertThat(itemProductJpaList).isEmpty();
        }
    }
}

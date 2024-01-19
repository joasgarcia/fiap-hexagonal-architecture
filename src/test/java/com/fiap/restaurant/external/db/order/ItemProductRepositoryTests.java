package com.fiap.restaurant.external.db.order;

import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ItemProductRepositoryTests {

    @Autowired
    private ItemProductRepository itemProductRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private ItemJpa itemJpa;
    private ProductJpa productJpa1;
    private ItemProductJpa itemProductJpa1;
    private ProductJpa productJpa2;
    private ItemProductJpa itemProductJpa2;
    private ProductJpa productJpa3;
    private ItemProductJpa itemProductJpa3;

    @BeforeEach
    void setup() {
        itemJpa = new ItemJpa();
        itemJpa.setName("Item 1");
        itemJpa.setDescription("Description 1");
        itemJpa.setPrice(17.5);
        itemJpaRepository.save(itemJpa);

        productJpa1 = new ProductJpa();
        productJpa1.setName("Drink 1");
        productJpa1.setDescription("Description 1");
        productJpa1.setCategory("DRINK");
        productJpa1.setPrice(7.5);
        productJpaRepository.save(productJpa1);

        itemProductJpa1 = new ItemProductJpa();
        itemProductJpa1.setItem(itemJpa);
        itemProductJpa1.setProduct(productJpa1);
        itemProductRepository.save(itemProductJpa1);

        productJpa2 = new ProductJpa();
        productJpa2.setName("Drink 2");
        productJpa2.setDescription("Description 2");
        productJpa2.setCategory("DRINK");
        productJpa2.setPrice(5.5);
        productJpaRepository.save(productJpa2);

        itemProductJpa2 = new ItemProductJpa();
        itemProductJpa2.setItem(itemJpa);
        itemProductJpa2.setProduct(productJpa2);
        itemProductRepository.save(itemProductJpa2);

        productJpa3 = new ProductJpa();
        productJpa3.setName("Snack 1");
        productJpa3.setDescription("Description 3");
        productJpa3.setCategory("SNACK");
        productJpa3.setPrice(6.0);
        productJpaRepository.save(productJpa3);

        itemProductJpa3 = new ItemProductJpa();
        itemProductJpa3.setItem(itemJpa);
        itemProductJpa3.setProduct(productJpa3);
        itemProductRepository.save(itemProductJpa3);
    }

    @Test
    void mustFinaAllItemProductByItemId() {
        List<ItemProductJpa> itemProductJpaList = itemProductRepository.findAllByItemId(itemJpa.getId());
        assertThat(itemProductJpaList).isNotEmpty().hasSize(3);
    }

    @Test
    void mustNotFinaAllItemProductByItemId() {
        List<ItemProductJpa> itemProductJpaList = itemProductRepository.findAllByItemId(0L);
        assertThat(itemProductJpaList).isEmpty();
    }

}

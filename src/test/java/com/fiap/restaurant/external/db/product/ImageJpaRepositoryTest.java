package com.fiap.restaurant.external.db.product;

import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.order.ItemJpaRepository;
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
public class ImageJpaRepositoryTest {

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Nested
    class FindByProduct {
        private ProductJpa productJpa;
        private ImageJpa imageJpa1;
        private ImageJpa imageJpa2;

        @BeforeEach
        void setup() {
            productJpa = new ProductJpa();
            productJpa.setName("Drink 1");
            productJpa.setDescription("Description 1");
            productJpa.setCategory("DRINK");
            productJpa.setPrice(7.5);
            productJpaRepository.save(productJpa);

            imageJpa1 = new ImageJpa();
            imageJpa1.setProduct(productJpa);
            imageJpa1.setSrc("http://image1.test");
            imageJpaRepository.save(imageJpa1);

            imageJpa2 = new ImageJpa();
            imageJpa2.setProduct(productJpa);
            imageJpa2.setSrc("http://image1.test");
            imageJpaRepository.save(imageJpa2);
        }

        @Test
        void mustFindAllImagesByProductId() {
            List<ImageJpa> imageJpaList = imageJpaRepository.findAllByProductId(productJpa.getId());
            assertThat(imageJpaList).isNotEmpty().hasSize(2);
        }

        @Test
        void mustNotFindAllImagesByProductId() {
            List<ImageJpa> imageJpaList = imageJpaRepository.findAllByProductId(0L);
            assertThat(imageJpaList).isEmpty();
        }
    }

    @Nested
    class FindByItem {

        private ItemJpa itemJpa;
        private ImageJpa imageJpa3;

        @BeforeEach
        void setup() {
            itemJpa = new ItemJpa();
            itemJpa.setName("Item 1");
            itemJpa.setDescription("Description 1");
            itemJpa.setPrice(19.9);
            itemJpaRepository.save(itemJpa);

            imageJpa3 = new ImageJpa();
            imageJpa3.setItem(itemJpa);
            imageJpa3.setSrc("http://image3.test");
            imageJpaRepository.save(imageJpa3);
        }

        @Test
        void mustFindAllImagesByItemId() {
            List<ImageJpa> imageJpaList = imageJpaRepository.findAllByItemId(itemJpa.getId());
            assertThat(imageJpaList).isNotEmpty().hasSize(1);
            assertThat(imageJpaList.get(0).getId()).isEqualTo(imageJpa3.getId());
            assertThat(imageJpaList.get(0).getSrc()).isEqualTo(imageJpa3.getSrc());
            assertThat(imageJpaList.get(0).getItem().getId()).isEqualTo(imageJpa3.getItem().getId());
        }

        @Test
        void mustNotFindAllImagesByItemId() {
            List<ImageJpa> imageJpaList = imageJpaRepository.findAllByItemId(0L);
            assertThat(imageJpaList).isEmpty();
        }
    }
}

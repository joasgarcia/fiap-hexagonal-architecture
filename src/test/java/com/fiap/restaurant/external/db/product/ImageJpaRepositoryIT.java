package com.fiap.restaurant.external.db.product;

import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.order.ItemJpaRepository;
import com.fiap.restaurant.util.ImageTestUtil;
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
public class ImageJpaRepositoryIT {

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveProductImage() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ImageJpa imageProductJpa = ImageTestUtil.generateJpa(productJpa, "http://image1.test");
            ImageJpa persistedImageProductJpa = imageJpaRepository.save(imageProductJpa);

            assertThat(persistedImageProductJpa).isNotNull();
            assertThat(persistedImageProductJpa.getId()).isEqualTo(imageProductJpa.getId());
            assertThat(persistedImageProductJpa.getProduct().getId()).isEqualTo(imageProductJpa.getProduct().getId());
            assertThat(persistedImageProductJpa.getSrc()).isEqualTo(imageProductJpa.getSrc());
        }

        @Test
        @Rollback
        void mustSaveItemImage() {
            ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 19.9));
            ImageJpa imageItemJpa = ImageTestUtil.generateJpa(itemJpa, "http://image2.test");
            ImageJpa persistedItemImageJpa = imageJpaRepository.save(imageItemJpa);

            assertThat(persistedItemImageJpa).isNotNull();
            assertThat(persistedItemImageJpa.getId()).isEqualTo(imageItemJpa.getId());
            assertThat(persistedItemImageJpa.getItem().getId()).isEqualTo(imageItemJpa.getItem().getId());
            assertThat(persistedItemImageJpa.getSrc()).isEqualTo(imageItemJpa.getSrc());
        }

        @Test
        @Rollback
        void mustDeleteProductImage() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ImageJpa imageProductJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image1.test"));

            imageJpaRepository.delete(imageProductJpa);

            Optional<ImageJpa> optionalProductImageJpa = imageJpaRepository.findById(imageProductJpa.getId());
            assertThat(optionalProductImageJpa).isEmpty();
        }

        @Test
        @Rollback
        void mustDeleteItemImage() {
            ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 19.9));
            ImageJpa imageItemJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image2.test"));

            imageJpaRepository.delete(imageItemJpa);

            Optional<ImageJpa> optionalItemImageJpa = imageJpaRepository.findById(imageItemJpa.getId());
            assertThat(optionalItemImageJpa).isEmpty();
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustFindAllImagesByProductId() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image1.test"));
            imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image2.test"));

            List<ImageJpa> imageJpaList = imageJpaRepository.findAllByProductId(productJpa.getId());
            assertThat(imageJpaList).isNotEmpty().hasSize(2);
        }

        @Test
        @Rollback
        void mustNotFindAnyImagesByProductId() {
            List<ImageJpa> imageJpaList = imageJpaRepository.findAllByProductId(0L);
            assertThat(imageJpaList).isEmpty();
        }

        @Test
        @Rollback
        void mustFindAllImagesByItemId() {
            ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 19.9));
            ImageJpa imageJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image3.test"));

            List<ImageJpa> imageJpaList = imageJpaRepository.findAllByItemId(itemJpa.getId());
            assertThat(imageJpaList).isNotEmpty().hasSize(1);
            assertThat(imageJpaList.get(0).getId()).isEqualTo(imageJpa.getId());
            assertThat(imageJpaList.get(0).getSrc()).isEqualTo(imageJpa.getSrc());
            assertThat(imageJpaList.get(0).getItem().getId()).isEqualTo(imageJpa.getItem().getId());
        }

        @Test
        @Rollback
        void mustNotFindAnyImagesByItemId() {
            List<ImageJpa> imageJpaList = imageJpaRepository.findAllByItemId(0L);
            assertThat(imageJpaList).isEmpty();
        }

        @Test
        @Rollback
        void mustFindImageById() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ImageJpa imageProductJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image1.test"));
            Optional<ImageJpa> optionalProductImageJpa = imageJpaRepository.findById(imageProductJpa.getId());

            ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 19.9));
            ImageJpa imageItemJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image2.test"));
            Optional<ImageJpa> optionalItemImageJpa = imageJpaRepository.findById(imageItemJpa.getId());

            assertThat(optionalProductImageJpa).isPresent();
            assertThat(optionalItemImageJpa).isPresent();
        }

        @Test
        @Rollback
        void mustNotFindImageById() {
            Optional<ImageJpa> optionalProductJpa = imageJpaRepository.findById(0L);
            assertThat(optionalProductJpa).isEmpty();
        }

        @Test
        @Rollback
        void mustVerifyIfImageExistsById() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ImageJpa imageProductJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image1.test"));

            ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 19.9));
            ImageJpa imageItemJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image2.test"));

            assertThat(imageJpaRepository.existsById(imageProductJpa.getId())).isTrue();
            assertThat(imageJpaRepository.existsById(imageItemJpa.getId())).isTrue();
            assertThat(imageJpaRepository.existsById(0L)).isFalse();
        }
    }
}

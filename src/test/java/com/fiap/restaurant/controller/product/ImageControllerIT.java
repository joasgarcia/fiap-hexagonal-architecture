package com.fiap.restaurant.controller.product;

import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.external.db.product.ImageJpa;
import com.fiap.restaurant.external.db.product.ImageJpaRepository;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.types.dto.product.ImagePresenterDTO;
import com.fiap.restaurant.types.dto.product.SaveProductImageDTO;
import com.fiap.restaurant.types.dto.product.UpdateImageDTO;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.util.ImageTestUtil;
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
public class ImageControllerIT {

    @Autowired
    private ImageDatabaseConnection imageDatabaseConnection;

    @Autowired
    private ProductDatabaseConnection productDatabaseConnection;

    @Autowired
    private ItemDatabaseConnection itemDatabaseConnection;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveProductImage() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            final String imageSrc = "http://image.test";

            SaveProductImageDTO saveProductImageDTO = ImageTestUtil.generateSaveProductImageDTO(productJpa.getId(), imageSrc);

            Image image = ImageController.saveProductImage(saveProductImageDTO, imageDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);
            assertThat(image).isNotNull();
            assertThat(image.getSrc()).isEqualTo(imageSrc);
            assertThat(image.getProduct().getId()).isEqualTo(productJpa.getId());
            assertThat(image.getItem()).isNull();
        }

        @Test
        @Rollback
        void mustThrowExceptionProductNotFoundOnSaveProductImage() {
            final Long nonexistentProductId = 0L;
            final String imageSrc = "http://image.test";

            SaveProductImageDTO saveProductImageDTO = ImageTestUtil.generateSaveProductImageDTO(nonexistentProductId, imageSrc);

            assertThatThrownBy(() -> ImageController.saveProductImage(saveProductImageDTO, imageDatabaseConnection, productDatabaseConnection, itemDatabaseConnection))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Produto não encontrado.");
        }

        @Test
        @Rollback
        void mustUpdateImage() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ImageJpa imageProductJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image1.test"));

            final String imageSrc = "http://image.test";
            UpdateImageDTO updateImageDTO = ImageTestUtil.generateUpdateImageDTO(imageSrc);
            Image image = ImageController.update(imageProductJpa.getId(), updateImageDTO, imageDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);
            assertThat(image).isNotNull();
            assertThat(image.getSrc()).isEqualTo(imageSrc);
            assertThat(image.getProduct().getId()).isEqualTo(productJpa.getId());
        }

        @Test
        @Rollback
        void mustThrowExceptionIdNotFoundOnUpdateImage() {
            final Long nonexistentProductId = 0L;
            final String imageSrc = "http://image.test";

            UpdateImageDTO updateImageDTO = ImageTestUtil.generateUpdateImageDTO(imageSrc);

            assertThatThrownBy(() -> ImageController.update(nonexistentProductId, updateImageDTO, imageDatabaseConnection, productDatabaseConnection, itemDatabaseConnection))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Imagem não encontrada");
        }

        @Test
        @Rollback
        void mustDeleteImage() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            ImageJpa imageProductJpa = imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image1.test"));

            ImageController.delete(imageProductJpa.getId(), imageDatabaseConnection);

            Optional<ImageJpa> optionalImageJpa = imageJpaRepository.findById(imageProductJpa.getId());
            assertThat(optionalImageJpa).isNotPresent();
        }

        @Test
        @Rollback
        void mustThrowExceptionIdNotFoundOnDeleteImage() {
            final Long nonexistentProductId = 0L;

            assertThatThrownBy(() -> ImageController.delete(nonexistentProductId, imageDatabaseConnection))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Imagem não encontrada");
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustFindAllImageByProductId() {
            ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
            imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image1.test"));
            imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image2.test"));
            imageJpaRepository.save(ImageTestUtil.generateJpa(productJpa, "http://image3.test"));

            List<ImagePresenterDTO> imageList = ImageController.findAllByProductId(productJpa.getId(), imageDatabaseConnection);
            assertThat(imageList).isNotEmpty().hasSize(3);
        }

        @Test
        @Rollback
        void mustNotFindAnyImageByProductId() {
            final Long nonexistentProductId = 0L;
            List<ImagePresenterDTO> imageList = ImageController.findAllByProductId(nonexistentProductId, imageDatabaseConnection);
            assertThat(imageList).isEmpty();
        }
    }
}

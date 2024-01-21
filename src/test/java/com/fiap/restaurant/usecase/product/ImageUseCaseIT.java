package com.fiap.restaurant.usecase.product;

import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.external.db.product.ImageJpa;
import com.fiap.restaurant.external.db.product.ImageJpaRepository;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.gateway.product.IImageGateway;
import com.fiap.restaurant.gateway.product.IProductGateway;
import com.fiap.restaurant.gateway.product.ImageGateway;
import com.fiap.restaurant.gateway.product.ProductGateway;
import com.fiap.restaurant.types.dto.product.SaveProductImageDTO;
import com.fiap.restaurant.types.dto.product.UpdateImageDTO;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.product.ImageDatabaseConnection;
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
public class ImageUseCaseIT {

    @Autowired
    private ImageDatabaseConnection imageDatabaseConnection;

    @Autowired
    private ProductDatabaseConnection productDatabaseConnection;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    private IImageGateway imageGateway;
    private IProductGateway productGateway;

    @BeforeEach
    void setup() {
        this.productGateway = new ProductGateway(productDatabaseConnection);
        this.imageGateway = new ImageGateway(this.imageDatabaseConnection, this.productDatabaseConnection);
    }

    @Nested
    class Write {

        @Test
        @Rollback
        void mustSaveProductImage() {
            ProductJpa productJpa = ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 7.5);
            productJpa = productJpaRepository.save(productJpa);

            SaveProductImageDTO saveProductImageDTO = new SaveProductImageDTO();
            saveProductImageDTO.setProductId(productJpa.getId());
            saveProductImageDTO.setSrc("http://image.test");

            Image image = ImageUseCase.saveProductImage(saveProductImageDTO, imageGateway, productGateway);
            assertThat(image).isNotNull();
            assertThat(image.getProduct().getId()).isEqualTo(saveProductImageDTO.getProductId());
            assertThat(image.getSrc()).isEqualTo(saveProductImageDTO.getSrc());
        }

        @Test
        @Rollback
        void mustThrowExceptionProductNotFoundOnSaveProductImage() {
            SaveProductImageDTO saveProductImageDTO = new SaveProductImageDTO();
            saveProductImageDTO.setProductId(1L);
            saveProductImageDTO.setSrc("http://image.test");

            assertThatThrownBy(() -> ImageUseCase.saveProductImage(saveProductImageDTO, imageGateway, productGateway))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Produto não encontrado.");
        }

        @Test
        @Rollback
        void mustUpdateImage() {
            ProductJpa productJpa = ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 7.5);
            productJpa = productJpaRepository.save(productJpa);

            SaveProductImageDTO saveProductImageDTO = new SaveProductImageDTO();
            saveProductImageDTO.setProductId(productJpa.getId());
            saveProductImageDTO.setSrc("http://image.test");

            Image image = ImageUseCase.saveProductImage(saveProductImageDTO, imageGateway, productGateway);
            assertThat(image).isNotNull();

            UpdateImageDTO updateImageDTO = new UpdateImageDTO();
            updateImageDTO.setSrc("http://image2.test");

            Image updatedImage = ImageUseCase.update(image.getId(), updateImageDTO, imageGateway);
            assertThat(updatedImage).isNotNull();
            assertThat(updatedImage.getId()).isEqualTo(image.getId());
            assertThat(updatedImage.getSrc()).isEqualTo(updateImageDTO.getSrc());
        }

        @Test
        @Rollback
        void mustThrowExceptionProductNotFoundOnUpdateImage() {
            UpdateImageDTO updateImageDTO = new UpdateImageDTO();
            updateImageDTO.setSrc("http://image2.test");

            assertThatThrownBy(() -> ImageUseCase.update(1L, updateImageDTO, imageGateway))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Imagem não encontrada");
        }

        @Test
        @Rollback
        void mustDeleteImage() {
            ProductJpa productJpa = ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 7.5);
            productJpa = productJpaRepository.save(productJpa);

            SaveProductImageDTO saveProductImageDTO = new SaveProductImageDTO();
            saveProductImageDTO.setProductId(productJpa.getId());
            saveProductImageDTO.setSrc("http://image.test");

            Image image = ImageUseCase.saveProductImage(saveProductImageDTO, imageGateway, productGateway);
            assertThat(image).isNotNull();

            ImageUseCase.delete(image.getId(), imageGateway);

            Optional<ImageJpa> optionalImage = imageJpaRepository.findById(image.getId());
            assertThat(optionalImage).isNotPresent();
        }

        @Test
        @Rollback
        void mustThrowExceptionProductNotFoundOnDeleteImage() {
            assertThatThrownBy(() -> ImageUseCase.delete(1L, imageGateway))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("Imagem não encontrada");
        }
    }

    @Nested
    class Read {

        @Test
        @Rollback
        void mustFindAllImageByProductId() {
            ProductJpa productJpa = ProductTestUtil.generateJpa("Product 1", "Description 1", "DRINK", 7.5);
            productJpa = productJpaRepository.save(productJpa);

            SaveProductImageDTO saveProductImageDTO1 = new SaveProductImageDTO();
            saveProductImageDTO1.setProductId(productJpa.getId());
            saveProductImageDTO1.setSrc("http://image1.test");

            ImageUseCase.saveProductImage(saveProductImageDTO1, imageGateway, productGateway);

            SaveProductImageDTO saveProductImageDTO2 = new SaveProductImageDTO();
            saveProductImageDTO2.setProductId(productJpa.getId());
            saveProductImageDTO2.setSrc("http://image2.test");

            ImageUseCase.saveProductImage(saveProductImageDTO2, imageGateway, productGateway);

            List<Image> productImageList = ImageUseCase.findAllByProduct(productJpa.getId(), imageGateway);
            assertThat(productImageList).isNotEmpty().hasSize(2);
        }
    }
}

package com.fiap.restaurant.usecase.order;

import com.fiap.restaurant.entity.order.Item;
import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.order.ItemJpaRepository;
import com.fiap.restaurant.external.db.order.ItemProductJpa;
import com.fiap.restaurant.external.db.order.ItemProductRepository;
import com.fiap.restaurant.external.db.product.ImageJpa;
import com.fiap.restaurant.external.db.product.ImageJpaRepository;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.gateway.order.IItemGateway;
import com.fiap.restaurant.gateway.order.IItemProductGateway;
import com.fiap.restaurant.gateway.order.ItemGateway;
import com.fiap.restaurant.gateway.order.ItemProductGateway;
import com.fiap.restaurant.gateway.product.IImageGateway;
import com.fiap.restaurant.gateway.product.IProductGateway;
import com.fiap.restaurant.gateway.product.ImageGateway;
import com.fiap.restaurant.gateway.product.ProductGateway;
import com.fiap.restaurant.types.dto.IdDTO;
import com.fiap.restaurant.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.types.dto.product.ImageSrcDTO;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.ItemProductDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.util.ItemTestUtil;
import com.fiap.restaurant.util.ProductTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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
public class ItemUseCaseIT {

    @Autowired
    private ItemDatabaseConnection itemDatabaseConnection;

    @Autowired
    private ProductDatabaseConnection productDatabaseConnection;

    @Autowired
    private ItemProductDatabaseConnection itemProductDatabaseConnection;

    @Autowired
    private ImageDatabaseConnection imageDatabaseConnection;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ItemProductRepository itemProductRepository;

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    private IItemGateway itemGateway;
    private IProductGateway productGateway;
    private IItemProductGateway itemProductGateway;
    private IImageGateway imageGateway;

    @BeforeEach
    void setup() {
        this.itemGateway = new ItemGateway(itemDatabaseConnection);
        this.productGateway = new ProductGateway(productDatabaseConnection);
        this.itemProductGateway = new ItemProductGateway(itemProductDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);
        this.imageGateway = new ImageGateway(imageDatabaseConnection, productDatabaseConnection, itemDatabaseConnection);
    }

    @Test
    @Rollback
    void mustSaveItem() {
        ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));

        SaveItemDTO saveItemDTO = ItemTestUtil.generateSaveItemDTO("Item 1", "Item Description 1", 19.9);
        saveItemDTO.getProductIdList().add(new IdDTO(productJpa.getId()));
        saveItemDTO.getImageSrcList().add(new ImageSrcDTO("http://image1.test"));
        saveItemDTO.getImageSrcList().add(new ImageSrcDTO("http://image2.test"));

        Item item = ItemUseCase.save(saveItemDTO, itemGateway, productGateway, itemProductGateway, imageGateway);
        assertThat(item).isNotNull();
        assertThat(item.getName()).isEqualTo(saveItemDTO.getName());
        assertThat(item.getDescription()).isEqualTo(saveItemDTO.getDescription());
        assertThat(item.getPrice()).isEqualTo(saveItemDTO.getPrice());
        assertThat(item.getItemProductList()).isNotEmpty().hasSize(1);
        assertThat(item.getItemProductList().get(0).getProduct().getId()).isEqualTo(productJpa.getId());
        assertThat(item.getItemProductList().get(0).getProduct().getName()).isEqualTo(productJpa.getName());
        assertThat(item.getItemProductList().get(0).getProduct().getPrice()).isEqualTo(productJpa.getPrice());
        assertThat(item.getItemProductList().get(0).getProduct().getCategory()).isEqualTo(productJpa.getCategory());
        assertThat(item.getImageList()).isNotEmpty().hasSize(2);
    }

    @Test
    @Rollback
    void mustThrowExceptionProductListOnSaveItem() {
        SaveItemDTO saveItemDTO = ItemTestUtil.generateSaveItemDTO("Item 1", "Item Description 1", 19.9);

        assertThatThrownBy(() -> ItemUseCase.save(saveItemDTO, itemGateway, productGateway, itemProductGateway, imageGateway))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("O(s) produto(s) do item não informado(s)");
    }

    @Test
    @Rollback
    void mustDeleteItem() {
        ProductJpa productJpa = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));

        SaveItemDTO saveItemDTO = ItemTestUtil.generateSaveItemDTO("Item 1", "Item Description 1", 19.9);
        saveItemDTO.getProductIdList().add(new IdDTO(productJpa.getId()));
        saveItemDTO.getImageSrcList().add(new ImageSrcDTO("http://image1.test"));
        saveItemDTO.getImageSrcList().add(new ImageSrcDTO("http://image2.test"));

        Item item = ItemUseCase.save(saveItemDTO, itemGateway, productGateway, itemProductGateway, imageGateway);
        assertThat(item).isNotNull();

        List<ItemProductJpa> itemProductJpaList = itemProductRepository.findAllByItemId(item.getId());
        assertThat(itemProductJpaList).isNotEmpty().hasSize(1);

        List<ImageJpa> imageJpaList = imageJpaRepository.findAllByItemId(item.getId());
        assertThat(imageJpaList).isNotEmpty().hasSize(2);

        ItemUseCase.delete(item.getId(), itemGateway, itemProductGateway, imageGateway);

        Optional<ItemJpa> optionalItemJpa = itemJpaRepository.findById(item.getId());
        assertThat(optionalItemJpa).isNotPresent();

        itemProductJpaList = itemProductRepository.findAllByItemId(item.getId());
        assertThat(itemProductJpaList).isEmpty();

        imageJpaList = imageJpaRepository.findAllByItemId(item.getId());
        assertThat(imageJpaList).isEmpty();
    }

    @Test
    @Rollback
    void mustThrowExceptionIdNotFoundOnDeleteItem() {
        final Long nonexistentItemId = 1L;

        assertThatThrownBy(() -> ItemUseCase.delete(nonexistentItemId, itemGateway, itemProductGateway, imageGateway))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Item não encontrado");
    }
}

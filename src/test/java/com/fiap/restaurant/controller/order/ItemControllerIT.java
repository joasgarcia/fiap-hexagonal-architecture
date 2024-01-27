package com.fiap.restaurant.controller.order;

import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.order.ItemJpaRepository;
import com.fiap.restaurant.external.db.order.ItemProductRepository;
import com.fiap.restaurant.external.db.product.ImageJpaRepository;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.external.db.product.ProductJpaRepository;
import com.fiap.restaurant.types.dto.IdDTO;
import com.fiap.restaurant.types.dto.order.ItemPresenterDTO;
import com.fiap.restaurant.types.dto.order.SaveItemDTO;
import com.fiap.restaurant.types.dto.product.ImageSrcDTO;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.ItemProductDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.util.ImageTestUtil;
import com.fiap.restaurant.util.ItemProductTestUtil;
import com.fiap.restaurant.util.ItemTestUtil;
import com.fiap.restaurant.util.ProductTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ItemControllerIT {

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

    @Test
    @Rollback
    void mustSaveItemWithoutImageList() {
        ProductJpa productJpa1 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
        ProductJpa productJpa2 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 9.5));

        final String itemName = "Item 1";
        final String itemDescription = "Description 1";
        final Double itemPrice = 19.9;

        List<IdDTO> productIdList = new ArrayList<>();
        productIdList.add(new IdDTO(productJpa1.getId()));
        productIdList.add(new IdDTO(productJpa2.getId()));

        List<ImageSrcDTO> imageSrcList = new ArrayList<>();

        SaveItemDTO saveItemDTO = ItemTestUtil.generateSaveItemDTO(itemName, itemDescription, itemPrice, productIdList, imageSrcList);

        ItemPresenterDTO itemPresenterDTO = ItemController.save(saveItemDTO, itemDatabaseConnection, productDatabaseConnection, itemProductDatabaseConnection, imageDatabaseConnection);
        assertThat(itemPresenterDTO).isNotNull();
        assertThat(itemPresenterDTO.getName()).isEqualTo(itemName);
        assertThat(itemPresenterDTO.getDescription()).isEqualTo(itemDescription);
        assertThat(itemPresenterDTO.getPrice()).isEqualTo(itemPrice);
        assertThat(itemPresenterDTO.getProductList()).isNotEmpty().hasSize(2);
        assertThat(itemPresenterDTO.getImageList()).isEmpty();
    }

    @Test
    @Rollback
    void mustSaveItemWithImageList() {
        ProductJpa productJpa1 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
        ProductJpa productJpa2 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 9.5));

        final String itemName = "Item 1";
        final String itemDescription = "Description 1";
        final Double itemPrice = 19.9;

        List<IdDTO> productIdList = new ArrayList<>();
        productIdList.add(new IdDTO(productJpa1.getId()));
        productIdList.add(new IdDTO(productJpa2.getId()));

        List<ImageSrcDTO> imageSrcList = new ArrayList<>();
        imageSrcList.add(new ImageSrcDTO("http://image1.test"));
        imageSrcList.add(new ImageSrcDTO("http://image2.test"));

        SaveItemDTO saveItemDTO = ItemTestUtil.generateSaveItemDTO(itemName, itemDescription, itemPrice, productIdList, imageSrcList);

        ItemPresenterDTO itemPresenterDTO = ItemController.save(saveItemDTO, itemDatabaseConnection, productDatabaseConnection, itemProductDatabaseConnection, imageDatabaseConnection);
        assertThat(itemPresenterDTO).isNotNull();
        assertThat(itemPresenterDTO.getName()).isEqualTo(itemName);
        assertThat(itemPresenterDTO.getDescription()).isEqualTo(itemDescription);
        assertThat(itemPresenterDTO.getPrice()).isEqualTo(itemPrice);
        assertThat(itemPresenterDTO.getProductList()).isNotEmpty().hasSize(2);
        assertThat(itemPresenterDTO.getImageList()).isNotEmpty().hasSize(2);
    }

    @Test
    @Rollback
    void mustThrowExceptionEmptyProductListOnSaveItem() {
        final String itemName = "Item 1";
        final String itemDescription = "Description 1";
        final Double itemPrice = 19.9;
        List<IdDTO> productIdList = new ArrayList<>();
        List<ImageSrcDTO> imageSrcList = new ArrayList<>();

        SaveItemDTO saveItemDTO = ItemTestUtil.generateSaveItemDTO(itemName, itemDescription, itemPrice, productIdList, imageSrcList);

        assertThatThrownBy(() -> ItemController.save(saveItemDTO, itemDatabaseConnection, productDatabaseConnection, itemProductDatabaseConnection, imageDatabaseConnection))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("O(s) produto(s) do item não informado(s)");
    }

    @Test
    @Rollback
    void mustDeleteItem() {
        ProductJpa productJpa1 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 1", "Description 1", "DRINK", 7.5));
        ProductJpa productJpa2 = productJpaRepository.save(ProductTestUtil.generateJpa("Drink 2", "Description 2", "DRINK", 9.5));

        ItemJpa itemJpa = itemJpaRepository.save(ItemTestUtil.generateJpa("Item 1", "Description 1", 19.9));

        itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa1));
        itemProductRepository.save(ItemProductTestUtil.generateJpa(itemJpa, productJpa2));

        imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image1.test"));
        imageJpaRepository.save(ImageTestUtil.generateJpa(itemJpa, "http://image2.test"));

        ItemController.delete(itemJpa.getId(), itemDatabaseConnection, productDatabaseConnection, itemProductDatabaseConnection, imageDatabaseConnection);

        Optional<ItemJpa> optionalItemJpa = itemJpaRepository.findById(itemJpa.getId());
        assertThat(optionalItemJpa).isNotPresent();
    }

    @Test
    @Rollback
    void mustThrowExceptionItemNotFoundOnDeleteItem() {
        final Long nonexistentItemId = 0L;

        assertThatThrownBy(() -> ItemController.delete(nonexistentItemId, itemDatabaseConnection, productDatabaseConnection, itemProductDatabaseConnection, imageDatabaseConnection))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Item não encontrado");
    }
}

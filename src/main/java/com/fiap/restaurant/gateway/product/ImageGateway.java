package com.fiap.restaurant.gateway.product;

import com.fiap.restaurant.entity.product.Image;
import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.product.ImageJpa;
import com.fiap.restaurant.external.db.product.ProductJpa;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.types.mapper.product.ImageMapper;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class ImageGateway implements IImageGateway {

    private final ImageDatabaseConnection imageDatabaseConnection;
    private final ProductDatabaseConnection productDatabaseConnection;
    private final ItemDatabaseConnection itemDatabaseConnection;

    public ImageGateway(ImageDatabaseConnection imageDatabaseConnection) {
        this(imageDatabaseConnection, null, null);
    }

    public ImageGateway(ImageDatabaseConnection imageDatabaseConnection, ProductDatabaseConnection productDatabaseConnection, ItemDatabaseConnection itemDatabaseConnection) {
        this.imageDatabaseConnection = imageDatabaseConnection;
        this.productDatabaseConnection = productDatabaseConnection;
        this.itemDatabaseConnection = itemDatabaseConnection;
    }

    @Override
    public Image save(Image image) {
        ImageJpa imageJpa = new ImageJpa();
        imageJpa.setSrc(image.getSrc());

        if (image.getProduct() != null) {
            ProductJpa productJpa = (ProductJpa) this.productDatabaseConnection.getById(image.getProduct().getId()).get();
            imageJpa.setProduct(productJpa);
        }

        if (image.getItem() != null) {
            ItemJpa itemJpa = (ItemJpa) this.itemDatabaseConnection.getById(image.getItem().getId()).get();
            imageJpa.setItem(itemJpa);
        }

        imageJpa = (ImageJpa) this.imageDatabaseConnection.save(imageJpa);

        return ImageMapper.INSTANCE.toImage(imageJpa);
    }

    @Override
    public Image update(Image image) {
        Optional<ImageJpa> imageJpa = this.imageDatabaseConnection.getById(image.getId());
        if (imageJpa.isEmpty()) return null;

        ImageJpa updatedImageJpa = imageJpa.get();
        updatedImageJpa.setSrc(image.getSrc());

        updatedImageJpa = (ImageJpa) this.imageDatabaseConnection.save(updatedImageJpa);
        return ImageMapper.INSTANCE.toImage(updatedImageJpa);
    }

    @Override
    public Image getById(Long id) {
        Optional<ImageJpa> imageJpa = this.imageDatabaseConnection.getById(id);
        return imageJpa.map(ImageMapper.INSTANCE::toImage).orElse(null);

    }

    @Override
    public boolean existsById(Long id) {
        return this.imageDatabaseConnection.existsById(id);
    }

    @Override
    public void delete(Long id) {
        Optional<ImageJpa> imageJpa = this.imageDatabaseConnection.getById(id);
        imageJpa.ifPresent(this.imageDatabaseConnection::delete);
    }

    @Override
    public List<Image> findAllByProduct(Long productId) {
        List<ImageJpa> imageJpaList = this.imageDatabaseConnection.findAllByProductId(productId);
        return ImageMapper.INSTANCE.toImageList(imageJpaList);
    }

    @Override
    public List<Image> findAllByItemId(Long itemId) {
        List<ImageJpa> imageJpaList = this.imageDatabaseConnection.findAllByItemId(itemId);
        return ImageMapper.INSTANCE.toImageList(imageJpaList);
    }
}

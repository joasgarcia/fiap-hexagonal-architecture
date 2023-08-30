package com.fiap.restaurant.cleanarchitecture.gateway.product;

import com.fiap.restaurant.cleanarchitecture.entity.product.Image;
import com.fiap.restaurant.cleanarchitecture.external.db.product.ImageJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.product.ProductJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.mapper.product.ImageMapper;
import org.springframework.beans.BeanUtils;

import java.util.List;

@SuppressWarnings("unchecked")
public class ImageGateway implements IImageGateway {

    private final ImageDatabaseConnection imageDatabaseConnection;
    private final ProductDatabaseConnection productDatabaseConnection;

    public ImageGateway(ImageDatabaseConnection imageDatabaseConnection) {
        this(imageDatabaseConnection, null);
    }

    public ImageGateway(ImageDatabaseConnection imageDatabaseConnection, ProductDatabaseConnection productDatabaseConnection) {
        this.imageDatabaseConnection = imageDatabaseConnection;
        this.productDatabaseConnection = productDatabaseConnection;
    }

    @Override
    public Image save(Image image) {
        ImageJpa imageJpa = new ImageJpa();
        imageJpa.setSrc(image.getSrc());
        if (image.getProduct() != null) imageJpa.setProduct((ProductJpa) this.productDatabaseConnection.getById(image.getProduct().getId()));

        this.imageDatabaseConnection.save(imageJpa);

        return ImageMapper.INSTANCE.toImage(imageJpa);
    }

    @Override
    public Image update(Image image) {
        ImageJpa imageJpa = (ImageJpa) this.imageDatabaseConnection.getById(image.getId());
        if (imageJpa == null) return null;

        BeanUtils.copyProperties(image, imageJpa, "id");
        this.imageDatabaseConnection.save(imageJpa);
        return ImageMapper.INSTANCE.toImage(imageJpa);
    }

    @Override
    public Image getById(Long id) {
        ImageJpa imageJpa = (ImageJpa) this.imageDatabaseConnection.getById(id);
        if (imageJpa == null) return null;

        return ImageMapper.INSTANCE.toImage(imageJpa);
    }

    @Override
    public boolean existsById(Long id) {
        return this.imageDatabaseConnection.existsById(id);
    }

    @Override
    public void delete(Long id) {
        ImageJpa imageJpa = (ImageJpa) this.imageDatabaseConnection.getById(id);
        this.imageDatabaseConnection.delete(imageJpa);
    }

    @Override
    public List<Image> findAllByProduct(Long productId) {
        List<ImageJpa> imageJpaList = this.imageDatabaseConnection.findAllByProductId(productId);
        return ImageMapper.INSTANCE.toImageList(imageJpaList);
    }
}

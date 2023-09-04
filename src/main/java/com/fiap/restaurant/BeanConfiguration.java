package com.fiap.restaurant;

import com.fiap.restaurant.adapter.driven.data.MercadoPagoGatewayAdapter;
import com.fiap.restaurant.adapter.driven.data.repository.customer.CustomerRepository;
import com.fiap.restaurant.adapter.driven.data.repository.order.ItemProductRepository;
import com.fiap.restaurant.adapter.driven.data.repository.order.ItemRepository;
import com.fiap.restaurant.adapter.driven.data.repository.order.OrderItemRepository;
import com.fiap.restaurant.adapter.driven.data.repository.order.OrderRepository;
import com.fiap.restaurant.adapter.driven.data.repository.product.ImageRepository;
import com.fiap.restaurant.adapter.driven.data.repository.product.ProductRepository;
import com.fiap.restaurant.cleanarchitecture.external.db.customer.CustomerJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.order.ItemJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.order.ItemProductJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.product.ImageJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.order.OrderJpa;
import com.fiap.restaurant.cleanarchitecture.external.db.product.ProductJpa;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.ItemProductDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ImageDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.cleanarchitecture.types.interfaces.db.product.ProductDatabaseConnection;
import com.fiap.restaurant.core.service.customer.CustomerService;
import com.fiap.restaurant.core.service.order.ItemProductService;
import com.fiap.restaurant.core.service.order.ItemService;
import com.fiap.restaurant.core.service.order.OrderItemService;
import com.fiap.restaurant.core.service.order.OrderService;
import com.fiap.restaurant.core.service.product.ImageService;
import com.fiap.restaurant.core.service.product.ProductService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tech Challenge - FIAP")
                        .description("Documentação da API utilizada na construção da solução ao desafio técnico proposto na Postech FIAP (Software Architecture)")
                        .version("1.0"));
    }

    @Autowired
    public CustomerDatabaseConnection<CustomerJpa> customerDatabaseConnection;

    @Autowired
    public ProductDatabaseConnection<ProductJpa> productDatabaseConnection;

    @Autowired
    public ImageDatabaseConnection<ImageJpa> imageDatabaseConnection;

    @Autowired
    public OrderDatabaseConnection<OrderJpa> orderDatabaseConnection;
    @Autowired
    public ItemDatabaseConnection<ItemJpa> itemDatabaseConnection;

    @Autowired
    public ItemProductDatabaseConnection<ItemProductJpa> itemProductDatabaseConnection;

}

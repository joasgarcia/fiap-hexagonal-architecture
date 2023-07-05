package com.fiap.restaurant;

import com.fiap.restaurant.adapter.driven.data.MercadoPagoGatewayAdapter;
import com.fiap.restaurant.adapter.driven.data.repository.customer.CustomerRepository;
import com.fiap.restaurant.adapter.driven.data.repository.order.ItemProductRepository;
import com.fiap.restaurant.adapter.driven.data.repository.order.ItemRepository;
import com.fiap.restaurant.adapter.driven.data.repository.order.OrderRepository;
import com.fiap.restaurant.adapter.driven.data.repository.product.ImageRepository;
import com.fiap.restaurant.adapter.driven.data.repository.product.ProductRepository;
import com.fiap.restaurant.core.service.customer.CustomerService;
import com.fiap.restaurant.core.service.order.ItemProductService;
import com.fiap.restaurant.core.service.order.ItemService;
import com.fiap.restaurant.core.service.order.OrderService;
import com.fiap.restaurant.core.service.product.ImageService;
import com.fiap.restaurant.core.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemProductRepository itemProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MercadoPagoGatewayAdapter mercadoPagoGateway;

    @Bean
    public OrderService orderService() {
        return new OrderService(this.orderRepository, this.mercadoPagoGateway);
    }

    @Bean
    public ItemService itemService() {
        return new ItemService(this.itemRepository);
    }

    @Bean
    public ItemProductService itemProductService() {
        return new ItemProductService(this.itemProductRepository);
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(this.customerRepository);
    }

    @Bean
    public ProductService productService() {
        return new ProductService(this.productRepository);
    }

    @Bean
    public ImageService imageService() {
        return new ImageService(this.imageRepository);
    }
}

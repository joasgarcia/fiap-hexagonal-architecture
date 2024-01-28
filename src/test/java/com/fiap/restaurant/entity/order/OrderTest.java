package com.fiap.restaurant.entity.order;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.entity.product.Product;
import com.fiap.restaurant.util.CustomerTestUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    void mustInstantiateCorrectly() {
        final String customerName = "Customer name";
        final String customerEmail = "customer@teste.com";
        final String customerCpf = CustomerTestUtil.CPF;
        final Customer customer = new Customer(customerName, customerEmail, customerCpf);

        final Date dateCreated = new Date();
        final OrderStatus status = OrderStatus.RECEIVED;
        final OrderPaymentStatus paymentStatus = OrderPaymentStatus.PENDING;

        Order order = new Order(customer, dateCreated, status, paymentStatus, new ArrayList<>());
        assertThat(order).isNotNull();
        assertThat(order.getCustomer().getName()).isEqualTo(customerName);
        assertThat(order.getCustomer().getEmail()).isEqualTo(customerEmail);
        assertThat(order.getCustomer().getCpf()).isEqualTo(customerCpf);
        assertThat(order.getDateCreated()).isEqualTo(dateCreated);
        assertThat(order.getStatus()).isEqualTo(status);
        assertThat(order.getPaymentStatus()).isEqualTo(paymentStatus);
        assertThat(order.getItems()).isEmpty();
    }

    @Test
    void mustCalculateTotalValueByItemPrice() {
        final Customer customer = new Customer("Customer name", "customer@teste.com", CustomerTestUtil.CPF);
        final Double itemPrice = 17.5;

        Item item = new Item("Item 1", "Description", itemPrice);
        Order order = new Order(customer, new Date(), OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new ArrayList<>());
        OrderItem orderItem = new OrderItem(order, item, "Observation");

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);

        Product product = new Product("Product 1", "Description 1", 12.0, "SNACK");
        ItemProduct itemProduct = new ItemProduct(item, product);

        item.addItemProduct(itemProduct);
        order.setItems(orderItemList);

        assertThat(order.getTotalValue()).isEqualTo(itemPrice);
    }

    @Test
    void mustCalculateTotalValueByProductPrice() {
        final Customer customer = new Customer("Customer name", "customer@teste.com", CustomerTestUtil.CPF);

        Item item = new Item("Item 1", "Description", null);
        Order order = new Order(customer, new Date(), OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new ArrayList<>());
        OrderItem orderItem = new OrderItem(order, item, "Observation");

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);

        final Double productPrice = 12.5;

        Product product = new Product("Product 1", "Description 1", productPrice, "SNACK");
        ItemProduct itemProduct = new ItemProduct(item, product);

        item.addItemProduct(itemProduct);
        order.setItems(orderItemList);

        assertThat(order.getTotalValue()).isEqualTo(productPrice);
    }
}
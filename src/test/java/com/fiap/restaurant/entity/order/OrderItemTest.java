package com.fiap.restaurant.entity.order;

import com.fiap.restaurant.entity.customer.Customer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderItemTest {

    @Test
    void mustInstantiateCorrectly() {
        final String customerName = "Customer name";
        final String customerEmail = "customer@teste.com";
        final String customerCpf = "71841727016";
        final Customer customer = new Customer(customerName, customerEmail, customerCpf);

        final Date orderDateCreated = new Date();
        final OrderStatus orderStatus = OrderStatus.RECEIVED;
        final OrderPaymentStatus orderPaymentStatus = OrderPaymentStatus.PENDING;
        final Order order = new Order(customer, orderDateCreated, orderStatus, orderPaymentStatus, new ArrayList<>());

        final String itemName = "Item 1";
        final String itemDescription = "Description Item 1";
        final Double itemPrice = 10.5;
        final Item item = new Item(itemName, itemDescription, itemPrice);

        final String observation = "Order Observation";

        OrderItem orderItem = new OrderItem(order, item, observation);
        assertThat(orderItem).isNotNull();
        assertThat(orderItem.getOrder().getCustomer().getName()).isEqualTo(customerName);
        assertThat(orderItem.getOrder().getCustomer().getEmail()).isEqualTo(customerEmail);
        assertThat(orderItem.getOrder().getCustomer().getCpf()).isEqualTo(customerCpf);
        assertThat(orderItem.getOrder().getDateCreated()).isEqualTo(orderDateCreated);
        assertThat(orderItem.getOrder().getStatus()).isEqualTo(orderStatus);
        assertThat(orderItem.getOrder().getPaymentStatus()).isEqualTo(orderPaymentStatus);
        assertThat(orderItem.getItem().getName()).isEqualTo(itemName);
        assertThat(orderItem.getItem().getDescription()).isEqualTo(itemDescription);
        assertThat(orderItem.getItem().getPrice()).isEqualTo(itemPrice);
        assertThat(orderItem.getObservation()).isEqualTo(observation);
    }
}
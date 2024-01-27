package com.fiap.restaurant.entity.order;

import com.fiap.restaurant.entity.customer.Customer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    void mustInstantiateCorrectly() {
        final String customerName = "Customer name";
        final String customerEmail = "customer@teste.com";
        final String customerCpf = "71841727016";
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
}
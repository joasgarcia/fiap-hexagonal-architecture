package com.fiap.restaurant.util;

import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.order.OrderJpa;

import java.util.Date;

public class OrderTestUtil {

    public static OrderJpa generateJpa(CustomerJpa customer, OrderStatus status, OrderPaymentStatus paymentStatus, Date dateCreated) {
        OrderJpa orderJpa = new OrderJpa();
        orderJpa.setCustomer(customer);
        orderJpa.setStatus(status);
        orderJpa.setPaymentStatus(paymentStatus);
        orderJpa.setDateCreated(dateCreated);

        return orderJpa;
    }
}

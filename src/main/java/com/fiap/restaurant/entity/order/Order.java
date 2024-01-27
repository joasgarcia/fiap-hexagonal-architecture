package com.fiap.restaurant.entity.order;

import com.fiap.restaurant.entity.customer.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private Long id;
    private Customer customer;
    private Date dateCreated;

    private OrderStatus status;

    private List<OrderItem> items = new ArrayList<>();

    private OrderPaymentStatus paymentStatus;

    public Order() {
        this(null, null, null, null, new ArrayList<>());
    }

    public Order(Customer customer, Date dateCreated, OrderStatus status, OrderPaymentStatus paymentStatus, List<OrderItem> items) {
        this.customer = customer;
        this.dateCreated = dateCreated;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return this.items;
    }

    public OrderPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(OrderPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

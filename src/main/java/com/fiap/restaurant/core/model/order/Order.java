package com.fiap.restaurant.core.model.order;

import com.fiap.restaurant.core.enums.OrderStatus;
import com.fiap.restaurant.core.model.customer.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private Long id;
    private Customer customer;
    private Date dateCreated;

    private OrderStatus status;

    private List<OrderItem> items;

    public Order() {
        this.setItems(new ArrayList<>());
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

    public void transmitToKitchen() {
        this.setStatus(OrderStatus.TRANSMITED_TO_KITCHEN);
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return this.items;
    }

    public void addItem(OrderItem orderItem) {
        this.items.add(orderItem);
        orderItem.setOrder(this);
    }
}

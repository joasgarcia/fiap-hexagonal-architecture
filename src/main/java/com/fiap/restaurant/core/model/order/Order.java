package com.fiap.restaurant.core.model.order;

import com.fiap.restaurant.core.enums.OrderStatus;
import com.fiap.restaurant.core.model.customer.Customer;
import com.fiap.restaurant.core.model.product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private Long id;
    private Customer customer;
    private Date dateCreated;

    private String status;

    private List<Product> items;

    public Order() {
        this.items = new ArrayList<>();
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

    public List<Product> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void transmitToKitchen() {
        this.setStatus(OrderStatus.TRANSMITED_TO_KITCHEN.toString());
    }
}

package com.fiap.restaurant.cleanarchitecture.external.db.order;

import com.fiap.restaurant.cleanarchitecture.entity.order.OrderStatus;
import com.fiap.restaurant.cleanarchitecture.external.db.customer.CustomerJpa;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity(name = "`order_ca`")
public class OrderJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerJpa customer;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(targetEntity = OrderItemJpa.class, mappedBy = "order", fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private List<OrderItemJpa> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerJpa getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerJpa customer) {
        this.customer = customer;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<OrderItemJpa> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}


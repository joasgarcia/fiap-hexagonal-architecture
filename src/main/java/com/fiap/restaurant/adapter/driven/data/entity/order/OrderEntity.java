package com.fiap.restaurant.adapter.driven.data.entity.order;

import com.fiap.restaurant.adapter.driven.data.entity.customer.CustomerEntity;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity(name = "`order`")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "date_created")
    private Date dateCreated;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItemEntity> items;

    public Long getId() {
        return id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }
}

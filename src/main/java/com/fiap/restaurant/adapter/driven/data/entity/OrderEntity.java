package com.fiap.restaurant.adapter.driven.data.entity;

import jakarta.persistence.*;

import java.util.Date;

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
}

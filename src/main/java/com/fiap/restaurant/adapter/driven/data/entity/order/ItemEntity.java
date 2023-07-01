package com.fiap.restaurant.adapter.driven.data.entity.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue
    private Long id;

}

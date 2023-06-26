package com.fiap.restaurant.core.model;

import java.util.Date;

public class Order {

    private Long id;
    private Date dateCreated;

    public Long getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}

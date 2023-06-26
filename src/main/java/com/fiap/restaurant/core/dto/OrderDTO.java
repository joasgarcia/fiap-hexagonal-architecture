package com.fiap.restaurant.core.dto;

import java.util.Date;

public class OrderDTO {

    private Long id;
    private Date dateCreated;

    public OrderDTO(Long id, Date dateCreated) {
        this.id = id;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}

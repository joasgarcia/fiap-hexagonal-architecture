package com.fiap.restaurant.gateway.order;

public interface IOrderPaymentGateway {

    Boolean registerOrder(Long customerId, Double value);

}

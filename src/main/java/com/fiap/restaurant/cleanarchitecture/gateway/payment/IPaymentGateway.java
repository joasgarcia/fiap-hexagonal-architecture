package com.fiap.restaurant.cleanarchitecture.gateway.payment;

public interface IPaymentGateway {

    Boolean processPayment(Long id);
}

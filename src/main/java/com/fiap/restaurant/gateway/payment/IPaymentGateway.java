package com.fiap.restaurant.gateway.payment;

public interface IPaymentGateway {

    Boolean processPayment(Long id);
}

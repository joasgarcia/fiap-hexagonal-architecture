package com.fiap.restaurant.cleanarchitecture.gateway.payment;

public class MercadoPagoGateway implements IPaymentGateway {
    @Override
    public Boolean processPayment(Long id) {
        return true;
    }
}

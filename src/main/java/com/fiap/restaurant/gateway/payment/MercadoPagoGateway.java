package com.fiap.restaurant.gateway.payment;

public class MercadoPagoGateway implements IPaymentGateway {
    @Override
    public Boolean processPayment(Long id) {
        return true;
    }
}

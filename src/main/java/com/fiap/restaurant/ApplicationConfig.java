package com.fiap.restaurant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationConfig {

    @Value("${fiap.payment.url}")
    private String paymentUrl;

    @Value("${fiap.production.url}")
    private String productionUrl;

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public String getProductionUrl() {
        return productionUrl;
    }
}

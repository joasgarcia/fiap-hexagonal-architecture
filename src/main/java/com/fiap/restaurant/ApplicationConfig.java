package com.fiap.restaurant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationConfig {

    @Value("${fiap.payment.queue.url}")
    private String paymentQueueName;

    @Value("${fiap.production.url}")
    private String productionUrl;

    public String getPaymentQueueName() {
        return paymentQueueName;
    }

    public String getProductionUrl() {
        return productionUrl;
    }
}

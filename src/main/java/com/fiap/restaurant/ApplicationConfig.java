package com.fiap.restaurant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationConfig {

    @Value("${fiap.payment.queue.url}")
    private String paymentQueueUrl;

    @Value("${fiap.production.url}")
    private String productionUrl;

    public String getPaymentQueueUrl() {
        return paymentQueueUrl;
    }

    public String getProductionUrl() {
        return productionUrl;
    }
}

package com.fiap.restaurant.core.service.order;

import com.fiap.restaurant.core.drivenport.PaymentGatewayPort;
import com.fiap.restaurant.core.enums.OrderStatus;
import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.repository.order.IOrderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class OrderService {

    private final IOrderRepository orderRepository;
    private final PaymentGatewayPort paymentGatewayPort;

    public OrderService(IOrderRepository orderRepository, PaymentGatewayPort paymentGatewayPort) {
        this.orderRepository = orderRepository;
        this.paymentGatewayPort = paymentGatewayPort;

    }

    public List<Order> list() {
        return orderRepository.list();
    }

    public Boolean pay(Long orderId) {
        Order order = orderRepository.findById(orderId);

        Boolean paymentWasSuccessful = paymentGatewayPort.makePayment(orderId);

        if (paymentWasSuccessful) {
            order.transmitToKitchen();
        }

        orderRepository.save(order);

        return paymentWasSuccessful;
    }
}

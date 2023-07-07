package com.fiap.restaurant.core.service.order;

import com.fiap.restaurant.core.drivenport.PaymentGatewayPort;
import com.fiap.restaurant.core.enums.OrderStatus;
import com.fiap.restaurant.core.exception.ResourceNotFoundException;
import com.fiap.restaurant.core.model.customer.Customer;
import com.fiap.restaurant.core.model.order.Item;
import com.fiap.restaurant.core.model.order.Order;
import com.fiap.restaurant.core.model.order.OrderItem;
import com.fiap.restaurant.core.repository.order.IOrderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class OrderService {

    private final ItemService itemService;
    private final IOrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final PaymentGatewayPort paymentGatewayPort;

    public OrderService(IOrderRepository orderRepository, PaymentGatewayPort paymentGatewayPort, ItemService itemService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.paymentGatewayPort = paymentGatewayPort;
        this.itemService = itemService;
        this.orderItemService = orderItemService;
    }

    public List<Order> list() {
        return orderRepository.list();
    }

    public void addItem(Long orderId, Long itemId) {
        Order order = this.orderRepository.findById(orderId);
        if (order == null) throw new ResourceNotFoundException("Pedido [" + orderId + "] não encontrado");

        Item item = this.itemService.findById(itemId);
        if (item == null) throw new ResourceNotFoundException("Item [" + itemId + "] não encontrado");


        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem = this.orderItemService.save(orderItem);

        order.addItem(orderItem);
    }

    public void removeItem(Long orderId, Long itemId) {
        this.orderItemService.delete(orderId, itemId);
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

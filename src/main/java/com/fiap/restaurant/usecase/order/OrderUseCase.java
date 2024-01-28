package com.fiap.restaurant.usecase.order;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.entity.order.*;
import com.fiap.restaurant.gateway.customer.ICustomerGateway;
import com.fiap.restaurant.gateway.order.*;
import com.fiap.restaurant.types.dto.order.OrderItemDTO;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;
import com.fiap.restaurant.types.dto.order.payment.OrderPaymentResponseDTO;
import com.fiap.restaurant.types.exception.BusinessException;
import com.fiap.restaurant.types.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderUseCase {

    public static Order findById(Long id, IOrderGateway orderGateway) {
        Order order = orderGateway.getById(id);

        if (order == null) throw new ResourceNotFoundException("Pedido não encontrado");

        return order;
    }

    public static Order updatePaymentStatus(Long id, OrderPaymentStatus paymentStatus, IOrderGateway orderGateway) {
        Order order = orderGateway.getById(id);

        if (order == null) throw new ResourceNotFoundException("Pedido não encontrado");

        order.setPaymentStatus(paymentStatus);

        orderGateway.update(order);

        return order;
    }

    public static Order updateStatus(Long id, OrderStatus status, IOrderGateway orderGateway) {
        Order order = orderGateway.getById(id);

        if (order == null) throw new ResourceNotFoundException("Pedido não encontrado");

        order.setStatus(status);

        orderGateway.update(order);

        return order;
    }

    @Deprecated
    public static List<Order> listOrderedByStatus(IOrderGateway orderGateway) {
        return orderGateway.listOrderedByStatusAndId();
    }

    public static Order save(SaveOrderDTO saveOrderDTO, IOrderGateway orderGateway, ICustomerGateway customerGateway, IItemGateway itemGateway, IOrderItemGateway orderItemGateway, IOrderPaymentGateway orderPaymentGateway, IOrderProductionGateway orderProductionGateway) {
        Customer customer = customerGateway.findByCpf(saveOrderDTO.getCustomerCpf());

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.RECEIVED);
        order.setPaymentStatus(OrderPaymentStatus.PENDING);
        order.setDateCreated(new Date());
        Order newOrder = orderGateway.save(order);

        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : saveOrderDTO.getItems()) {
            Item item = itemGateway.getById(orderItemDTO.getItemId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(newOrder);
            orderItem.setItem(item);
            orderItem.setObservation(orderItemDTO.getObservation());
            orderItemGateway.save(orderItem);

            orderItemList.add(orderItem);
        }

        newOrder.setItems(orderItemList);

        OrderPaymentResponseDTO orderPaymentRestResponseDTO = orderPaymentGateway.registerOrder(customer.getId(), newOrder.getTotalValue());
        if (!orderPaymentRestResponseDTO.getSuccess()) throw new BusinessException("Não foi possível registrar o pedido no serviço de pagamentos");

        Boolean orderProductionQueued = orderProductionGateway.registerOrder(newOrder.getId());
        if (!orderProductionQueued) throw new BusinessException("Não foi possível registrar o pedido no serviço da fila de pedidos");

        newOrder.setPaymentStatus(OrderPaymentStatus.PROCESSING);
        orderGateway.update(newOrder);

        return newOrder;
    }
}

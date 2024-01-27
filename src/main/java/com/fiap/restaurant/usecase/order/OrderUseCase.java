package com.fiap.restaurant.usecase.order;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.entity.order.Item;
import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.entity.order.OrderItem;
import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.gateway.customer.ICustomerGateway;
import com.fiap.restaurant.gateway.order.IItemGateway;
import com.fiap.restaurant.gateway.order.IOrderGateway;
import com.fiap.restaurant.gateway.order.IOrderItemGateway;
import com.fiap.restaurant.gateway.order.IOrderPaymentGateway;
import com.fiap.restaurant.gateway.payment.IPaymentGateway;
import com.fiap.restaurant.types.dto.order.OrderItemDTO;
import com.fiap.restaurant.types.dto.order.OrderPaymentRestResponseDTO;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;
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

    public static Order save(SaveOrderDTO saveOrderDTO, IOrderGateway orderGateway, IPaymentGateway paymentGateway, ICustomerGateway customerGateway, IItemGateway itemGateway, IOrderItemGateway orderItemGateway, IOrderPaymentGateway orderPaymentGateway) {
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

        OrderPaymentRestResponseDTO orderPaymentRestResponseDTO = orderPaymentGateway.registerOrder(newOrder.getId());

        Boolean paymentCreatedSuccessfully = paymentGateway.processPayment(newOrder.getId());

        if (!paymentCreatedSuccessfully) throw new BusinessException("Erro ao processar pagamento");

        order.setPaymentStatus(OrderPaymentStatus.PROCESSING);
        orderGateway.update(newOrder);

        return newOrder;
    }
}

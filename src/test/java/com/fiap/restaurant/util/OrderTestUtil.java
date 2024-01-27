package com.fiap.restaurant.util;

import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.order.OrderJpa;
import com.fiap.restaurant.types.dto.order.OrderItemDTO;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;

import java.util.Arrays;
import java.util.Date;

public class OrderTestUtil {

    public static OrderJpa generateJpa(CustomerJpa customer, OrderStatus status, OrderPaymentStatus paymentStatus, Date dateCreated) {
        OrderJpa orderJpa = new OrderJpa();
        orderJpa.setCustomer(customer);
        orderJpa.setStatus(status);
        orderJpa.setPaymentStatus(paymentStatus);
        orderJpa.setDateCreated(dateCreated);

        return orderJpa;
    }

    public static SaveOrderDTO generateSaveDTO(String customerCpf, OrderItemDTO... orderItemDTOList) {
        SaveOrderDTO saveOrderDTO = new SaveOrderDTO();
        saveOrderDTO.setCustomerCpf(customerCpf);
        saveOrderDTO.setItems(Arrays.stream(orderItemDTOList).toList());

        return saveOrderDTO;
    }
}

package com.fiap.restaurant.presenter.order;

import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.types.dto.order.OrderScreenPresenterDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderPresenter {

    public static List<OrderScreenPresenterDTO> buildOrderScreen(List<Order> orderList) {
        List<OrderScreenPresenterDTO> orderScreenPresenterDTOlist = new ArrayList<OrderScreenPresenterDTO>();

        for (Order order : orderList) {
            OrderScreenPresenterDTO orderScreenPresenterDTO = new OrderScreenPresenterDTO();
            orderScreenPresenterDTO.setCustomerFirstName(order.getCustomer().getName().split(" ")[0]);
            orderScreenPresenterDTO.setStatus(order.getStatus().toString());
            orderScreenPresenterDTO.setWaitTimeInSeconds((new Date().getTime() - order.getDateCreated().getTime()) / 1000);

            orderScreenPresenterDTOlist.add(orderScreenPresenterDTO);
        }

        return orderScreenPresenterDTOlist;
    }
}

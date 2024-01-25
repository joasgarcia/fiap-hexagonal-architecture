package com.fiap.restaurant.controller.order;

import com.fiap.restaurant.entity.order.Order;
import com.fiap.restaurant.entity.order.OrderItem;
import com.fiap.restaurant.entity.order.OrderPaymentStatus;
import com.fiap.restaurant.entity.order.OrderStatus;
import com.fiap.restaurant.external.db.customer.CustomerJpa;
import com.fiap.restaurant.external.db.order.ItemJpa;
import com.fiap.restaurant.external.db.order.OrderItemJpa;
import com.fiap.restaurant.external.db.order.OrderJpa;
import com.fiap.restaurant.gateway.order.IOrderItemGateway;
import com.fiap.restaurant.gateway.payment.IPaymentGateway;
import com.fiap.restaurant.types.dto.order.OrderItemDTO;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;
import com.fiap.restaurant.types.interfaces.db.customer.CustomerDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.ItemDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderDatabaseConnection;
import com.fiap.restaurant.types.interfaces.db.order.OrderItemDatabaseConnection;
import com.fiap.restaurant.util.CustomerTestUtil;
import com.fiap.restaurant.util.ItemTestUtil;
import com.fiap.restaurant.util.OrderItemTestUtil;
import com.fiap.restaurant.util.OrderTestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderDatabaseConnection orderDatabaseConnection;

    @Mock
    private CustomerDatabaseConnection customerDatabaseConnection;

    @Mock
    private ItemDatabaseConnection itemDatabaseConnection;

    @Mock
    private OrderItemDatabaseConnection orderItemDatabaseConnection;

    @Mock
    private IPaymentGateway paymentGateway;

    @Mock
    private IOrderItemGateway orderItemGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void mustSaveOrder() {
        CustomerJpa customerJpa = CustomerTestUtil.generateJpa("John Doe", "johndoe@email.com", UUID.randomUUID().toString());
        ItemJpa itemJpa = ItemTestUtil.generateJpa("Item 1", "Item Description 1", 17.5);
        itemJpa.setId(1L);

        OrderJpa orderJpa = OrderTestUtil.generateJpa(customerJpa, OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new Date());
        orderJpa.setId(1L);

        OrderItemJpa orderItemJpa = OrderItemTestUtil.generateJpa(orderJpa, itemJpa, "Observation");
        orderItemJpa.setId(1L);

        when(customerDatabaseConnection.findByCpf(any(String.class)))
                .thenReturn(customerJpa);

        when(orderDatabaseConnection.getById(any(Long.class)))
                .thenReturn(orderJpa);

        when(orderDatabaseConnection.save(any(OrderJpa.class)))
                .thenReturn(orderJpa);

        when(itemDatabaseConnection.getById(any(Long.class)))
                .thenReturn(Optional.of(itemJpa));

        when(orderItemDatabaseConnection.save(any(OrderItemJpa.class)))
                .thenReturn(orderItemJpa);

        when(orderItemGateway.save(any(OrderItem.class)))
                .thenAnswer(i -> i.getArgument(0));

        when(paymentGateway.processPayment(any(Long.class)))
                .thenReturn(false);

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setItemId(1L);
        orderItemDTO.setObservation("Observation Item 1");

        orderItemDTOList.add(orderItemDTO);

        SaveOrderDTO saveOrderDTO = new SaveOrderDTO();
        saveOrderDTO.setCustomerCpf("71841727016");
        saveOrderDTO.setItems(orderItemDTOList);

        Order savedOrder = OrderController.save(saveOrderDTO, orderDatabaseConnection, customerDatabaseConnection, itemDatabaseConnection, orderItemDatabaseConnection);
        assertThat(savedOrder).isNotNull();
        verify(orderDatabaseConnection, times(2)).save(any(OrderJpa.class));
    }
}

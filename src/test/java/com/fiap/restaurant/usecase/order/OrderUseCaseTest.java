package com.fiap.restaurant.usecase.order;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.entity.order.*;
import com.fiap.restaurant.gateway.customer.ICustomerGateway;
import com.fiap.restaurant.gateway.order.IItemGateway;
import com.fiap.restaurant.gateway.order.IOrderGateway;
import com.fiap.restaurant.gateway.order.IOrderItemGateway;
import com.fiap.restaurant.gateway.payment.IPaymentGateway;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;
import com.fiap.restaurant.types.exception.BusinessException;
import com.fiap.restaurant.util.CustomerTestUtil;
import com.fiap.restaurant.util.OrderItemTestUtil;
import com.fiap.restaurant.util.OrderTestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderUseCaseTest {

    @Mock
    private ICustomerGateway customerGateway;

    @Mock
    private IPaymentGateway paymentGateway;

    @Mock
    private IOrderGateway orderGateway;

    @Mock
    private IItemGateway itemGateway;

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
        Customer customer = new Customer("John Doe", "johndoe@email.com", CustomerTestUtil.CPF);
        Item item = new Item("Item 1", "Item Description 1", 17.5);

        Order order = new Order(customer, new Date(), OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new ArrayList<>());
        order.setId(1L);

        when(customerGateway.findByCpf(any(String.class)))
                .thenReturn(customer);

        when(orderGateway.getById(any(Long.class)))
                .thenReturn(order);

        when(orderGateway.save(any(Order.class)))
                .thenReturn(order);

        when(itemGateway.getById(any(Long.class)))
                .thenReturn(item);

        when(orderItemGateway.save(any(OrderItem.class)))
                .thenAnswer(i -> i.getArgument(0));

        when(paymentGateway.processPayment(any(Long.class)))
                .thenReturn(true);

        SaveOrderDTO saveOrderDTO = OrderTestUtil.generateSaveDTO(CustomerTestUtil.CPF, OrderItemTestUtil.generateDTO(1L, "Observation Item 1"));

        Order savedOrder = OrderUseCase.save(saveOrderDTO, orderGateway, paymentGateway, customerGateway, itemGateway, orderItemGateway);
        assertThat(savedOrder).isNotNull();
        verify(orderGateway, times(1)).update(any(Order.class));
    }

    @Test
    void mustThrowExceptionPaymentNotProcessed() {
        Customer customer = new Customer("John Doe", "johndoe@email.com", UUID.randomUUID().toString());
        Item item = new Item("Item 1", "Item Description 1", 17.5);

        Order order = new Order(customer, new Date(), OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new ArrayList<>());
        order.setId(1L);

        when(customerGateway.findByCpf(any(String.class)))
                .thenReturn(customer);

        when(orderGateway.getById(any(Long.class)))
                .thenReturn(order);

        when(orderGateway.save(any(Order.class)))
                .thenReturn(order);

        when(itemGateway.getById(any(Long.class)))
                .thenReturn(item);

        when(orderItemGateway.save(any(OrderItem.class)))
                .thenAnswer(i -> i.getArgument(0));

        when(paymentGateway.processPayment(any(Long.class)))
                .thenReturn(false);

        SaveOrderDTO saveOrderDTO = OrderTestUtil.generateSaveDTO(CustomerTestUtil.CPF, OrderItemTestUtil.generateDTO(1L, "Observation Item 1"));

        assertThatThrownBy(() -> OrderUseCase.save(saveOrderDTO, orderGateway, paymentGateway, customerGateway, itemGateway, orderItemGateway))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Erro ao processar pagamento");

        verify(orderGateway, times(0)).update(any(Order.class));
    }
}

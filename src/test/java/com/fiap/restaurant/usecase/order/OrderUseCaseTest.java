package com.fiap.restaurant.usecase.order;

import com.fiap.restaurant.entity.customer.Customer;
import com.fiap.restaurant.entity.order.*;
import com.fiap.restaurant.gateway.customer.ICustomerGateway;
import com.fiap.restaurant.gateway.order.*;
import com.fiap.restaurant.gateway.payment.IPaymentGateway;
import com.fiap.restaurant.types.dto.order.SaveOrderDTO;
import com.fiap.restaurant.types.dto.order.payment.OrderPaymentResponseDTO;
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

    @Mock
    private IOrderPaymentGateway orderPaymentGateway;

    @Mock
    private IOrderProductionGateway orderProductionGateway;

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
        customer.setId(1L);

        Item item = new Item("Item 1", "Item Description 1", 17.5);
        item.setId(1L);

        Order order = new Order(customer, new Date(), OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new ArrayList<>());
        order.setId(1L);

        OrderPaymentResponseDTO orderPaymentResponseDTO = new OrderPaymentResponseDTO();
        orderPaymentResponseDTO.setSuccess(true);
        orderPaymentResponseDTO.setMessage("Cobrança registrada com sucesso");

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

        when(orderPaymentGateway.registerOrder(any(Long.class), any(Double.class)))
                .thenReturn(orderPaymentResponseDTO);

        when(orderProductionGateway.registerOrder(any(Long.class)))
                .thenReturn(true);

        SaveOrderDTO saveOrderDTO = OrderTestUtil.generateSaveDTO(CustomerTestUtil.CPF, OrderItemTestUtil.generateDTO(1L, "Observation Item 1"));

        Order savedOrder = OrderUseCase.save(saveOrderDTO, orderGateway, customerGateway, itemGateway, orderItemGateway, orderPaymentGateway, orderProductionGateway);
        assertThat(savedOrder).isNotNull();
        verify(orderGateway, times(1)).update(any(Order.class));
    }

    @Test
    void mustThrowExceptionPaymentNotProcessed() {
        Customer customer = new Customer("John Doe", "johndoe@email.com", CustomerTestUtil.randomCpf());
        customer.setId(1L);

        Item item = new Item("Item 1", "Item Description 1", 17.5);
        item.setId(1L);

        Order order = new Order(customer, new Date(), OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new ArrayList<>());
        order.setId(1L);

        OrderPaymentResponseDTO orderPaymentResponseDTO = new OrderPaymentResponseDTO();
        orderPaymentResponseDTO.setSuccess(false);
        orderPaymentResponseDTO.setMessage("Valor não pode ser nulo");

        when(customerGateway.findByCpf(any(String.class)))
                .thenReturn(customer);

        when(orderGateway.getById(any(Long.class)))
                .thenReturn(order);

        when(orderGateway.save(any(Order.class)))
                .thenReturn(order);

        when(itemGateway.getById(any(Long.class)))
                .thenReturn(item);

        when(orderPaymentGateway.registerOrder(any(Long.class), any(Double.class)))
                .thenReturn(orderPaymentResponseDTO);

        when(orderProductionGateway.registerOrder(any(Long.class)))
                .thenReturn(true);

        SaveOrderDTO saveOrderDTO = OrderTestUtil.generateSaveDTO(CustomerTestUtil.CPF, OrderItemTestUtil.generateDTO(1L, "Observation Item 1"));

        assertThatThrownBy(() -> OrderUseCase.save(saveOrderDTO, orderGateway,  customerGateway, itemGateway, orderItemGateway, orderPaymentGateway, orderProductionGateway))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Não foi possível registrar o pedido no serviço de pagamentos");

        verify(orderGateway, times(0)).update(any(Order.class));
    }

    @Test
    void mustThrowExceptionProductionNotProcessed() {
        Customer customer = new Customer("John Doe", "johndoe@email.com", CustomerTestUtil.randomCpf());
        customer.setId(1L);

        Item item = new Item("Item 1", "Item Description 1", 17.5);
        item.setId(1L);

        Order order = new Order(customer, new Date(), OrderStatus.RECEIVED, OrderPaymentStatus.PENDING, new ArrayList<>());
        order.setId(1L);

        OrderPaymentResponseDTO orderPaymentResponseDTO = new OrderPaymentResponseDTO();
        orderPaymentResponseDTO.setSuccess(true);
        orderPaymentResponseDTO.setMessage("Cobrança registrada com sucesso");

        when(customerGateway.findByCpf(any(String.class)))
                .thenReturn(customer);

        when(orderGateway.getById(any(Long.class)))
                .thenReturn(order);

        when(orderGateway.save(any(Order.class)))
                .thenReturn(order);

        when(itemGateway.getById(any(Long.class)))
                .thenReturn(item);

        when(orderPaymentGateway.registerOrder(any(Long.class), any(Double.class)))
                .thenReturn(orderPaymentResponseDTO);

        when(orderProductionGateway.registerOrder(any(Long.class)))
                .thenReturn(false);

        SaveOrderDTO saveOrderDTO = OrderTestUtil.generateSaveDTO(CustomerTestUtil.CPF, OrderItemTestUtil.generateDTO(1L, "Observation Item 1"));

        assertThatThrownBy(() -> OrderUseCase.save(saveOrderDTO, orderGateway,  customerGateway, itemGateway, orderItemGateway, orderPaymentGateway, orderProductionGateway))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Não foi possível registrar o pedido no serviço da fila de pedidos");

        verify(orderGateway, times(0)).update(any(Order.class));
    }
}

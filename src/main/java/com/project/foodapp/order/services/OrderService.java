package com.project.foodapp.order.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.foodapp.enums.OrderStatus;
import com.project.foodapp.order.dtos.OrderDTO;
import com.project.foodapp.order.dtos.OrderItemDTO;
import com.project.foodapp.response.Response;

public interface OrderService {

    Response<?> placeOrderFromCart();

    Response<OrderDTO> getOrderById(Long id);

    Response<Page<OrderDTO>> getAllOrders(OrderStatus orderStatus, int page, int size);

    Response<List<OrderDTO>> getOrdersOfUser();

    Response<OrderItemDTO> getOrderItemById(Long orderItemId);

    Response<OrderDTO> updateOrderStatus(OrderDTO orderDTO);

    Response<Long> countUniqueCustomers();
}

package com.project.foodapp.order.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.foodapp.auth_users.dtos.UserDTO;
import com.project.foodapp.enums.OrderStatus;
import com.project.foodapp.enums.PaymentStatus;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {

    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private UserDTO user; // Customer who placed the order
    private List<OrderItemDTO> orderItems;
}

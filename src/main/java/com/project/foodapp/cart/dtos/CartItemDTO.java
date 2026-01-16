package com.project.foodapp.cart.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.foodapp.menu.dtos.MenuDTO;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemDTO {

    private Long id;
    private MenuDTO menu;
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal subtotal;
}

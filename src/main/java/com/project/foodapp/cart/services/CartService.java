package com.project.foodapp.cart.services;

import com.project.foodapp.cart.dtos.CartDTO;
import com.project.foodapp.response.Response;

public interface CartService {

    Response<?> addItemToCart(CartDTO cartDTO);

    Response<?> incrementItem(Long menuId);

    Response<?> decrementItem(Long menuId);

    Response<?> removeItem(Long cartItemId);

    Response<CartDTO> getShoppingCart();

    Response<?> clearShoppingCart();
}

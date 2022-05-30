package com.demo.BaristaBucks.Service;

import com.demo.BaristaBucks.Dto.RequestDto.CartRequestDto;

public interface CartService {
    CartRequestDto addItemToCart(CartRequestDto requestDto);

    CartRequestDto addRemoveItemFromCart(CartRequestDto requestDto);
}

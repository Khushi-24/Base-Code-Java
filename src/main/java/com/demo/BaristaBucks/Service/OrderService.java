package com.demo.BaristaBucks.Service;

import com.demo.BaristaBucks.Dto.RequestDto.OrderRequestDto;

public interface OrderService {

    OrderRequestDto createOrder(OrderRequestDto requestDto);
}

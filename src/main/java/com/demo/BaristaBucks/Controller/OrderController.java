package com.demo.BaristaBucks.Controller;

import com.demo.BaristaBucks.Dto.RequestDto.CoffeeRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.OrderRequestDto;
import com.demo.BaristaBucks.Service.OrderService;
import com.demo.BaristaBucks.Util.ApiResponse;
import com.demo.BaristaBucks.Util.EndPoints;
import com.demo.BaristaBucks.Util.SuccessMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {

private final OrderService orderService;

    @PostMapping(EndPoints.Order.CREATE_ORDER)
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDto requestDto) {
        OrderRequestDto order = orderService.createOrder(requestDto);
        return ApiResponse.sendCreatedResponse(SuccessMessages.Order.ORDER_CREATED_SUCCESSFULLY, order);
    }
}

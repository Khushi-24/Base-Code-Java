package com.demo.BaristaBucks.Controller;

import com.demo.BaristaBucks.Dto.RequestDto.CartRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.CoffeeRequestDto;
import com.demo.BaristaBucks.Service.CartService;
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
public class CartController {

    private final CartService cartService;


    @PostMapping(EndPoints.Cart.ADD_TO_CART)
    public ResponseEntity<?> addItemToCart(@Valid @RequestBody CartRequestDto requestDto) {
        CartRequestDto cart = cartService.addItemToCart(requestDto);
        return ApiResponse.sendCreatedResponse(SuccessMessages.Cart.ITEM_ADDED_SUCCESSFULLY, cart);
    }

    @PostMapping(EndPoints.Cart.ADD_OR_REMOVE_FROM_CART)
    public ResponseEntity<?> addOrRemoveFromCart(@Valid @RequestBody CartRequestDto requestDto) {
        CartRequestDto cart = cartService.addRemoveItemFromCart(requestDto);
        return ApiResponse.sendCreatedResponse(SuccessMessages.Cart.ITEM_ADDED_SUCCESSFULLY, cart);
    }

}

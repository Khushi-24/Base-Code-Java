package com.demo.BaristaBucks.Dto.RequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CartRequestDto {

    private Long cartId;

    @NotNull(message = "{coffee.id_null_empty}")
    private Long coffeeId;

    @NotNull(message = "{user.id_null_empty}")
    private Long userId;

    @NotNull(message = "{cart.quantity_null_empty}")
    private Long quantity;

    @NotNull(message = "{order.id_null_empty }")
    private Long orderId;

    @NotNull(message = "{cart.total_cart_price_null}")
    private Double totalPrice;

}

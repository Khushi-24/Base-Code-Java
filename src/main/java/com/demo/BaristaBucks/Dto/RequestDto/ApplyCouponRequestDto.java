package com.demo.BaristaBucks.Dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ApplyCouponRequestDto {

    @NotNull(message = "{cart.total_cart_price_null}")
    private Double totalCartPrice;

    @NotNull(message = "{coupon.id_null_empty}")
    private Long couponId;
}

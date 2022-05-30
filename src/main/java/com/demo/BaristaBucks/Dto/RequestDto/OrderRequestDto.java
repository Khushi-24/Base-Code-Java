package com.demo.BaristaBucks.Dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderRequestDto {

    private Long id;

    @NotNull(message = "{user.id_null_empty}")
    private Long userId;

    @NotNull(message = "{order.total_price_null}")
    private Double totalPrice;

    @NotNull(message = "{order.discount_price_null}")
    private Double discountedPrice;

    @NotNull(message = "{coupon.id_null_empty}")
    private Long couponId;

    @NotNull(message = "{order.paid_price_null}")
    private Double paidPrice;

}

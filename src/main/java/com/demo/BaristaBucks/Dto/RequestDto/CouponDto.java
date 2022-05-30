package com.demo.BaristaBucks.Dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CouponDto {

    private Long id;

    @NotEmpty(message = "{coupon.name_null_empty}")
    @NotNull(message = "{coupon.name_null_empty}")
    private String couponName;

    @NotNull(message = "{coupon.discount_percentage_null}")
    private Long discountPercentage;

    @NotNull(message = "{coupon.minimum_order_amount_null}")
    private Long minimumOrderAmount;

    @NotNull(message = "{coupon.max_discount_amount_null}")
    private Long maxDiscountPrice;

}

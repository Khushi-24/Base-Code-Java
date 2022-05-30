package com.demo.BaristaBucks.Service;

import com.demo.BaristaBucks.Dto.RequestDto.ApplyCouponRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.CouponDto;

import java.util.List;

public interface CouponService {
    CouponDto addCoupon(CouponDto requestDto);

    Double applyCoupon(ApplyCouponRequestDto requestDto);

    List<CouponDto> getAllCouponsByUserId(Long userId);
}

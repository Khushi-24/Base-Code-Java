package com.demo.BaristaBucks.ServiceImpl;

import com.demo.BaristaBucks.Dto.RequestDto.ApplyCouponRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.CouponDto;
import com.demo.BaristaBucks.Entity.Coffee;
import com.demo.BaristaBucks.Entity.Coupons;
import com.demo.BaristaBucks.Entity.User;
import com.demo.BaristaBucks.Exception.AlreadyExistsException;
import com.demo.BaristaBucks.Exception.BadRequestException;
import com.demo.BaristaBucks.Exception.EntityNotFoundException;
import com.demo.BaristaBucks.Repository.CouponRepository;
import com.demo.BaristaBucks.Repository.UserRepository;
import com.demo.BaristaBucks.Service.CouponService;
import com.demo.BaristaBucks.Util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Override
    public CouponDto addCoupon(CouponDto requestDto) {
        if(!couponRepository.existsByCouponName(requestDto.getCouponName())){
            Coupons coupons = ObjectMapperUtil.map(requestDto, Coupons.class);
            couponRepository.save(coupons);
            return ObjectMapperUtil.map(coupons, CouponDto.class);
        }else{
            throw new AlreadyExistsException(Coupons.class,requestDto.getCouponName());
        }
    }

    @Override
    public Double applyCoupon(ApplyCouponRequestDto requestDto) {
        Coupons coupons = couponRepository.findById(requestDto.getCouponId()).orElseThrow(() -> new EntityNotFoundException(Coupons.class, requestDto.getCouponId()));
        if(coupons != null){
            if(requestDto.getTotalCartPrice() > coupons.getMinimumOrderAmount()){
                Double discountPrice = (requestDto.getTotalCartPrice() * coupons.getDiscountPercentage())/100d;
                if(discountPrice > coupons.getMaxDiscountPrice()){
                    discountPrice = Double.valueOf(coupons.getMaxDiscountPrice());
                }
                return discountPrice;
            }else {
               throw new BadRequestException("Minimum cart value should be: " +coupons.getMinimumOrderAmount());
            }
        }else {
            throw new EntityNotFoundException("Coupon doesn't exists with id" +requestDto.getCouponId());
        }
    }

    @Override
    public List<CouponDto> getAllCouponsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, userId));
        List<Coupons> coupons = couponRepository.findAll();
        List<Coupons> listOfCouponsUsedByUser = couponRepository.findByUserId(userId);
        coupons.removeAll(listOfCouponsUsedByUser);
        return ObjectMapperUtil.mapAll(coupons, CouponDto.class);
    }
}

package com.demo.BaristaBucks.ServiceImpl;

import com.demo.BaristaBucks.Dto.RequestDto.OrderRequestDto;
import com.demo.BaristaBucks.Entity.Cart;
import com.demo.BaristaBucks.Entity.Coupons;
import com.demo.BaristaBucks.Entity.Order;
import com.demo.BaristaBucks.Entity.User;
import com.demo.BaristaBucks.Exception.EntityNotFoundException;
import com.demo.BaristaBucks.Repository.CartRepository;
import com.demo.BaristaBucks.Repository.CouponRepository;
import com.demo.BaristaBucks.Repository.OrderRepository;
import com.demo.BaristaBucks.Repository.UserRepository;
import com.demo.BaristaBucks.Service.OrderService;
import com.demo.BaristaBucks.Util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final CouponRepository couponRepository;

    private final CartRepository cartRepository;

    @Override
    public OrderRequestDto createOrder(OrderRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new EntityNotFoundException(User.class, requestDto.getUserId()));
        Coupons coupons = couponRepository.findById(requestDto.getCouponId()).orElseThrow(() -> new EntityNotFoundException(Coupons.class, requestDto.getCouponId()));
        Order order = ObjectMapperUtil.map(requestDto, Order.class);
        order.setUser(user);
        order.setCoupon(coupons);
        orderRepository.save(order);
        List<Cart> cartList = cartRepository.findByUserId(user.getId());
        cartList.forEach(cart -> {
            cart.setOrderId(order.getId());
            cartRepository.save(cart);
        });
        return requestDto;
    }
}

package com.demo.BaristaBucks.ServiceImpl;

import com.demo.BaristaBucks.Dto.RequestDto.RatingRequestDto;
import com.demo.BaristaBucks.Entity.Cart;
import com.demo.BaristaBucks.Entity.Coffee;
import com.demo.BaristaBucks.Entity.Rating;
import com.demo.BaristaBucks.Entity.User;
import com.demo.BaristaBucks.Exception.EntityNotFoundException;
import com.demo.BaristaBucks.Repository.CoffeeRepository;
import com.demo.BaristaBucks.Repository.RatingRepository;
import com.demo.BaristaBucks.Repository.UserRepository;
import com.demo.BaristaBucks.Service.RatingService;
import com.demo.BaristaBucks.Util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final CoffeeRepository coffeeRepository;

    private final UserRepository userRepository;

    private final RatingRepository ratingRepository;

    @Override
    public RatingRequestDto addRating(RatingRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new EntityNotFoundException(User.class, requestDto.getUserId()));
        Coffee coffee = coffeeRepository.findById(requestDto.getCoffeeId()).orElseThrow(() -> new EntityNotFoundException(Coffee.class, requestDto.getCoffeeId()));
        Rating rating = ObjectMapperUtil.map(requestDto, Rating.class);
        rating.setCoffee(coffee);
        rating.setUser(user);
        ratingRepository.save(rating);
        requestDto.setId(rating.getId());
        return requestDto;
    }
}

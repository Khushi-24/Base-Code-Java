package com.demo.BaristaBucks.ServiceImpl;

import com.demo.BaristaBucks.Common.PaginationResponseDto;
import com.demo.BaristaBucks.Dto.RequestDto.CoffeeRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.FeatureDto;
import com.demo.BaristaBucks.Dto.RequestDto.PaginationRequestDto;
import com.demo.BaristaBucks.Dto.ResponseDto.CoffeeListResponseDto;
import com.demo.BaristaBucks.Entity.Coffee;
import com.demo.BaristaBucks.Exception.AlreadyExistsException;
import com.demo.BaristaBucks.Exception.EntityNotFoundException;
import com.demo.BaristaBucks.Repository.CartRepository;
import com.demo.BaristaBucks.Repository.CoffeeRepository;
import com.demo.BaristaBucks.Repository.RatingRepository;
import com.demo.BaristaBucks.Service.CoffeeService;
import com.demo.BaristaBucks.Util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoffeeServiceImpl implements CoffeeService {

    private final CoffeeRepository coffeeRepository;

    private final RatingRepository ratingRepository;

    private final CartRepository cartRepository;


    @Override
    public CoffeeRequestDto addUpdateCoffee(CoffeeRequestDto requestDto) {
        Coffee coffee = coffeeRepository.findByNameAndSize(requestDto.getName(), requestDto.getSize());
        if(coffee != null){
            if(requestDto.getId() != null){
                coffee = coffeeRepository.findById(requestDto.getId()).orElseThrow(() -> new EntityNotFoundException(Coffee.class, requestDto.getId()));
                if(coffee.getId().equals(requestDto.getId())){
                    Date createdDate = coffee.getCreatedTimeStamp();
                    Date updatedDate = coffee.getUpdatedTimeStamp();
                    coffee = ObjectMapperUtil.map(requestDto, Coffee.class);
                    coffee.setCreatedTimeStamp(createdDate);
                    coffee.setUpdatedTimeStamp(updatedDate);
                    coffeeRepository.save(coffee);
                    return requestDto;
                }else {
                    throw new AlreadyExistsException(Coffee.class, requestDto.getName());
                }
            } else {
                throw new AlreadyExistsException(Coffee.class, requestDto.getName());
            }
        }else {
            if(requestDto.getId() != null){
                assert false;
                Date createdDate = coffee.getCreatedTimeStamp();
                Date updatedDate = coffee.getUpdatedTimeStamp();
                coffee = coffeeRepository.findById(requestDto.getId()).orElseThrow(() -> new EntityNotFoundException(Coffee.class, requestDto.getId()));
                coffee.setCreatedTimeStamp(createdDate);
                coffee.setUpdatedTimeStamp(updatedDate);
                coffeeRepository.save(coffee);
            }else{
                coffee = ObjectMapperUtil.map(requestDto, Coffee.class);
                coffeeRepository.save(coffee);
                requestDto.setId(coffee.getId());
            }
            return requestDto;
        }

    }

    @Override
    public Boolean featureCoffee(FeatureDto requestDto) {
        Coffee coffee = coffeeRepository.findById(requestDto.getId()).orElseThrow(() -> new EntityNotFoundException(Coffee.class, requestDto.getId()));
        coffee.setIsFeatured(requestDto.getIsFeatured());
        coffeeRepository.save(coffee);
        return requestDto.getIsFeatured();
    }

    @Override
    public PaginationResponseDto<CoffeeListResponseDto> getListOfCoffee(PaginationRequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize());
        Page<Coffee> coffeeList = coffeeRepository.findAll(pageable);
        PaginationResponseDto<CoffeeListResponseDto> responseDto = null;
        List<CoffeeListResponseDto> coffeeListResponseDtos = ObjectMapperUtil.mapAll(coffeeList.getContent(), CoffeeListResponseDto.class);
        coffeeListResponseDtos.forEach(coffeeListResponseDto -> {
            coffeeListResponseDto.setRating(ratingRepository.findRatingByCoffeeId(coffeeListResponseDto.getId()));
            coffeeListResponseDto.setNoOfTimesOrdered(cartRepository.countByCoffeeIdAndOrderIdNotNull(coffeeListResponseDto.getId()));
        });
        responseDto = new PaginationResponseDto<>(
                coffeeList.getTotalElements(),
                coffeeList.getPageable().getPageNumber(),
                coffeeListResponseDtos);
        return responseDto;
    }

    @Override
    public List<CoffeeListResponseDto> getListOfFeaturedCoffee() {
        List<Coffee> coffeeList = coffeeRepository.findAllByIsFeatured(true);
        List<CoffeeListResponseDto> coffeeListResponseDtos = ObjectMapperUtil.mapAll(coffeeList, CoffeeListResponseDto.class);
        coffeeListResponseDtos.forEach(coffeeListResponseDto -> {
            coffeeListResponseDto.setRating(ratingRepository.findRatingByCoffeeId(coffeeListResponseDto.getId()));
            coffeeListResponseDto.setNoOfTimesOrdered(cartRepository.countByCoffeeIdAndOrderIdNotNull(coffeeListResponseDto.getId()));
        });
        return coffeeListResponseDtos;
    }

    @Override
    public List<CoffeeListResponseDto> getListOfCoffeeByPastOrder(Long userId) {
        List<Coffee> coffeeList = coffeeRepository.findByPastOrder(userId);
        List<CoffeeListResponseDto> coffeeListResponseDtos = ObjectMapperUtil.mapAll(coffeeList, CoffeeListResponseDto.class);
        coffeeListResponseDtos.forEach(coffeeListResponseDto -> {
            coffeeListResponseDto.setRating(ratingRepository.findRatingByCoffeeId(coffeeListResponseDto.getId()));
            coffeeListResponseDto.setNoOfTimesOrdered(cartRepository.countByCoffeeIdAndOrderIdNotNull(coffeeListResponseDto.getId()));
        });
        return coffeeListResponseDtos;
    }
}

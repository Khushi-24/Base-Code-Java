package com.demo.BaristaBucks.Service;

import com.demo.BaristaBucks.Common.PaginationResponseDto;
import com.demo.BaristaBucks.Dto.RequestDto.CoffeeRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.FeatureDto;
import com.demo.BaristaBucks.Dto.RequestDto.PaginationRequestDto;
import com.demo.BaristaBucks.Dto.ResponseDto.CoffeeListResponseDto;

import java.util.List;

public interface CoffeeService {
    CoffeeRequestDto addUpdateCoffee(CoffeeRequestDto requestDto);

    Boolean featureCoffee(FeatureDto requestDto);

    PaginationResponseDto<CoffeeListResponseDto> getListOfCoffee(PaginationRequestDto requestDto);

    List<CoffeeListResponseDto> getListOfFeaturedCoffee();

    List<CoffeeListResponseDto> getListOfCoffeeByPastOrder(Long userId);
}

package com.demo.BaristaBucks.Controller;

import com.demo.BaristaBucks.Common.PaginationResponseDto;
import com.demo.BaristaBucks.Dto.RequestDto.CoffeeRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.FeatureDto;
import com.demo.BaristaBucks.Dto.RequestDto.PaginationRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.UserRequestDto;
import com.demo.BaristaBucks.Dto.ResponseDto.CoffeeListResponseDto;
import com.demo.BaristaBucks.Service.CoffeeService;
import com.demo.BaristaBucks.Util.ApiResponse;
import com.demo.BaristaBucks.Util.CollectionUtility;
import com.demo.BaristaBucks.Util.EndPoints;
import com.demo.BaristaBucks.Util.SuccessMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoffeeController {

    private final CoffeeService coffeeService;

    @PostMapping(EndPoints.Coffee.ADD_UPDATE_COFFEE)
    public ResponseEntity<?> addUpdateCoffee(@Valid @RequestBody CoffeeRequestDto requestDto) {
        CoffeeRequestDto coffee = coffeeService.addUpdateCoffee(requestDto);
        return ApiResponse.sendCreatedResponse(SuccessMessages.Coffee.COFFEE_ADDED_SUCCESSFULLY, coffee);
    }

    @PostMapping(EndPoints.Coffee.FEATURE_UN_FEATURE_COFFEE)
    public ResponseEntity<?> featureCoffee(@Valid @RequestBody FeatureDto requestDto) {
        Boolean featured = coffeeService.featureCoffee(requestDto);
        if(featured){
            return ApiResponse.sendOkResponse(SuccessMessages.Coffee.COFFEE_FEATURED_SUCCESSFULLY, null);
        }else{
            return ApiResponse.sendOkResponse(SuccessMessages.Coffee.COFFEE_UN_FEATURED_SUCCESSFULLY, null);
        }
    }

    @PostMapping(EndPoints.Coffee.GET_ALL_COFFEE_LIST)
    public ResponseEntity<?> getListOfCoffee(@Valid @RequestBody PaginationRequestDto requestDto) {
        PaginationResponseDto<CoffeeListResponseDto> coffee = coffeeService.getListOfCoffee(requestDto);
        if(coffee == null){
            return ApiResponse.sendNoContentResponse();
        }
        return ApiResponse.sendOkResponse(SuccessMessages.Coffee.COFFEE_LIST_FETCHED_SUCCESSFULLY, coffee);
    }

    @GetMapping(EndPoints.Coffee.GET_LIST_OF_FEATURED_COFFEE)
    public ResponseEntity<?> getListOfFeaturedCoffee() {
        List<CoffeeListResponseDto> coffee = coffeeService.getListOfFeaturedCoffee();
        if(!CollectionUtility.nonNullNonEmpty(coffee)){
            return ApiResponse.sendNoContentResponse();
        }
        return ApiResponse.sendOkResponse(SuccessMessages.Coffee.FEATURED_COFFEE_LIST_FETCHED_SUCCESSFULLY, coffee);
    }

    @PostMapping(EndPoints.Coffee.GET_LIST_OF_COFFEE_BY_PAST_ORDER)
    public ResponseEntity<?> getListOfCoffeeByPastOrder(@PathVariable Long userId) {
        List<CoffeeListResponseDto> coffee = coffeeService.getListOfCoffeeByPastOrder(userId);
        if(!CollectionUtility.nonNullNonEmpty(coffee)){
            return ApiResponse.sendNoContentResponse();
        }
        return ApiResponse.sendOkResponse(SuccessMessages.Coffee.FEATURED_COFFEE_LIST_FETCHED_SUCCESSFULLY, coffee);
    }

}

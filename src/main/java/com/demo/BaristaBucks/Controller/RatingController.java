package com.demo.BaristaBucks.Controller;

import com.demo.BaristaBucks.Dto.RequestDto.RatingRequestDto;
import com.demo.BaristaBucks.Service.RatingService;
import com.demo.BaristaBucks.Util.ApiResponse;
import com.demo.BaristaBucks.Util.EndPoints;
import com.demo.BaristaBucks.Util.SuccessMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping(EndPoints.Rating.ADD_RATING)
    public ResponseEntity<?> addRating(@Valid @RequestBody RatingRequestDto requestDto) {
        RatingRequestDto rating = ratingService.addRating(requestDto);
        return ApiResponse.sendCreatedResponse(SuccessMessages.Rating.RATING_ADDED_SUCCESSFULLY, rating);
    }
}

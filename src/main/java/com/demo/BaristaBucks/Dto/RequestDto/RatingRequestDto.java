package com.demo.BaristaBucks.Dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RatingRequestDto {

    private Long id;

    @NotNull(message = "{user.id_null_empty}")
    private Long userId;

    @NotNull(message = "{coffee.id_null_empty}")
    private Long coffeeId;

    @NotNull(message = "{rating.rating_null_empty}")
    private Float rating;

}

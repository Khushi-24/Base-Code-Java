package com.demo.BaristaBucks.Dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CoffeeRequestDto {

    private Long id;

    @NotEmpty(message = "{coffee.name_null_empty}")
    @NotNull(message = "{coffee.name_null_empty}")
    private String name;

    @NotNull(message = "{coffee.price_null_empty}")
    private Double price;

    @NotEmpty(message = "{coffee.description_null_empty}")
    @NotNull(message = "{coffee.description_null_empty}")
    private String description;

    private String image;

    @NotEmpty(message = "{coffee.size_null_empty}")
    @NotNull(message = "{coffee.size_null_empty}")
    private String size;

    private Boolean isFeatured;


}

package com.demo.BaristaBucks.Dto.ResponseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String email;

    private String name;

    private String profilePicturePath;

    private String countryCode;

    private String phoneNo;

    private Boolean isSuspend;
}

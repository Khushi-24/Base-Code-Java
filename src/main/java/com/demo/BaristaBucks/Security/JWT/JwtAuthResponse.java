package com.demo.BaristaBucks.Security.JWT;

import com.demo.BaristaBucks.Dto.ResponseDto.UserResponseDto;
import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;
    private String refreshToken;
    private UserResponseDto user;
}

package com.demo.BaristaBucks.Service;

import com.demo.BaristaBucks.Dto.RequestDto.GenerateRefreshTokenRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.LogoutRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.UserRequestDto;

import java.util.Map;

public interface UserService {
    UserRequestDto addUpdateUser(UserRequestDto requestDto);

    Map<String, Object> generateTokenFromRefreshToken(GenerateRefreshTokenRequestDto requestDto);

    void logout(LogoutRequestDto requestDto);
}

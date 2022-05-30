package com.demo.BaristaBucks.Controller;

import com.demo.BaristaBucks.Dto.RequestDto.GenerateRefreshTokenRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.LogoutRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.UserRequestDto;
import com.demo.BaristaBucks.Dto.ResponseDto.UserResponseDto;
import com.demo.BaristaBucks.Exception.ForbiddenException;
import com.demo.BaristaBucks.Service.UserService;
import com.demo.BaristaBucks.Util.ApiResponse;
import com.demo.BaristaBucks.Util.EndPoints;
import com.demo.BaristaBucks.Util.SuccessMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(EndPoints.User.ADD_USER)
    public ResponseEntity<?> addUpdateUser(@Valid @RequestBody UserRequestDto requestDto) {
        UserRequestDto user = userService.addUpdateUser(requestDto);
        return ApiResponse.sendCreatedResponse(SuccessMessages.User.USER_ADDED_SUCCESSFULLY, user);
    }

    @PostMapping(EndPoints.User.REFRESH_TOKEN)
    public ResponseEntity<?> refreshToken(@RequestBody @Valid GenerateRefreshTokenRequestDto requestDto) {
        Map<String, Object> map = userService.generateTokenFromRefreshToken(requestDto);
        if (map == null) {
            throw new ForbiddenException();
        }
        return ApiResponse.sendOkResponse("Token Generated", map);

    }

    @PostMapping(EndPoints.User.LOGOUT)
    public ResponseEntity<?> logout(@RequestBody @Valid LogoutRequestDto requestDto) {
        userService.logout(requestDto);
        return ApiResponse.sendOkResponse(SuccessMessages.User.LOGOUT_SUCCESS, null);

    }
}

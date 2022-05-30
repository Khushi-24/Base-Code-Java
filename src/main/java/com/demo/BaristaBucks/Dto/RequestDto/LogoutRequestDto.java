package com.demo.BaristaBucks.Dto.RequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LogoutRequestDto {
    private String deviceToken;

    @NotNull(message = "{user.refresh_token_null_empty}")
    @NotEmpty(message = "{user.refresh_token_null_empty}")
    private String refreshToken;
}
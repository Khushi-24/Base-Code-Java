package com.demo.BaristaBucks.Security.JWT;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class JwtAuthRequest {

    @NotEmpty(message = "{user.user_name_null_empty}")
    @NotNull(message = "{user.user_name_null_empty}")
    private String userName;

    @NotNull(message = "{user.user_name_null_empty}")
    @NotEmpty(message = "{user.user_name_null_empty}")
    private String password;

    @NotNull(message = "{user.device_token_null_empty}")
    @NotEmpty(message = "{user.device_token_null_empty}")
    private String deviceToken;

    @NotNull(message = "{user.refresh_token_null_empty}")
    @NotEmpty(message = "{user.refresh_token_null_empty}")
    private String platformType;
}

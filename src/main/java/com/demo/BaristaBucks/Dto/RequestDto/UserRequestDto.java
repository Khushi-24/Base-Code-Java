package com.demo.BaristaBucks.Dto.RequestDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequestDto {

    private Long id;

    @NotEmpty(message = "{user.user_email_null_empty}")
    @NotNull(message = "{user.user_email_null_empty}")
    private String email;

    @NotEmpty(message = "{user.password_null_empty}")
    @NotNull(message = "{user.password_null_empty}")
    private String password;

    @NotEmpty(message = "{user.user_name_null_empty}")
    @NotNull(message = "{user.user_name_null_empty}")
    private String userName;

    private String profilePicturePath;

    @NotEmpty(message = "{user.user_country_code_null_empty}")
    @NotNull(message = "{user.user_country_code_null_empty}")
    private String countryCode;

    @NotEmpty(message = "{user.user_phone_no_null_empty}")
    @NotNull(message = "{user.user_phone_no_null_empty}")
    private String phoneNo;

    private String role;

}

package com.demo.BaristaBucks.Controller;


import com.demo.BaristaBucks.Dto.ResponseDto.UserResponseDto;
import com.demo.BaristaBucks.Entity.DeviceToken;
import com.demo.BaristaBucks.Entity.User;
import com.demo.BaristaBucks.Exception.BadRequestException;
import com.demo.BaristaBucks.Exception.EntityNotFoundException;
import com.demo.BaristaBucks.Exception.UnAuthorisedException;
import com.demo.BaristaBucks.Repository.DeviceTokenRepository;
import com.demo.BaristaBucks.Repository.UserRepository;
import com.demo.BaristaBucks.Security.JWT.JwtAuthRequest;
import com.demo.BaristaBucks.Security.JWT.JwtAuthResponse;
import com.demo.BaristaBucks.Security.JWT.JwtTokenUtil;
import com.demo.BaristaBucks.Util.EndPoints;
import com.demo.BaristaBucks.Util.ObjectMapperUtil;
import com.demo.BaristaBucks.Util.PlatformType;
import com.demo.BaristaBucks.Util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final DeviceTokenRepository deviceTokenRepository;

    @PostMapping(EndPoints.User.LOGIN)
    public ResponseEntity<?> createToken(@Valid @RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {

        User user = userRepository.findByUserEmail(jwtAuthRequest.getUserName());
        if(user != null){
            boolean isPasswordMatch = passwordEncoder.matches(jwtAuthRequest.getPassword(), user.getPassword());
            if(!isPasswordMatch){
                throw new UnAuthorisedException("Invalid Credentials");
            }
        }else{
            throw new EntityNotFoundException("User doesn't exists.");
        }
        Map<String, Object> tokens = jwtTokenUtil.generateToken(user.getEmail(),
                PlatformType.WEB.name(),
                user.getRole().getName());
        JwtAuthResponse response = new JwtAuthResponse();
        UserResponseDto userResponseDto = new UserResponseDto();
        String jwtToken = (String) tokens.get("Authorization");
        String refreshToken = (String) tokens.get("refreshToken");
        if (!TextUtils.isEmpty(jwtAuthRequest.getDeviceToken())) {
            DeviceToken deviceToken = deviceTokenRepository.findByDeviceToken(jwtAuthRequest.getDeviceToken());
            if (deviceToken == null) {
                deviceToken = new DeviceToken(
                        jwtAuthRequest.getDeviceToken(),
                        jwtAuthRequest.getPlatformType(),
                        user.getId());
            } else {
                deviceToken.setUserId(user.getId());
                deviceToken.setPlatformType(jwtAuthRequest.getPlatformType());
            }
            //save device token to database
            deviceTokenRepository.save(deviceToken);
        }
        response.setToken(jwtToken);
        response.setRefreshToken(refreshToken);
        ObjectMapperUtil.map(user,userResponseDto);
        response.setUser(userResponseDto);

        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.ACCEPTED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
//
//        User user = userRepository.findByEmailAndPassword(jwtAuthRequest.getUserName(), );
//        this.authenticate(jwtAuthRequest.getUserName(), jwtAuthRequest.getPassword());
//        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUserName());
//        String generatedToken = jwtTokenUtil.generateToken(userDetails);
//        JwtAuthResponse response = new JwtAuthResponse();
//        response.setToken(generatedToken);
////        User user = us
////        response.setUser();
//        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.ACCEPTED);
//    }


    private void authenticate(String userName, String userPassword) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, userPassword);
        try{
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("Invalid details.");
            throw new Exception("Invalid details.");
        }
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}

package com.demo.BaristaBucks.ServiceImpl;

import com.demo.BaristaBucks.Dto.RequestDto.GenerateRefreshTokenRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.LogoutRequestDto;
import com.demo.BaristaBucks.Dto.RequestDto.UserRequestDto;
import com.demo.BaristaBucks.Entity.RefreshToken;
import com.demo.BaristaBucks.Entity.Role;
import com.demo.BaristaBucks.Entity.User;
import com.demo.BaristaBucks.Exception.AlreadyExistsException;
import com.demo.BaristaBucks.Exception.EntityNotFoundException;
import com.demo.BaristaBucks.Repository.DeviceTokenRepository;
import com.demo.BaristaBucks.Repository.RefreshTokenRepository;
import com.demo.BaristaBucks.Repository.RoleRepository;
import com.demo.BaristaBucks.Repository.UserRepository;
import com.demo.BaristaBucks.Security.Encryption.Encryption;
import com.demo.BaristaBucks.Security.JWT.JwtTokenUtil;
import com.demo.BaristaBucks.Service.UserService;
import com.demo.BaristaBucks.Util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final DeviceTokenRepository deviceTokenRepository;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserRequestDto addUpdateUser(@Valid @RequestBody UserRequestDto requestDto) {
        User user;
        User oldUser = userRepository.findByUserEmail(requestDto.getEmail());
        Role role = roleRepository.getById(2L);
        if(requestDto.getId() != null){
            user = userRepository.findById(requestDto.getId()).orElseThrow(() -> new EntityNotFoundException(User.class, requestDto.getId()));
            if (user != null && oldUser != null && !user.getId().equals(oldUser.getId()))
                throw new AlreadyExistsException(User.class, requestDto.getEmail());
            assert user != null;
            Long id = user.getId();
            ObjectMapperUtil.map(requestDto, user);
            user.setId(id);
            user.setRole(role);
            user.setPassword(Encryption.sha256(requestDto.getPassword()));
            userRepository.save(user);
        }else {
            user = new User();
            if (oldUser != null)
                throw new AlreadyExistsException(User.class, requestDto.getEmail());
            ObjectMapperUtil.map(requestDto, user);
            user.setPassword(getEncodedPassword(requestDto.getPassword()));
            user.setRole(role);
            userRepository.save(user);
            requestDto.setId(user.getId());
        }
        requestDto.setRole(role.getName());
        requestDto.setPassword(null);
        return requestDto;

    }

    @Override
//    @Transactional
    public Map<String, Object> generateTokenFromRefreshToken(GenerateRefreshTokenRequestDto requestDto) {
        String userId = getUsernameFromToken(requestDto.getAccessToken());
        String userType = getUserTypeFromToken(requestDto.getAccessToken());
        RefreshToken refreshToken1 = refreshTokenRepository.findByToken(requestDto.getRefreshToken());
        if (refreshToken1 == null) {
            if (requestDto.getDeviceToken() != null) {
                deviceTokenRepository.deleteByDeviceToken(requestDto.getDeviceToken());//delete device token in case of force logout
            }
            //returning null as error can't be thrown here.
            //If we throw an error here then deleteByDeviceToken will not get
            //effect as method is @transactional
            return null;
        }
        long diff = System.currentTimeMillis() - refreshToken1.getRefreshDate();
        if (diff > JwtTokenUtil.JWTToken.REFRESH_TOKEN_VALIDITY_MILLISECONDS) {
            if (requestDto.getDeviceToken() != null) {
                deviceTokenRepository.deleteByDeviceToken(requestDto.getDeviceToken());//delete device token in case of force logout
            }
            //returning null as error can't be thrown here.
            //If we throw an error here then deleteByDeviceToken will not get
            //effect as method is @transactional
            return null;
        }
        if (!refreshToken1.getUserId().equals(userId)) {
            if (requestDto.getDeviceToken() != null) {
                //delete device token in case of force logout
                deviceTokenRepository.deleteByDeviceToken(requestDto.getDeviceToken());
            }
            //returning null as error can't be thrown here.
            //If we throw an error here then deleteByDeviceToken will not get
            //effect as method is @transactional
            return null;
        }
        String token;
        token = jwtTokenUtil.generateTokenForRefresh(userId, userType);
        refreshToken1.setRefreshDate(System.currentTimeMillis());
        refreshTokenRepository.save(refreshToken1);
        Map<String, Object> map = new HashMap<>();
        map.put("authenticationToken", token);
        map.put("refreshToken", requestDto.getRefreshToken());
        return map;
    }

    @Override
    @Transactional
    public void logout(LogoutRequestDto requestDto) {
        if (requestDto.getDeviceToken() != null) {
            deviceTokenRepository.deleteByDeviceToken(requestDto.getDeviceToken());
        }
        RefreshToken byToken = refreshTokenRepository.findByToken(requestDto.getRefreshToken());
        if (byToken != null) {
            refreshTokenRepository.deleteById(byToken.getRefreshTokenId());
        }
    }

    private String getUsernameFromToken(String accessToken) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] parts = accessToken.split("\\."); // Splitting header, payload and signature
        JSONObject jObject = new JSONObject(new String(decoder.decode(parts[1])));
        return jObject.getString("sub");
    }

    private String getUserTypeFromToken(String accessToken) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] parts = accessToken.split("\\."); // Splitting header, payload and signature
        JSONObject jObject = new JSONObject(new String(decoder.decode(parts[1])));
        return jObject.getString("userType");
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

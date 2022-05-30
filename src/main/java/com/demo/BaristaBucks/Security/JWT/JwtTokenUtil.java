package com.demo.BaristaBucks.Security.JWT;

import com.demo.BaristaBucks.Entity.RefreshToken;
import com.demo.BaristaBucks.Repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final RefreshTokenRepository refreshTokenRepository;

    public static class JWTToken {
        public static final long REFRESH_TOKEN_VALIDITY_MILLISECONDS = 4 * 24 * 60 * 60 * 1000; //todo: Need to reduce access token validity.
    }

    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;//todo: Need to reduce access token validity.
    private static final String SECRET_KEY = "BaristaBucks";


    public String getUsernameFromToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7, token.length());
        }
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getUserTypeFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("userType", String.class);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
//                .parseClaimsJws(token)
//                .getBody();
//    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes(Charset.forName("UTF-8"))).parseClaimsJws(token.replace("{", "").replace("}","")).getBody();
    }

//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes(Charset.forName("UTF-8"))).compact();
//    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Map<String, Object> generateToken(String userId, String platformType, String userType) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(userId, platformType, userType);
    }

    private Map<String, Object> doGenerateToken(String userId, String platformType, String userType) {
        Map<String, Object> tokenMap = new HashMap<>();
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        claims.put("userType", userType);
        tokenMap.put("Authorization", Jwts.builder()
                .setClaims(claims)
                .setId(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                .setHeaderParam("type", "JWT")
                .compact());
        String refreshToken = UUID.randomUUID().toString();
//           uncomment in case of single login functionality
//        if (refreshTokenRepository.existsByUserId(userId)) {
//            //TODO:need to work on it as delete does not work without transactional
//            refreshTokenRepository.deleteByUserId(userId);
//        }
        Long refTokenId = refreshTokenRepository.save(new RefreshToken(refreshToken, userId, System.currentTimeMillis(), platformType)).getRefreshTokenId();
        tokenMap.put("refreshToken", refreshToken);
        tokenMap.put("expiration", System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000);
        return tokenMap;
    }

    public String generateTokenForRefresh(String userId, String userType) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("userType", userType);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        return Jwts.builder()
                .setClaims(claims)
                .setId(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                .setHeaderParam("type", "JWT")
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}

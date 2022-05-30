package com.demo.BaristaBucks.Security.JWT;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestToken = request.getHeader("Authorization");
        String userName = null;
        String userType = null;
        String token = null;

        if(requestToken != null && requestToken.startsWith("Bearer")){
            token = requestToken.substring(7);

            try {
                userName = jwtTokenUtil.getUsernameFromToken(token);
                userType = jwtTokenUtil.getUserTypeFromToken(token);
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get Jwt token");
            }catch (ExpiredJwtException e){
                System.out.println("Token Expired");
            }
        }else{
            System.out.println("Jwt token doesn't start with bearer");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if(jwtTokenUtil.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + userName + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                System.out.println("Invalid Jwt Token");
            }
        }else {
            System.out.println("Username is null or context is not null.");
        }
        filterChain.doFilter(request, response);
    }
}

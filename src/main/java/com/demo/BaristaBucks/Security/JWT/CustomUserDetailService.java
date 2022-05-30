package com.demo.BaristaBucks.Security.JWT;

import com.demo.BaristaBucks.Entity.User;
import com.demo.BaristaBucks.Exception.EntityNotFoundException;
import com.demo.BaristaBucks.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException(CustomUserDetailService.class, username));
        return user;
    }
}

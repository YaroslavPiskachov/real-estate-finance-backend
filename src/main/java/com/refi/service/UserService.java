package com.refi.service;

import com.refi.dto.LoginRequest;
import com.refi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public String login(LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtUtil.createToken(userDetails.getUsername(), userDetails
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid login credentials");
        }
    }
}

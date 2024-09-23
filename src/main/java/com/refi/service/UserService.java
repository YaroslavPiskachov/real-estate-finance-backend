package com.refi.service;

import com.refi.InvalidLoginException;
import com.refi.dto.LoginRequest;
import com.refi.dto.LoginResponse;
import com.refi.dto.RegistrationRequest;
import com.refi.model.User;
import com.refi.repository.UserRepository;
import com.refi.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final long expiration;
    private final UserRepository userRepository;

    public UserService(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                       @Value("${security.jwt.expiration}") long expiration, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.expiration = expiration;
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) throws InvalidLoginException {
        String email = loginRequest.email();
        String password = loginRequest.password();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            return getLoginResponse(authentication);
        } catch (AuthenticationException e) {
            log.error("Invalid credentials for email: {}", email, e);
            throw new InvalidLoginException("Invalid login attempt");
        }
    }

    private LoginResponse getLoginResponse(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new LoginResponse(jwtUtil.createToken(userDetails.getUsername(), userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList()), expiration);
    }

    public void register(@Valid RegistrationRequest registrationRequest) {
        userRepository.save(User.builder()
                .email(registrationRequest.email())
                .fullName(registrationRequest.fullname())
                .passwordHash(registrationRequest.password())
                .mfaEnabled(false)
                .build());
    }
}

package com.refi.controller;

import com.refi.InvalidLoginException;
import com.refi.dto.ErrorResponse;
import com.refi.dto.LoginResponse;
import com.refi.dto.RegistrationRequest;
import com.refi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.refi.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws InvalidLoginException {
        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        userService.register(registrationRequest);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(InvalidLoginException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}

package com.refi.controller;

import com.refi.service.UserService;
import lombok.RequiredArgsConstructor;
import com.refi.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();
        return userService.login(email, password);
    }
}

package com.refi.dto;

public record LoginResponse(String token, Long expiresIn) {
}

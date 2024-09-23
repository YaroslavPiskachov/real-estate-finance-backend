package com.refi.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;
}

package com.example.systemshop.payload;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "user.username.required")
    private String username;

    @NotBlank(message = "user.password.required")
    private String password;
} 
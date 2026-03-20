package com.system.shop.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "refresh.token.required")
    private String refreshToken;
}

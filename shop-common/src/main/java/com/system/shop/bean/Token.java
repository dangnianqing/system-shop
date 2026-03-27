package com.system.shop.bean;

import lombok.Data;

import java.io.Serializable;


@Data
public class Token implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String refreshToken;

    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

    public Token() {
    }
}

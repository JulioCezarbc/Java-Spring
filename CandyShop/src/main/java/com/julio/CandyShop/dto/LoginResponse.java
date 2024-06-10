package com.julio.CandyShop.dto;

public record LoginResponse(String accessToken, String refreshToken, Long accessExpiresIn, Long refreshExpiresIn) {
}

package br.edu.ifpb.dac.ecommerce.presentation.dto;

public record LoginResponseDto(
        String accessToken,
        String username
) {}
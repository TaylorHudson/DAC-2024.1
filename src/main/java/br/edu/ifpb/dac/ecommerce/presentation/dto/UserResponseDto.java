package br.edu.ifpb.dac.ecommerce.presentation.dto;

public record UserResponseDto(
        Long id,
        String username,
        String email
) {}
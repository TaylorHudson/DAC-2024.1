package br.edu.ifpb.dac.ecommerce.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenValidRequestDto(
        @NotBlank(message = "Access token must not be blank")
        String accessToken
) {}
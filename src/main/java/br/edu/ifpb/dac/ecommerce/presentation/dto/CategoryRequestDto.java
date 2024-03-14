package br.edu.ifpb.dac.ecommerce.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(
        @NotBlank(message = "Category name must not be blank")
        String name,
        @NotBlank(message = "Category description must not be blank")
        String description
) {}
package br.edu.ifpb.dac.ecommerce.presentation.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record ProductRequestDto(
        @NotBlank(message = "Product name must not be blank")
        String name,
        @NotBlank(message = "Product description must not be blank")
        String description,
        Double price,
        Set<String> images,
        Long categoryId
) {}
package br.edu.ifpb.dac.ecommerce.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ProductRequestDto(
        @NotEmpty
        @NotNull
        String name,
        @NotEmpty
        @NotNull
        String description,
        @NotEmpty
        @NotNull
        Double price,
        Set<String> images,
        @NotEmpty
        @NotNull
        Long categoryId
) {}
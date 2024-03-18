package br.edu.ifpb.dac.ecommerce.presentation.dto;

import java.util.Set;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        Double price,
        Set<String> images,
        Long categoryId
) {}
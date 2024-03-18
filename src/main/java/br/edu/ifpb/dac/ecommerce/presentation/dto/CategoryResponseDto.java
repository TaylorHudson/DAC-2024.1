package br.edu.ifpb.dac.ecommerce.presentation.dto;

import java.util.List;

public record CategoryResponseDto(
        Long id,
        String name,
        String description,
        List<Long> relatedProductsIds
) {}
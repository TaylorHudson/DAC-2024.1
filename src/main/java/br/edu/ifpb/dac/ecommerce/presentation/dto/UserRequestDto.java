package br.edu.ifpb.dac.ecommerce.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequestDto(
        Long id,
        @NotBlank(message = "Nome de usuário é obrigatório")
        String username,
        @Email(message = "Formato de email inválido")
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String password,
        @NotBlank(message = "Cpf é obrigatório")
        @CPF(message = "Formato de cpf inválido")
        String document
) {}
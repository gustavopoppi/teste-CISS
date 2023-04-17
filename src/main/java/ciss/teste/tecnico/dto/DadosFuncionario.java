package ciss.teste.tecnico.dto;

import jakarta.validation.constraints.*;

public record DadosFuncionario(
        Long id,

        @NotBlank
        @Size(min = 2, max = 30)
        String nome,

        @NotBlank
        @Size(min = 2, max = 50)
        String sobreNome,

        @NotBlank
        @Email
        String email,

        @Pattern(regexp = "\\d{11}", message = "Deve ser um número de 11 dígitos")
        String numeroPIS) {
}
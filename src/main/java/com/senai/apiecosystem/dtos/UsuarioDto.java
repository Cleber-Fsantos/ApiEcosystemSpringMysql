package com.senai.apiecosystem.dtos;

import com.senai.apiecosystem.models.UsuarioModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UsuarioDto(
        @NotBlank String nome,
        @NotBlank @Email(message = "O Email deve estar em formato válido") String email,
        @NotBlank String senha,
        String telefone,

        String genero,

        String cpf,

        String cnpj,

        @NotBlank UUID id_tipoUsuario,

        UUID id_endereco

) {

}

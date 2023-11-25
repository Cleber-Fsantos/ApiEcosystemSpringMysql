package com.senai.apiecosystem.dtos;

import com.senai.apiecosystem.models.TipoModel;
import com.senai.apiecosystem.models.UsuarioModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsuarioDto(
        @NotBlank String nome,
        @NotBlank @Email(message = "O Email deve estar em formato válido") String email,
        @NotBlank String senha,
        String telefone,

        String genero,

        String cpf,

        String cnpj,

        String tipo_User,

        @NotNull UUID id_tipousuario,

        UUID id_endereco

) {

}

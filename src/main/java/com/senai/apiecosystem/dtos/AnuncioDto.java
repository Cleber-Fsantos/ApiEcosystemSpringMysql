package com.senai.apiecosystem.dtos;

import com.senai.apiecosystem.models.UsuarioModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

public record AnuncioDto(
        @NotBlank String titulo,
        //@NotBlank LocalDate data_cadastro,
        @NotBlank String data_retirada,
        @NotBlank String período_retirada,

        @NotBlank String status_anuncio,

        String url_imagem,

        @NotNull UUID id_usuario,

        @NotNull UUID id_produto

) {

}

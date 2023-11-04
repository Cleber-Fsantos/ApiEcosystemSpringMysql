package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ColetaDto(
        @NotBlank String status_coleta,

        @NotBlank String data_retirada,

        @NotBlank String observacao,

        @NotNull UUID id_usuario,

        @NotNull UUID id_anuncio

) {

}

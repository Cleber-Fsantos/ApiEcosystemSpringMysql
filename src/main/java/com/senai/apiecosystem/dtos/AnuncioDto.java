package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record AnuncioDto(
        @NotBlank String titulo,

        @NotBlank String data_retirada,
        @NotBlank String período_retirada,

        @NotBlank String status_anuncio,

        MultipartFile imagem,

        @NotNull UUID id_usuario,

        @NotNull UUID id_produto

) {

}

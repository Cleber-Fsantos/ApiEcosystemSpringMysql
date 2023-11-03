package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProdutoDto(
        @NotNull String nome,
        Integer quantidade,
        @NotNull UUID id_categoria
) {

}

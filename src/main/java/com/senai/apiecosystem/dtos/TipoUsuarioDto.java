package com.senai.apiecosystem.dtos;

import jakarta.validation.constraints.NotNull;

public record TipoUsuarioDto(
        @NotNull Integer tipo_usuario
) {

}

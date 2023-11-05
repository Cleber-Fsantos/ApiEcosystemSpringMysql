package com.senai.apiecosystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_coleta")
public class ColetaModel implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private LocalDate data_cadastro;

    private String data_retirada;

    private String status_coleta;

    private String observacao;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioModel usuario;

    @OneToOne
    @JoinColumn(name = "id_anuncio", referencedColumnName = "id")
    private AnuncioModel anuncio;

}
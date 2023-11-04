package com.senai.apiecosystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_anuncio")
public class AnuncioModel implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String titulo;

    private LocalDate data_cadastro;

    private String data_retirada;

    private String período_retirada;

    private String status_anuncio;

    private String url_imagem;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioModel usuario;

    @OneToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private ProdutoModel produto;

}
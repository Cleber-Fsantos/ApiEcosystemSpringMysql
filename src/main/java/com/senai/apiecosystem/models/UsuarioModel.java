package com.senai.apiecosystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_usuario")
public class UsuarioModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String nome;

    private String email;

    private String senha;

    private String telefone;

    private String genero;

    private String cpf;

    private String cnpj;

    @ManyToOne
    @JoinColumn(name = "id_tipoUsuario", referencedColumnName = "id")
    private UsuarioModel id_tipoUsuario;

    @ManyToOne
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private UsuarioModel id_endereco;


}
package com.senai.apiecosystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_usuario")
public class UsuarioModel implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

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

    private TipoModel tipo_usuario_teste;

    @OneToOne
    @JoinColumn(name = "id_tipousuario", referencedColumnName = "id")
    private TipoUsuarioModel tipousuario;


    @OneToOne
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private EnderecoModel endereco;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.tipo_usuario_teste == TipoModel.ADMIN){
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_DOADOR"),
                    new SimpleGrantedAuthority("ROLE_COLETOR")
            );
        } else if (this.tipo_usuario_teste == TipoModel.DOADOR) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_DOADOR")
            );
        } else if (this.tipo_usuario_teste == TipoModel.COLETOR) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_COLETOR")
            );
        }
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
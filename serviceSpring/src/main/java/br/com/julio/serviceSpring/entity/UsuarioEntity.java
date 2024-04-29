package br.com.julio.serviceSpring.entity;

import br.com.julio.serviceSpring.dto.UsuarioDTO;
import br.com.julio.serviceSpring.entity.enums.TipoSituacaoUsuario;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSituacaoUsuario situacaoUsuario;

    public UsuarioEntity(){
    }

    public UsuarioEntity(UsuarioDTO usuarioDTO){
        BeanUtils.copyProperties(usuarioDTO, this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public TipoSituacaoUsuario getSituacaoUsuario() {
        return situacaoUsuario;
    }

    public void setSituacaoUsuario(TipoSituacaoUsuario situacaoUsuario) {
        this.situacaoUsuario = situacaoUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package br.com.julio.serviceSpring.entity;

import br.com.julio.serviceSpring.dto.PerfilUsuarioDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "perfil_usuario")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PerfilUsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;
    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private PerfilEntity perfil;

    public PerfilUsuarioEntity(PerfilUsuarioDTO perfilUsuario){
        BeanUtils.copyProperties(perfilUsuario, this);
        if (perfilUsuario != null && perfilUsuario.getUsuario() != null){
            this.usuario = new UsuarioEntity(perfilUsuario.getUsuario());
        }
        if (perfilUsuario != null && perfilUsuario.getPerfil() != null){
            this.perfil = new PerfilEntity(perfilUsuario.getPerfil());
        }

    }

}

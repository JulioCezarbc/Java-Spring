package br.com.julio.serviceSpring.entity;


import br.com.julio.serviceSpring.dto.PerfilDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Table(name = "perfil")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PerfilEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String descricao;

    public PerfilEntity(PerfilDTO perfilDTO){
        BeanUtils.copyProperties(perfilDTO,this);
    }

}

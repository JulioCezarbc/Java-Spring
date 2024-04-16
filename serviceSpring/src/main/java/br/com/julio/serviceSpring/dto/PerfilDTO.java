package br.com.julio.serviceSpring.dto;

import br.com.julio.serviceSpring.entity.PerfilEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class PerfilDTO {
    private Long id;
    private String descricao;
    public PerfilDTO(PerfilEntity perfilEntity){
        BeanUtils.copyProperties(perfilEntity,this);
    }

}

package br.com.julio.serviceSpring.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarios_verificador")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioVerificadorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", unique = true)
    private UsuarioEntity usuario;
    @Column(nullable = false)
    private Instant dataExpiracao;
}

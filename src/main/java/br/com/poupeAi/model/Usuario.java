package br.com.poupeAi.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
@Hidden
public class Usuario extends AbstractEntity{
    private String nome;
    private String email;
    private String senha;

    @Builder
    public Usuario(Long id, String nome, String email, String senha) {
        super(id);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}

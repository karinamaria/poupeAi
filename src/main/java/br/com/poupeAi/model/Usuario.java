package br.com.poupeAi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class Usuario extends AbstractEntity{
    private String nome;
    private String email;
    private String senha;
}

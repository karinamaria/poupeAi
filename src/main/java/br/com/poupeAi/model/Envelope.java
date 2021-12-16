package br.com.poupeAi.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "envelope")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode(exclude = {"orcamento", "despesas"})
@Hidden
public class Envelope extends AbstractEntity{
    private String nome;
    private double orcamento;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Despesa> despesas = new HashSet<>();;
}

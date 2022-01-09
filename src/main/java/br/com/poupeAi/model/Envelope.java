package br.com.poupeAi.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import javax.persistence.*;
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch= FetchType.EAGER)
    private Set<Despesa> despesas = new HashSet<>();

    @Builder
    public Envelope(Long id, String nome, double orcamento, Set<Despesa> despesas) {
        super(id);
        this.nome = nome;
        this.orcamento = orcamento;
        this.despesas = despesas;
    }
}

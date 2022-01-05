package br.com.poupeAi.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<Despesa> despesas = new ArrayList<>();

    @Builder
    public Envelope(Long id, String nome, double orcamento, List<Despesa> despesas) {
        super(id);
        this.nome = nome;
        this.orcamento = orcamento;
        this.despesas = despesas;
    }
}

package br.com.poupeAi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "envelope")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class Envelope extends AbstractEntity{
    private String nome;
    private double orcamento;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PlanejamentoMensal planejamentoMensal;
}

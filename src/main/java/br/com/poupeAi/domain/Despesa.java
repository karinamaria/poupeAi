package br.com.poupeAi.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "despesa")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class Despesa extends AbstractEntity{
    private double quantia;
    private boolean ehParaOutroEnvelope;
    @OneToOne(cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private Envelope envelope;
}

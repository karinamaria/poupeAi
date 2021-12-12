package br.com.poupeAi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "despesa")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
@Hidden
public class Despesa extends AbstractEntity{
    private double quantia;
    private boolean ehParaOutroEnvelope = false;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private Envelope envelope;
}

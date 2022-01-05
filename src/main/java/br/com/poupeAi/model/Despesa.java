package br.com.poupeAi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

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

    @Builder
    public Despesa(Long id, double quantia, boolean ehParaOutroEnvelope, Envelope envelope) {
        super(id);
        this.quantia = quantia;
        this.ehParaOutroEnvelope = ehParaOutroEnvelope;
        this.envelope = envelope;
    }

    @Override
    public boolean equals(Object o) {
        if(super.equals(o) && o instanceof Despesa)
            return o == this;

        return false;
    }
}

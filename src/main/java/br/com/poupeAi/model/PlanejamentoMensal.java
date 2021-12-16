package br.com.poupeAi.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "planejamento_mensal")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
@Hidden
public class PlanejamentoMensal extends AbstractEntity{
    private int frequenciaEnvioRelatorio;
    private int mes;
    private int ano;
    @ManyToOne
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Envelope> envelopes  = new HashSet<>();
}

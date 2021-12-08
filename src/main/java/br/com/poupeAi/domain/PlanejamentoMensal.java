package br.com.poupeAi.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "planejamento_mensal")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class PlanejamentoMensal extends AbstractEntity{
    private int frequenciaEnvioRelatorio;
    private int mês;
    private int ano;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario usuario;
}

package br.com.poupeAi.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "planejamento_mensal")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
@Hidden
public class PlanejamentoMensal extends AbstractEntity{
    //private int frequenciaEnvioRelatorio;
    private int mes;
    private int ano;
    @ManyToOne
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Envelope> envelopes  = new HashSet<>();

    @Builder
    public PlanejamentoMensal(Long id, int mes, int ano, Usuario usuario, Set<Envelope> envelopes) {
        super(id);
        this.mes = mes;
        this.ano = ano;
        this.usuario = usuario;
        this.envelopes = envelopes;
    }
}

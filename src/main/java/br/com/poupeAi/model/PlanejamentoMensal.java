package br.com.poupeAi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planejamento_mensal")
@Getter @Setter @EqualsAndHashCode
public class PlanejamentoMensal extends AbstractEntity{
    private int frequenciaEnvioRelatorio;
    private int mes;
    private int ano;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Envelope> envelopes;

    public PlanejamentoMensal(){
        this.envelopes=new ArrayList<>();
    }
}

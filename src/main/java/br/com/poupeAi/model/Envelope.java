package br.com.poupeAi.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "envelope")
@Getter @Setter @EqualsAndHashCode
@Hidden
public class Envelope extends AbstractEntity{
    private String nome;
    private double orcamento;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Despesa> despesas;

    public Envelope(){
        this.despesas = new ArrayList<>();
    }
}

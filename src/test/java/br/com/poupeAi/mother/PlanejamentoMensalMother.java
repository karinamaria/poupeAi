package br.com.poupeAi.mother;

import br.com.poupeAi.model.PlanejamentoMensal;

import java.util.Collections;
import java.util.HashSet;

import static br.com.poupeAi.mother.EnvelopeMother.createAnExistingEnvelope;
import static br.com.poupeAi.mother.EnvelopeMother.createDefaultEnvelopes;
import static br.com.poupeAi.mother.UsuarioMother.createAValidUsuario;
import static br.com.poupeAi.mother.UsuarioMother.getAnExistingUsuario;

/**
 * PlanejamentoMensalMother é uma classe object mother
 * usada para ajudar a criar objetos de exemplo usados
 * nos testes
 */
public class PlanejamentoMensalMother {
    public static final int MONTH = 12;
    public static final int YEAR = 2021;

    public static PlanejamentoMensal createNewValidPlanejamento(){
        return PlanejamentoMensal.builder()
                .mes(MONTH)
                .ano(YEAR)
                .build();
    }

    public static PlanejamentoMensal createPlanejamentoExpected(){
        return PlanejamentoMensal.builder()
                .id(1L)
                .ano(MONTH)
                .mes(YEAR)
                .usuario(getAnExistingUsuario())
                .envelopes(createDefaultEnvelopes())
                .build();
    }

    public static PlanejamentoMensal createPlanejamentoWithoutEnvelopes(){
        return PlanejamentoMensal.builder()
                .id(1L)
                .ano(MONTH)
                .mes(YEAR)
                .usuario(getAnExistingUsuario())
                .envelopes(new HashSet<>())
                .build();
    }

    public static PlanejamentoMensal createPlanejamentoWithEnvelope(){
        return PlanejamentoMensal.builder()
                .id(1L)
                .ano(MONTH)
                .mes(YEAR)
                .usuario(getAnExistingUsuario())
                .envelopes(new HashSet<>(Collections.singleton(createAnExistingEnvelope())))
                .build();
    }

}

package br.com.poupeAi.mother;

import br.com.poupeAi.model.PlanejamentoMensal;

import static br.com.poupeAi.mother.EnvelopeMother.createDefaultEnvelopes;
import static br.com.poupeAi.mother.UsuarioMother.createAValidUsuario;

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
                .usuario(createAValidUsuario())
                .envelopes(createDefaultEnvelopes())
                .build();
    }

}

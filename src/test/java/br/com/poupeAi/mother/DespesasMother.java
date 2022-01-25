package br.com.poupeAi.mother;

import br.com.poupeAi.model.Despesa;

import static br.com.poupeAi.mother.EnvelopeMother.getAnOtherEnvelope;

public class DespesasMother {
    public static Despesa createAValidDespesa(){
        return  Despesa.builder()
                .quantia(500)
                .ehParaOutroEnvelope(false)
                .build();
    }

    public static Despesa getExistingDespesa(){
        return Despesa.builder()
                .id(1L)
                .quantia(500)
                .ehParaOutroEnvelope(false)
                .build();
    }

    public static Despesa getEditedDespesa(){
        return  Despesa.builder()
                .id(1L)
                .quantia(500)
                .ehParaOutroEnvelope(true)
                .envelope(getAnOtherEnvelope())
                .build();
    }

    public static Despesa createAnInvalidDespesa(){
        return Despesa.builder()
                .quantia(500)
                .ehParaOutroEnvelope(true)
                .build();
    }
}

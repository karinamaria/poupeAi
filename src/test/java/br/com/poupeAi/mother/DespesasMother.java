package br.com.poupeAi.mother;

import br.com.poupeAi.model.Despesa;

public class DespesasMother {
    public static Despesa getExistingDespesa(){
        return Despesa.builder()
                .id(1L)
                .quantia(500)
                .ehParaOutroEnvelope(false)
                .build();
    }

}

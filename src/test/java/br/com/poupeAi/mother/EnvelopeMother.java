package br.com.poupeAi.mother;

import br.com.poupeAi.model.Envelope;
import br.com.poupeAi.model.EnvelopeDefaultEnum;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static br.com.poupeAi.mother.DespesasMother.getExistingDespesa;

/**
 * EnvelopeMother é uma classe object mother
 * usada para ajudar a criar objetos de exemplo usados
 * nos testes
 */
public class EnvelopeMother {
    public static Set<Envelope> createDefaultEnvelopes(){
        Set<Envelope> envelopes = new HashSet<>();

        for(EnvelopeDefaultEnum e: EnvelopeDefaultEnum.values()){
            Envelope envelope = new Envelope();
            envelope.setNome(e.toString());
            envelope.setOrcamento(0f);
            envelopes.add(envelope);
        }
        return envelopes;
    }

    public static Envelope createNewValidEnvelope(){
        return Envelope.builder()
                .nome("Teste 1")
                .orcamento(1000)
                .build();
    }

    public static Envelope createAnExistingEnvelope(){
        return Envelope.builder()
                .id(1L)
                .nome("Teste 1")
                .orcamento(1000)
                .despesas(new HashSet<>(Collections.singleton(getExistingDespesa())))
                .build();
    }

    public static Envelope createAnExistingEnvelopeWithNoDepesas(){
        return Envelope.builder()
                .id(1L)
                .nome("Teste 1")
                .orcamento(1000)
                .despesas(new HashSet<>())
                .build();
    }

    public static Envelope createAnExistingEnvelopeWithNoBalance(){
        return Envelope.builder()
                .id(1L)
                .nome("Teste 1")
                .orcamento(0)
                .despesas(new HashSet<>())
                .build();
    }

    public static Envelope getAnOtherEnvelope(){
        return Envelope.builder()
                .id(2L)
                .nome("Teste 2")
                .orcamento(700)
                .despesas(new HashSet<>())
                .build();
    }
}

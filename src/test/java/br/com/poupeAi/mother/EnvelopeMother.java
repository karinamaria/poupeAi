package br.com.poupeAi.mother;

import br.com.poupeAi.model.Envelope;
import br.com.poupeAi.model.EnvelopeDefaultEnum;

import java.util.HashSet;
import java.util.Set;

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
            envelope.setNome(e.getDescricao());
            envelope.setOrcamento(0f);
            envelopes.add(envelope);
        }
        return envelopes;
    }
}

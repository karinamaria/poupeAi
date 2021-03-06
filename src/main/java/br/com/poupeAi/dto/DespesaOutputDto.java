package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="DespesaOutputDto")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class DespesaOutputDto {
    private Long id;
    private double quantia;
    private boolean ehParaOutroEnvelope;
    private OutroEnvelopeDto envelope;
}

package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Schema(name="DespesaInputDto")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class DespesaInputDto {
    @NotNull
    @PositiveOrZero
    private double quantia;
    @NotNull
    private boolean ehParaOutroEnvelope;
    private OutroEnvelopeDto envelope;
}

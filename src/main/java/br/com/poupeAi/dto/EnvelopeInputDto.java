package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Schema(name="EnvelopeInputDto")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class EnvelopeInputDto {
    @NotNull
    private String nome;
    @NotNull
    @PositiveOrZero
    private double orcamento;
}

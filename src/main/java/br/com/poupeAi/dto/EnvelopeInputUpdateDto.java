package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Schema(name="EnvelopeInputUpdateDto")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class EnvelopeInputUpdateDto {
    @NotNull
    @PositiveOrZero
    private int id;
    @NotNull
    private String nome;
    @NotNull
    @PositiveOrZero
    private double orcamento;
}

package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(name="PlanejamentoMensalInputDto")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class PlanejamentoMensalInputDto {
//    @NotNull
//    @Positive
//    private int frequenciaEnvioRelatorio;

    @NotNull
    @Min(value=1)
    @Max(value=12)
    private int mes;

    @NotNull
    @Min(value=2021)
    private int ano;
}

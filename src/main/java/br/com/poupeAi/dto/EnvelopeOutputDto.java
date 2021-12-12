package br.com.poupeAi.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="EnvelopeOutputDto")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class EnvelopeOutputDto {
    private Long id;
    private String nome;
    private double orcamento;
    private List<DespesaOutputDto> despesas;
}

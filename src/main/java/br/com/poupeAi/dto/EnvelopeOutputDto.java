package br.com.poupeAi.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="EnvelopeOutputDto")
public class EnvelopeOutputDto {
    private Long id;
    private String nome;
    private double orcamento;
    private List<DespesaOutputDto> despesas;
}

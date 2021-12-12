package br.com.poupeAi.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="DespesaOutputDto")
public class DespesaOutputDto {
    private double quantia;
    private boolean ehParaOutroEnvelope;
    private EnvelopeOutputDto envelope;
}

package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Schema(name="PlanejamentoMensalInputDto")
public class PlanejamentoMensalInputDto {
    @NotNull
    @Positive
    private int frequenciaEnvioRelatorio;

    @NotNull
    @Min(value=1)
    @Max(value=12)
    private int mes;

    @NotNull
    @Min(value=2021)
    private int ano;

    public PlanejamentoMensalInputDto(int frequenciaEnvioRelatorio, int mes, int ano) {
        this.frequenciaEnvioRelatorio = frequenciaEnvioRelatorio;
        this.mes = mes;
        this.ano = ano;
    }

    public int getFrequenciaEnvioRelatorio() {
        return frequenciaEnvioRelatorio;
    }

    public void setFrequenciaEnvioRelatorio(int frequenciaEnvioRelatorio) {
        this.frequenciaEnvioRelatorio = frequenciaEnvioRelatorio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}

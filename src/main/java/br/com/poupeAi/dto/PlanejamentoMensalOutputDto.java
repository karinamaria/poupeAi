package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Schema(name="PlanejamentoMensalOutputDto")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class PlanejamentoMensalOutputDto {
    private Long id;
    private int frequenciaEnvioRelatorio;
    private int mes;
    private int ano;
    private UsuarioOutputDto usuario;
    private Set<EnvelopeOutputDto> envelopes;

}

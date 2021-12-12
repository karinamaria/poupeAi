package br.com.poupeAi.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="PlanejamentoMensalOutputDto")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class PlanejamentoMensalOutputDto {
    private Long id;
    private int frequenciaEnvioRelatorio;
    private int mes;
    private int ano;
    private UsuarioOutputDto usuario;
    private List<EnvelopeOutputDto> envelopes;

}

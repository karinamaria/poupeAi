package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.PlanejamentoMensalInputDto;
import br.com.poupeAi.model.PlanejamentoMensal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanejamentoMensalMapper {
    PlanejamentoMensal planejamentoMensalDtoToPlanejamento(PlanejamentoMensalInputDto planejamentoMensalInputDto);
}

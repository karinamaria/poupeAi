package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.PlanejamentoMensalInputDto;
import br.com.poupeAi.dto.PlanejamentoMensalOutputDto;
import br.com.poupeAi.model.PlanejamentoMensal;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PlanejamentoMensalMapper {
    PlanejamentoMensalMapper INSTANCE = Mappers.getMapper( PlanejamentoMensalMapper.class );

    PlanejamentoMensal planejamentoMensalDtoToPlanejamento(PlanejamentoMensalInputDto planejamentoMensalInputDto);
    PlanejamentoMensalOutputDto planejamentoToPlanejamentoOutput(PlanejamentoMensal planejamentoMensal);
}

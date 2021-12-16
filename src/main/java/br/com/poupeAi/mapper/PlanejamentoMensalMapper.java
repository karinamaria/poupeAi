package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.PlanejamentoMensalInputDto;
import br.com.poupeAi.dto.PlanejamentoMensalOutputDto;
import br.com.poupeAi.model.PlanejamentoMensal;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PlanejamentoMensalMapper {
    PlanejamentoMensal planejamentoMensalDtoToPlanejamento(PlanejamentoMensalInputDto planejamentoMensalInputDto);
    PlanejamentoMensalOutputDto planejamentoToPlanejamentoOutput(PlanejamentoMensal planejamentoMensal);
}

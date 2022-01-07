package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.DespesaInputDto;
import br.com.poupeAi.dto.DespesaOutputDto;
import br.com.poupeAi.model.Despesa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DespesaMapper {
    Despesa despesaInputDtoToDespesa(DespesaInputDto despesaInputDto);
    DespesaOutputDto despesaToDespesaOutputDto(Despesa despesa);
}

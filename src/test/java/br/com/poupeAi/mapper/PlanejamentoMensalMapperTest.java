package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.PlanejamentoMensalInputDto;
import br.com.poupeAi.dto.PlanejamentoMensalOutputDto;
import br.com.poupeAi.model.PlanejamentoMensal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.poupeAi.mother.PlanejamentoMensalMother.createPlanejamentoExpected;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Planejamento Mensal Mapper Test")
public class PlanejamentoMensalMapperTest {

    @Test
    void planejamentoMensalDtoToPlanejamento(){
        PlanejamentoMensalInputDto planejamentoMensalInputDto = PlanejamentoMensalInputDto.builder()
                .ano(2021)
                .mes(12)
                .build();

        PlanejamentoMensal planejamentoMensalReturn = PlanejamentoMensalMapper.INSTANCE
                .planejamentoMensalDtoToPlanejamento(planejamentoMensalInputDto);

        assertThat(planejamentoMensalReturn.getMes())
                .isNotNull()
                .isEqualTo(planejamentoMensalInputDto.getMes());
        assertThat(planejamentoMensalReturn.getAno())
                .isNotNull()
                .isEqualTo(planejamentoMensalInputDto.getAno());
        assertThat(planejamentoMensalReturn.getEnvelopes())
                .isNullOrEmpty();
        assertThat(planejamentoMensalReturn.getUsuario())
                .isNull();
        assertThat(planejamentoMensalReturn.getId())
                .isNull();
    }

    @Test
    void planejamentoToPlanejamentoOutput(){
        PlanejamentoMensal planejamentoMensal = createPlanejamentoExpected();

        PlanejamentoMensalOutputDto planejamentoMensalReturn = PlanejamentoMensalMapper.INSTANCE
                .planejamentoToPlanejamentoOutput(planejamentoMensal);

        assertThat(planejamentoMensalReturn.getId())
                .isNotNull()
                .isEqualTo(planejamentoMensal.getId());
        assertThat(planejamentoMensalReturn.getMes())
                .isNotNull()
                .isEqualTo(planejamentoMensal.getMes());
        assertThat(planejamentoMensalReturn.getEnvelopes())
                .hasSize(planejamentoMensal.getEnvelopes().size());
        assertThat(planejamentoMensalReturn.getUsuario().getId())
                .isNotNull()
                .isEqualTo(planejamentoMensal.getUsuario().getId());
    }
}

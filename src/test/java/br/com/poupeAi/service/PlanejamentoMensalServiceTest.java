package br.com.poupeAi.service;

import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.helper.UsuarioHelper;
import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.repository.PlanejamentoMensalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.poupeAi.mother.PlanejamentoMensalMother.createNewValidPlanejamento;
import static br.com.poupeAi.mother.PlanejamentoMensalMother.createPlanejamentoExpected;
import static br.com.poupeAi.mother.UsuarioMother.createAValidUsuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Planejamento Mensal Service Test")
public class PlanejamentoMensalServiceTest {
    @InjectMocks
    private PlanejamentoMensalService planejamentoMensalService;

    @Mock
    private UsuarioHelper usuarioHelper;

    @Mock
    private PlanejamentoMensalRepository planejamentoMensalRepository;

    @Test
    void saveAValidNewPlanejamento(){
        //[GIVEN] a valid planejamento
        PlanejamentoMensal planejamentoMensal = createNewValidPlanejamento();
        Usuario usuario = createAValidUsuario();

        given(usuarioHelper.getUsuarioLogado()).willReturn(usuario);
        given(planejamentoMensalRepository.findByUsuarioAndMesAndAno(usuario, planejamentoMensal.getMes(), planejamentoMensal.getAno()))
                .willReturn(any(PlanejamentoMensal.class));

        //[WHEN] create a planejamento
        PlanejamentoMensal planejamentoMensalExpected = createPlanejamentoExpected();
        when(planejamentoMensalRepository.save(planejamentoMensal)).thenReturn(planejamentoMensalExpected);

        //[THEN] return entity planejamento
        PlanejamentoMensal planejamentoMensalReturn = planejamentoMensalService.salvar(planejamentoMensal);

        assertThat(planejamentoMensalReturn.getId())
                .isNotNull()
                .isEqualTo(planejamentoMensalExpected.getId());
        assertThat(planejamentoMensalReturn.getMes())
                .isNotNull()
                .isEqualTo(planejamentoMensalExpected.getMes());
        assertThat(planejamentoMensalReturn.getAno())
                .isNotNull()
                .isEqualTo(planejamentoMensalExpected.getAno());
        assertThat(planejamentoMensalReturn.getUsuario())
                .isNotNull()
                .isEqualTo(planejamentoMensalExpected.getUsuario());
        assertThat(planejamentoMensalReturn.getEnvelopes())
                .isNotNull()
                .isEqualTo(planejamentoMensalExpected.getEnvelopes());
    }

    @Test
    void verifyCallsInSaveAValidNewPlanejamento(){
        //[GIVEN] a valid planejamento
        PlanejamentoMensal planejamentoMensal = createNewValidPlanejamento();
        Usuario usuario = createAValidUsuario();

        given(usuarioHelper.getUsuarioLogado()).willReturn(usuario);
        given(planejamentoMensalRepository.findByUsuarioAndMesAndAno(usuario, planejamentoMensal.getMes(), planejamentoMensal.getAno()))
                .willReturn(any(PlanejamentoMensal.class));

        //[WHEN] create a planejamento
        PlanejamentoMensal planejamentoMensalExpected = createPlanejamentoExpected();
        when(planejamentoMensalRepository.save(planejamentoMensal)).thenReturn(planejamentoMensalExpected);
        planejamentoMensalService.salvar(planejamentoMensal);

        //[THEN] verify calls to mock class
        verify(usuarioHelper, times(1)).getUsuarioLogado();
        verify(planejamentoMensalRepository, times(1))
                .findByUsuarioAndMesAndAno(any(Usuario.class), anyInt(), anyInt());
        verify(planejamentoMensalRepository, times(1)).save(any(PlanejamentoMensal.class));
    }

    @Test
    void trySaveNewPlanejamentoWhenExistsPlanejamentoToCurrentMonth(){
        //[GIVEN] a valid planejamento
        PlanejamentoMensal planejamentoMensal = createNewValidPlanejamento();
        Usuario usuario = createAValidUsuario();
        PlanejamentoMensal planejamentoMensalToCurrentMonth = createPlanejamentoExpected();

        given(usuarioHelper.getUsuarioLogado()).willReturn(usuario);
        given(planejamentoMensalRepository.findByUsuarioAndMesAndAno(usuario, planejamentoMensal.getMes(), planejamentoMensal.getAno()))
                .willReturn(planejamentoMensalToCurrentMonth);

        //[WHEN] create a planejamento
        //[THEN] expected NegocioException
        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> planejamentoMensalService.salvar(planejamentoMensal))
                .withMessageContaining("O usuário já possui um planejamento para ");

    }
}

package br.com.poupeAi.service;

import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.helper.UsuarioHelper;
import br.com.poupeAi.model.Envelope;
import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.repository.PlanejamentoMensalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static br.com.poupeAi.mother.EnvelopeMother.createAnExistingEnvelope;
import static br.com.poupeAi.mother.EnvelopeMother.createNewValidEnvelope;
import static br.com.poupeAi.mother.PlanejamentoMensalMother.*;
import static br.com.poupeAi.mother.UsuarioMother.getAnExistingUsuario;
import static br.com.poupeAi.mother.UsuarioMother.getAnOtherExistingUsuario;
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
        Usuario usuario = getAnExistingUsuario();

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
        Usuario usuario = getAnExistingUsuario();

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
        Usuario usuario = getAnExistingUsuario();
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

    @Test
    void findByExistingPlanejamento(){
        //[GIVEN] id of existing planejamento
        PlanejamentoMensal planejamentoMensal = createPlanejamentoExpected();
        given(planejamentoMensalRepository.findById(1L))
                .willReturn(Optional.of(planejamentoMensal));
        given(usuarioHelper.getUsuarioLogado()).willReturn(getAnExistingUsuario());

        //[WHEN] find a planejamento
        PlanejamentoMensal planejamentoMensalReturn = planejamentoMensalService.buscarPlanejamentoPeloId(1L);

        //[THEN] verify if return is not null
        assertThat(planejamentoMensalReturn)
                .isNotNull()
                .isEqualTo(planejamentoMensal);
    }

    @Test
    void findByExistingPlanejamentoAnotherUser(){
        //[GIVEN] id of existing planejamento
        PlanejamentoMensal planejamentoMensal = createPlanejamentoExpected();
        given(planejamentoMensalRepository.findById(1L))
                .willReturn(Optional.of(planejamentoMensal));
        given(usuarioHelper.getUsuarioLogado()).willReturn(getAnOtherExistingUsuario());

        //[WHEN] find a planejamento
        //[THEN] expected NegocioException
        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> planejamentoMensalService.buscarPlanejamentoPeloId(1L));
    }

    @Test
    void findByPlanejamentoNotAvailable(){
        //[GIVEN] id of planejamento not available
        given(planejamentoMensalRepository.findById(1L))
                .willReturn(Optional.empty());

        //[WHEN] find a planejamento not available
        //[THEN] expected ResourceNotFoundException
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> planejamentoMensalService.buscarPlanejamentoPeloId(1L))
                .withMessageContaining("Planejamento Mensal não encontrado(a).");
    }

    @Test
    void saveAValidEnvelopeInExistingPlanejamento(){
        //[GIVEN] new envelope to planejamento
        Envelope envelope = createNewValidEnvelope();
        Usuario usuario = getAnExistingUsuario();
        PlanejamentoMensal planejamentoMensalToCurrentMonth = createPlanejamentoExpected();

        given(usuarioHelper.getUsuarioLogado()).willReturn(usuario);
        given(planejamentoMensalRepository.findById(planejamentoMensalToCurrentMonth.getId()))
                .willReturn(Optional.of(planejamentoMensalToCurrentMonth));

        //[WHEN] save a envelope in planejamento
        PlanejamentoMensal planejamentoMensalExpected = createPlanejamentoExpected();
        planejamentoMensalExpected.getEnvelopes().add(envelope);
        when(planejamentoMensalRepository.save(any())).thenReturn(planejamentoMensalExpected);

        PlanejamentoMensal planejamentoMensalReturn = planejamentoMensalService
                .adicionarEnvelope(planejamentoMensalToCurrentMonth.getId(), envelope);
        Optional<Envelope> envelopeReturn = planejamentoMensalReturn
                .getEnvelopes()
                .stream()
                .filter(e -> e.getNome().equalsIgnoreCase(envelope.getNome()))
                .findFirst();

        //[THEN] verify if return is not null
        assertThat(planejamentoMensalReturn.getId())
                .isNotNull()
                .isEqualTo(planejamentoMensalExpected.getId());
        assertThat(envelopeReturn)
                .isPresent()
                .isEqualTo(Optional.of(envelope));
    }

    @Test
    void trySaveAnExistingEnvelopeInPlanejamento(){
        //[GIVEN] new envelope to planejamento
        Usuario usuario = getAnExistingUsuario();
        PlanejamentoMensal planejamentoMensalToCurrentMonth = createPlanejamentoExpected();
        Envelope envelope = planejamentoMensalToCurrentMonth
                .getEnvelopes()
                .stream()
                .filter(e -> e.getNome().equalsIgnoreCase("CARRO"))
                .findFirst()
                .orElse(new Envelope());

        given(usuarioHelper.getUsuarioLogado()).willReturn(usuario);
        given(planejamentoMensalRepository.findById(planejamentoMensalToCurrentMonth.getId()))
                .willReturn(Optional.of(planejamentoMensalToCurrentMonth));

        //[THEN] expected NegocioException
        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> planejamentoMensalService.adicionarEnvelope(1L, envelope))
                .withMessageContaining("Usuário já possui o envelope");

    }

    @Test
    void editEnvelopePlanejamento(){
        //[GIVEN] id of existing planejamento
        PlanejamentoMensal planejamentoMensal = createPlanejamentoWithEnvelope();

        given(planejamentoMensalRepository.findById(1L))
                .willReturn(Optional.of(planejamentoMensal));
        given(usuarioHelper.getUsuarioLogado()).willReturn(getAnExistingUsuario());

        //[WHEN] save a envelope in planejamento
        PlanejamentoMensal planejamentoMensalExpected = createPlanejamentoWithoutEnvelopes();
        Envelope editEnvelope = createAnExistingEnvelope();
        editEnvelope.setOrcamento(5000);
        planejamentoMensalExpected.getEnvelopes().add(editEnvelope);
        when(planejamentoMensalRepository.save(any())).thenReturn(planejamentoMensalExpected);

        PlanejamentoMensal planejamentoMensalReturn = planejamentoMensalService
                .atualizarEnvelope(1L,1L, 5000);

        //[THEN] verify if orcamento was updated
        assertThat(planejamentoMensalReturn.getEnvelopes()
                .stream()
                .filter(e->e.getId().equals(1L))
                .map(Envelope::getOrcamento)
                .findFirst())
                .isPresent()
                .get()
                .isEqualTo(5000.0);

    }

    @Test
    void tryEditEnvelopeOtherPlanejamento(){
        //[GIVEN] id of existing planejamento
        PlanejamentoMensal planejamentoMensal = createPlanejamentoWithEnvelope();

        given(planejamentoMensalRepository.findById(1L))
                .willReturn(Optional.of(planejamentoMensal));
        given(usuarioHelper.getUsuarioLogado()).willReturn(getAnOtherExistingUsuario());

        //[WHEN] save a envelope in planejamento
        //[THEN] expected NegocioException
        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() ->  planejamentoMensalService
                        .atualizarEnvelope(1L,1L, 5000))
                .withMessageContaining("Não é possível cadastrar o envelope");
    }

    @Test
    void tryEditOrcamentoEnvelope(){
        //[GIVEN] id of existing planejamento
        PlanejamentoMensal planejamentoMensal = createPlanejamentoWithEnvelope();

        given(planejamentoMensalRepository.findById(1L))
                .willReturn(Optional.of(planejamentoMensal));
        given(usuarioHelper.getUsuarioLogado()).willReturn(getAnExistingUsuario());

        //[WHEN] save a envelope in planejamento
        //[THEN] expected NegocioException
        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() ->  planejamentoMensalService
                        .atualizarEnvelope(1L,1L, 200))
                .withMessageContaining("O envelope já possui um total de despesas R$ ");

    }

    @Test
    void tryDeleteEnvelopeWithDespesas(){
        //[GIVEN] id of existing planejamento
        PlanejamentoMensal planejamentoMensal = createPlanejamentoWithEnvelope();

        given(planejamentoMensalRepository.findById(1L))
                .willReturn(Optional.of(planejamentoMensal));
        given(usuarioHelper.getUsuarioLogado()).willReturn(getAnExistingUsuario());

        //[WHEN] delete a envelope in planejamento
        //[THEN] expected NegocioException
        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() ->  planejamentoMensalService
                        .deletarEnvelopeDoPlanejamento(1L,1L))
                .withMessageContaining("Não é possivel remover o envelope, pois ele já tem despesas");
    }

    @Test
    void findAllPlanejamentosByUsuario(){
        //[GIVEN]
        List<PlanejamentoMensal> planejamentos = Arrays.asList(createPlanejamentoWithEnvelope());
        given(planejamentoMensalRepository.findByUsuarioOrderByAnoDesc(any(), any()))
                .willReturn(new PageImpl<>(planejamentos));
        given(usuarioHelper.getUsuarioLogado()).willReturn(getAnExistingUsuario());

        //[WHEN]
        Pageable pageable = PageRequest.of(0, 10);
        Page<PlanejamentoMensal> planejamentosReturn = planejamentoMensalService.buscarPlanejamentoPorUsuario(pageable);

        //[THEN]
        assertThat(planejamentosReturn.getContent())
                .isNotNull()
                .isNotEmpty()
                .hasSize(planejamentos.size());
    }
}

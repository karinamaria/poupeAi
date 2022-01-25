package br.com.poupeAi.service;

import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.model.Despesa;
import br.com.poupeAi.model.Envelope;
import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.repository.PlanejamentoMensalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.poupeAi.mother.DespesasMother.*;
import static br.com.poupeAi.mother.EnvelopeMother.*;
import static br.com.poupeAi.mother.PlanejamentoMensalMother.createPlanejamentoWithoutEnvelopes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Despesa Service Test")
public class DespesaServiceTest {
    @InjectMocks
    private DespesaService despesaService;

    @Mock
    private PlanejamentoMensalRepository planejamentoRepository;

    @Mock
    private PlanejamentoMensalService planejamentoService;

    @Test
    void saveAValidNewDespesa() {
        PlanejamentoMensal planejamento = createPlanejamentoWithoutEnvelopes();
        Envelope envelope = createAnExistingEnvelopeWithNoDepesas();
        Despesa despesa = createAValidDespesa();

        given(planejamentoService.buscarPlanejamentoPeloId(planejamento.getId())).willReturn(planejamento);
        given(planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, envelope.getId())).willReturn(envelope);

        PlanejamentoMensal planejamentoExpected = createPlanejamentoWithoutEnvelopes();
        Envelope envelopeExpected = createAnExistingEnvelopeWithNoDepesas();
        Despesa despesaExpected = getExistingDespesa();
        envelopeExpected.getDespesas().add(despesaExpected);
        planejamentoExpected.getEnvelopes().add(envelopeExpected);
        when(planejamentoRepository.save(planejamento)).thenReturn(planejamentoExpected);

        PlanejamentoMensal planejamentoReturn = despesaService
                .salvarDespesa(planejamento.getId(), envelope.getId(), despesa);
        assertThat(planejamentoReturn.getId())
                .isNotNull()
                .isEqualTo(planejamentoExpected.getId());

        Optional<Envelope> envelopeOptionalReturn = planejamentoReturn
                .getEnvelopes()
                .stream()
                .filter(e -> e.getId().equals(envelopeExpected.getId()))
                .findFirst();
        assertThat(envelopeOptionalReturn).isPresent();

        Envelope envelopeReturn = envelopeOptionalReturn.get();
        assertThat(envelopeReturn.getId())
                .isNotNull()
                .isEqualTo(envelopeExpected.getId());

        Optional<Despesa> despesaOptionalReturn = envelopeReturn
                .getDespesas()
                .stream().filter(d -> d.getId().equals(despesaExpected.getId()))
                .findFirst();
        assertThat(despesaOptionalReturn).isPresent();

        Despesa despesaReturn = despesaOptionalReturn.get();
        assertThat(despesaReturn.getId())
                .isNotNull()
                .isEqualTo(despesaExpected.getId());
        assertThat(despesaReturn.getQuantia())
                .isNotNull()
                .isEqualTo(despesaExpected.getQuantia());
        assertThat(despesaReturn.isEhParaOutroEnvelope())
                .isNotNull()
                .isEqualTo(despesaExpected.isEhParaOutroEnvelope());
        assertThat(despesaReturn.getEnvelope())
                .isEqualTo(despesaExpected.getEnvelope());
    }

    @Test
    void trySaveAnInvalidDespesa() {
        PlanejamentoMensal planejamento = createPlanejamentoWithoutEnvelopes();
        Envelope envelope = createAnExistingEnvelope();

        given(planejamentoService.buscarPlanejamentoPeloId(planejamento.getId())).willReturn(planejamento);
        given(planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, envelope.getId())).willReturn(envelope);

        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> despesaService
                        .salvarDespesa(planejamento.getId(), envelope.getId(), createAnInvalidDespesa()))
                .withMessage("O envelope para o qual está sendo emprestada a quantia deve ser indicado.");
    }

    @Test
    void trySaveDespesaWithInsufficientBalance() {
        PlanejamentoMensal planejamento = createPlanejamentoWithoutEnvelopes();
        Envelope envelope = createAnExistingEnvelopeWithNoBalance();

        given(planejamentoService.buscarPlanejamentoPeloId(planejamento.getId())).willReturn(planejamento);
        given(planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, envelope.getId())).willReturn(envelope);

        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> despesaService
                        .salvarDespesa(planejamento.getId(), envelope.getId(), createAValidDespesa()))
                .withMessageContaining("Saldo insuficiente no envelope. Saldo: R$ ");
    }

    @Test
    void findDespesaByExistengId() {
        PlanejamentoMensal planejamento = createPlanejamentoWithoutEnvelopes();
        Envelope envelope = createAnExistingEnvelopeWithNoDepesas();
        Despesa despesa = getExistingDespesa();

        envelope.getDespesas().add(despesa);
        planejamento.getEnvelopes().add(envelope);

        given(planejamentoService.buscarPlanejamentoPeloId(planejamento.getId())).willReturn(planejamento);
        given(planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, envelope.getId())).willReturn(envelope);

        Despesa despesaReturn = despesaService
                .buscarDespesaPorId(planejamento.getId(), envelope.getId(), despesa.getId());

        assertThat(despesaReturn)
                .isNotNull()
                .isEqualTo(despesa);
    }

    @Test
    void findDespesaByIdNotAvailable() {
        PlanejamentoMensal planejamento = createPlanejamentoWithoutEnvelopes();
        Envelope envelope = createAnExistingEnvelopeWithNoDepesas();

        given(planejamentoService.buscarPlanejamentoPeloId(planejamento.getId())).willReturn(planejamento);
        given(planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, envelope.getId())).willReturn(envelope);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> despesaService
                        .buscarDespesaPorId(planejamento.getId(), envelope.getId(), getExistingDespesa().getId()))
                .withMessage("Despesa não encontrada no envelope.");
    }

    @Test
    void editAValidDespesa() {
        PlanejamentoMensal planejamento = createPlanejamentoWithoutEnvelopes();
        Envelope envelope = createAnExistingEnvelopeWithNoDepesas();
        envelope.getDespesas().add(getExistingDespesa());
        planejamento.getEnvelopes().add(envelope);
        planejamento.getEnvelopes().add(getAnOtherEnvelope());

        given(planejamentoService.buscarPlanejamentoPeloId(planejamento.getId())).willReturn(planejamento);
        given(planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, envelope.getId())).willReturn(envelope);
        given(planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, 2L))
                .willReturn(getAnOtherEnvelope());

        PlanejamentoMensal planejamentoExpected = createPlanejamentoWithoutEnvelopes();
        Envelope envelopeExpected = createAnExistingEnvelopeWithNoDepesas();
        Despesa despesaExpected = getEditedDespesa();
        envelopeExpected.getDespesas().add(despesaExpected);
        planejamentoExpected.getEnvelopes().add(envelopeExpected);
        when(planejamentoRepository.save(planejamento)).thenReturn(planejamentoExpected);

        PlanejamentoMensal planejamentoReturn = despesaService
                .atualizarDespesa(planejamento.getId(), envelope.getId(), getEditedDespesa());
        assertThat(planejamentoReturn.getId())
                .isNotNull()
                .isEqualTo(planejamentoExpected.getId());

        Optional<Envelope> envelopeOptionalReturn = planejamentoReturn
                .getEnvelopes()
                .stream()
                .filter(e -> e.getId().equals(envelopeExpected.getId()))
                .findFirst();
        assertThat(envelopeOptionalReturn).isPresent();

        Envelope envelopeReturn = envelopeOptionalReturn.get();
        assertThat(envelopeReturn.getId())
                .isNotNull()
                .isEqualTo(envelopeExpected.getId());

        Optional<Despesa> despesaOptionalReturn = envelopeReturn
                .getDespesas()
                .stream().filter(d -> d.getId().equals(despesaExpected.getId()))
                .findFirst();
        assertThat(despesaOptionalReturn).isPresent();

        Despesa despesaReturn = despesaOptionalReturn.get();
        assertThat(despesaReturn.getId())
                .isNotNull()
                .isEqualTo(despesaExpected.getId());
        assertThat(despesaReturn.getQuantia())
                .isNotNull()
                .isEqualTo(despesaExpected.getQuantia());
        assertThat(despesaReturn.isEhParaOutroEnvelope())
                .isNotNull()
                .isEqualTo(despesaExpected.isEhParaOutroEnvelope());
        assertThat(despesaReturn.getEnvelope())
                .isNotNull()
                .isEqualTo(despesaExpected.getEnvelope());
    }

    @Test
    void tryEditDespesaNotAvailable() {
        PlanejamentoMensal planejamento = createPlanejamentoWithoutEnvelopes();
        Envelope envelope = createAnExistingEnvelopeWithNoDepesas();
        planejamento.getEnvelopes().add(envelope);

        given(planejamentoService.buscarPlanejamentoPeloId(planejamento.getId())).willReturn(planejamento);
        given(planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, envelope.getId())).willReturn(envelope);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> despesaService
                        .atualizarDespesa(planejamento.getId(), envelope.getId(), getExistingDespesa()))
                .withMessage("Despesa não encontrada no envelope.");
    }

    @Test
    void tryRemoveDespesaNotAvailable() {
        PlanejamentoMensal planejamento = createPlanejamentoWithoutEnvelopes();
        Envelope envelope = createAnExistingEnvelopeWithNoDepesas();
        planejamento.getEnvelopes().add(envelope);

        given(planejamentoService.buscarPlanejamentoPeloId(planejamento.getId())).willReturn(planejamento);
        given(planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, envelope.getId())).willReturn(envelope);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> despesaService
                        .removerDespesa(planejamento.getId(), envelope.getId(), getExistingDespesa().getId()))
                .withMessage("Despesa não encontrada no envelope.");
    }
}

package br.com.poupeAi.service;

import br.com.poupeAi.exception.*;
import br.com.poupeAi.model.*;
import br.com.poupeAi.repository.PlanejamentoMensalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DespesaService {
    private final PlanejamentoMensalRepository planejamentoRespository;
    private final PlanejamentoMensalService planejamentoService;

    @Autowired
    public DespesaService(PlanejamentoMensalRepository planejamentoRepository,
                          PlanejamentoMensalService planejamentoService) {
        this.planejamentoRespository = planejamentoRepository;
        this.planejamentoService = planejamentoService;
    }

    public void validarDespesa(PlanejamentoMensal planejamento, Envelope envelope, Despesa despesa)
            throws NegocioException {
        double totalDespesasEnvelope = envelope.getDespesas()
                .stream()
                .filter(despesa1 -> !despesa1.getId().equals(despesa.getId()))
                .mapToDouble(Despesa::getQuantia)
                .sum();

        double saldoEnvelope = envelope.getOrcamento() - totalDespesasEnvelope;
        if(saldoEnvelope < despesa.getQuantia())
            throw new NegocioException("Saldo insuficiente no envelope. Saldo: R$ " + saldoEnvelope);

        if(despesa.isEhParaOutroEnvelope() && Objects.isNull(despesa.getEnvelope()))
            throw new NegocioException("O envelope para o qual está sendo emprestada a quantia deve ser indicado.");
        else if(Objects.nonNull(despesa.getEnvelope())) {
            Envelope outroEnvelope = planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, despesa.getEnvelope().getId());
            despesa.setEnvelope(outroEnvelope);
        }
    }

    public PlanejamentoMensal salvarDespesa(Long idPlanejamento, Long idEnvelope, Despesa despesa)
            throws NegocioException {
        PlanejamentoMensal planejamento = planejamentoService.buscarPlanejamentoPeloId(idPlanejamento);
        Envelope envelope = planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, idEnvelope);

        this.validarDespesa(planejamento, envelope, despesa);

        envelope.getDespesas().add(despesa);
        return planejamentoRespository.save(planejamento);
    }

    private Despesa buscarDespesaNoEnvelope(Envelope envelope, Long idDespesa)
            throws ResourceNotFoundException {
        Optional<Despesa> despesa = envelope.getDespesas().stream()
                .filter(despesa1 -> despesa1.getId().equals(idDespesa)).findFirst();
        if(!despesa.isPresent())
            throw new ResourceNotFoundException("Despesa não encontrada no envelope.");

        return despesa.get();
    }

    public Despesa buscarDespesaPorId(Long idPlanejamento, Long idEnvelope, Long idDespesa) {
        PlanejamentoMensal planejamento = planejamentoService.buscarPlanejamentoPeloId(idPlanejamento);
        Envelope envelope = planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, idEnvelope);

        return this.buscarDespesaNoEnvelope(envelope, idDespesa);
    }

    public PlanejamentoMensal atualizarDespesa(Long idPlanejamento, Long idEnvelope, Despesa despesa)
            throws NegocioException, ResourceNotFoundException {
        PlanejamentoMensal planejamento = planejamentoService.buscarPlanejamentoPeloId(idPlanejamento);
        Envelope envelope = planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, idEnvelope);
        Despesa despesaOriginal = this.buscarDespesaNoEnvelope(envelope, despesa.getId());

        this.validarDespesa(planejamento, envelope, despesa);
        envelope.getDespesas().remove(despesaOriginal);
        envelope.getDespesas().add(despesa);

        return planejamentoRespository.save(planejamento);
    }

    public void removerDespesa(Long idPlanejamento, Long idEnvelope, Long idDespesa)
            throws ResourceNotFoundException {
        PlanejamentoMensal planejamento = planejamentoService.buscarPlanejamentoPeloId(idPlanejamento);
        Envelope envelope = planejamentoService.buscarEnvelopeNoPlanejamento(planejamento, idEnvelope);
        Despesa despesa = this.buscarDespesaNoEnvelope(envelope, idDespesa);

        envelope.getDespesas().remove(despesa);
        this.planejamentoRespository.save(planejamento);
    }
}

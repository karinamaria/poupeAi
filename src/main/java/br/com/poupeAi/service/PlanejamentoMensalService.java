package br.com.poupeAi.service;

import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.helper.UsuarioHelper;
import br.com.poupeAi.model.Despesa;
import br.com.poupeAi.model.Envelope;
import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.repository.PlanejamentoMensalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PlanejamentoMensalService extends GenericService<PlanejamentoMensal, PlanejamentoMensalRepository> {
    private final UsuarioHelper usuarioHelper;

    @Autowired
    public PlanejamentoMensalService(PlanejamentoMensalRepository repository,
                                     UsuarioHelper usuarioHelper) {
        this.usuarioHelper = usuarioHelper;
        this.nomeEntidade = "Planejamento Mensal";
        this.repository = repository;
    }

    @Override
    public void validar(PlanejamentoMensal planejamentoMensal) throws NegocioException {
        Usuario usuario = usuarioHelper.getUsuarioLogado();

        PlanejamentoMensal planejamento = this.repository
                .findByUsuarioAndMesAndAno(usuario, planejamentoMensal.getMes(), planejamentoMensal.getAno());

        if(Optional.ofNullable(planejamento).isPresent()){
            throw new NegocioException("O usuário já possui um planejamento para "
                    +planejamentoMensal.getMes()+"/"+planejamentoMensal.getAno());
        }
        planejamentoMensal.setUsuario(usuario);
    }

    public PlanejamentoMensal adicionarEnvelope(Long idPlanejamento,
                                                Envelope envelope){
        PlanejamentoMensal planejamentoMensal = this.buscarPorId(idPlanejamento);

        ehPlanejamentoDeOutroUsuario(planejamentoMensal);

        boolean usuarioJaPossuiEnvelope = planejamentoMensal.getEnvelopes()
                .stream()
                .anyMatch(env -> env.getNome().equals(envelope.getNome()));
        if(usuarioJaPossuiEnvelope){
            throw new NegocioException("Usuário já possui o envelope");
        }

        planejamentoMensal.getEnvelopes().add(envelope);

        return this.repository.save(planejamentoMensal);
    }

    public PlanejamentoMensal atualizarEnvelope(Long idPlanejamento,
                                                Envelope envelope) {
        PlanejamentoMensal planejamentoMensal = this.buscarPorId(idPlanejamento);

        ehPlanejamentoDeOutroUsuario(planejamentoMensal);

        Envelope envelopeBase = buscarEnvelopeNoPlanejamento(planejamentoMensal, envelope.getId());
        verificarDespesas(envelopeBase, envelope);
        envelope.setId(envelopeBase.getId());

        if(!planejamentoMensal.getEnvelopes().add(envelope)) {
            planejamentoMensal.getEnvelopes().remove(envelopeBase);
            planejamentoMensal.getEnvelopes().add(envelope);
        }
       return this.repository.save(planejamentoMensal);
    }

    public PlanejamentoMensal buscarPlanejamentoPeloId(Long idPlanejamento){
        PlanejamentoMensal planejamentoMensal = this.buscarPorId(idPlanejamento);

        ehPlanejamentoDeOutroUsuario(planejamentoMensal);

        return planejamentoMensal;
    }

    public void deletarEnvelopeDoPlanejamento(Long idPlanejamento,
                                             Long idEnvelope){
        PlanejamentoMensal planejamentoMensal = this.buscarPorId(idPlanejamento);
        ehPlanejamentoDeOutroUsuario(planejamentoMensal);

        Envelope envelope = buscarEnvelopeNoPlanejamento(planejamentoMensal, idEnvelope);

        if(!envelope.getDespesas().isEmpty()){
            throw new NegocioException("Não é possivel remover o envelope, pois ele já tem despesas");
        }

        planejamentoMensal.getEnvelopes().remove(envelope);

        this.repository.save(planejamentoMensal);
    }

    public Page<PlanejamentoMensal> buscarPorUsuario(Pageable pageable){
        return this.repository.findByUsuarioOrderByAnoDesc(pageable, usuarioHelper.getUsuarioLogado());
    }

    private Envelope buscarEnvelopeNoPlanejamento(PlanejamentoMensal planejamentoMensal,
                                                  Long idEnvelope){
        Optional<Envelope> envelopeBase = planejamentoMensal.getEnvelopes()
                .stream()
                .filter(envelope1 -> envelope1.getId().equals(idEnvelope))
                .findFirst();
        if(!envelopeBase.isPresent()){
            throw new NegocioException("Envelope não encontrado no planejamento id: "+planejamentoMensal.getId());
        }
        return envelopeBase.get();
    }

    private void verificarDespesas(Envelope envelopeBase, Envelope envelopeAtualizado){
        double valorDespesasEnvelopeBase = envelopeBase.getDespesas()
                .stream()
                .mapToDouble(Despesa::getQuantia)
                .sum();
        if(envelopeAtualizado.getOrcamento() < valorDespesasEnvelopeBase){
            throw new NegocioException("O envelope já possui um total de despesas R$ "+valorDespesasEnvelopeBase);
        }
    }

    private void ehPlanejamentoDeOutroUsuario(PlanejamentoMensal planejamentoMensal){
        if(!planejamentoMensal.getUsuario().equals(usuarioHelper.getUsuarioLogado())){
            throw new NegocioException("Não é possível cadastrar o envelope");
        }
    }

}

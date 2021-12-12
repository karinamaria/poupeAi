package br.com.poupeAi.service;

import br.com.poupeAi.helper.UsuarioHelper;
import br.com.poupeAi.model.Envelope;
import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.repository.PlanejamentoMensalRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if(!planejamentoMensal.getUsuario().equals(usuarioHelper.getUsuarioLogado())){
            throw new NegocioException("Não é possível cadastrar o envelope");
        }

        boolean usuarioJaPossuiEnvelope = planejamentoMensal.getEnvelopes()
                .stream()
                .anyMatch(env -> env.getNome().equals(envelope.getNome()));
        if(usuarioJaPossuiEnvelope){
            throw new NegocioException("Usuário já possui o envelope");
        }
        planejamentoMensal.getEnvelopes().add(envelope);

        return this.repository.save(planejamentoMensal);
    }

    public List<PlanejamentoMensal> buscarPorUsuario(){
        return this.repository.findByUsuario(usuarioHelper.getUsuarioLogado());
    }
}

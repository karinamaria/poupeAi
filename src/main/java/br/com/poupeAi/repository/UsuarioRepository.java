package br.com.poupeAi.repository;

import br.com.poupeAi.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario> {
    @Query(value = "SELECT u from Usuario u where u.email = ?1 and u.ativo=true")
    Usuario findByEmail(String email);

    @Query(value = "SELECT sum(e.orcamento) from PlanejamentoMensal p JOIN p.usuario u JOIN p.envelopes e" +
            " WHERE u.id = ?1" +
            " AND p.ativo = true" +
            " AND e.nome = ?2" +
            " AND ((p.mes <= ?3 AND p.ano = ?4) OR p.ano < ?4)")
    double orcamentoAcumuladoPorEnvelope(Long idUsuario, String nomeEnvelope, int mesReferencia, int anoReferencia);

    @Query(value = "SELECT sum(d.quantia) from PlanejamentoMensal p JOIN p.usuario u JOIN p.envelopes e JOIN e.despesas d" +
            " WHERE u.id = ?1" +
            " AND p.ativo = true" +
            " AND e.nome = ?2" +
            " AND ((p.mes <= ?3 AND p.ano = ?4) OR p.ano < ?4)")
    double despesasAcumuladasPorEnvelope(Long idUsuario, String nomeEnvelope, int mesReferencia, int anoReferencia);
}

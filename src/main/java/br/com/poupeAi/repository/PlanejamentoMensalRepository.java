package br.com.poupeAi.repository;

import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanejamentoMensalRepository extends GenericRepository<PlanejamentoMensal> {
    PlanejamentoMensal findByUsuarioAndMesAndAno(Usuario usuario, int mes, int ano);
    List<PlanejamentoMensal> findByUsuario(Usuario usuario);
}

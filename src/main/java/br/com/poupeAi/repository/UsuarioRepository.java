package br.com.poupeAi.repository;

import br.com.poupeAi.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario> {
    Usuario findByEmail(String email);
}

package br.com.poupeAi.repository;

import br.com.poupeAi.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario> {
    @Query(value = "SELECT u from Usuario u where u.email = ?1 and u.ativo=true")
    Usuario findByEmail(String email);
}

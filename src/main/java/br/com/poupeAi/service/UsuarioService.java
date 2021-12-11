package br.com.poupeAi.service;

import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsuarioService extends GenericService<Usuario, UsuarioRepository>  {
    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.nomeEntidade = "Usuário";
        this.repository = repository;
    }

    @Override
    public void validar(Usuario usuario) throws NegocioException {
        Usuario usuarioEncontrado = this.buscarPorEmail(usuario);
        if(Objects.nonNull(usuarioEncontrado) && !usuarioEncontrado.getId().equals(usuario.getId()))
            throw new NegocioException("Este endereço de e-mail já está em uso.");
    }

    public Usuario buscarPorEmail(Usuario usuario) {
        return this.repository.findByEmail(usuario.getEmail());
    }
}

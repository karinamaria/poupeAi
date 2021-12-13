package br.com.poupeAi.service;

import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.helper.UsuarioHelper;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsuarioService extends GenericService<Usuario, UsuarioRepository>  {
    private final UsuarioHelper usuarioHelper;

    @Autowired
    public UsuarioService(UsuarioRepository repository, UsuarioHelper usuarioHelper) {
        this.nomeEntidade = "Usuário";
        this.repository = repository;
        this.usuarioHelper = usuarioHelper;
    }

    @Override
    public void validar(Usuario usuario) throws NegocioException {
        if(Objects.nonNull(usuario.getId()) && !usuarioHelper.getUsuarioLogado().getId().equals(usuario.getId()))
            throw  new NegocioException("Você não tem permissão para editar esse usuário.");

        Usuario usuarioEncontrado = this.buscarPorEmail(usuario);
        if(Objects.nonNull(usuarioEncontrado) && !usuarioEncontrado.getId().equals(usuario.getId()))
            throw new NegocioException("Este endereço de e-mail já está em uso.");
    }

    @Override
    public void validarNaRemocao(Usuario usuario) throws NegocioException, ResourceNotFoundException {
        if(Objects.nonNull(usuario.getId()) && !usuarioHelper.getUsuarioLogado().getId().equals(usuario.getId()))
            throw  new NegocioException("Você não tem permissão para remover esse usuário.");
    }

    @Override
    public Usuario salvar(Usuario usuario) throws NegocioException {
        if(Objects.isNull(usuario.getId()) || !this.buscarPorId(usuario.getId()).getSenha().equals(usuario.getSenha()))
            // Criptografar a senha do usuário
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));

        return super.salvar(usuario);
    }

    public Usuario buscarPorEmail(Usuario usuario) {
        return this.repository.findByEmail(usuario.getEmail());
    }
}

package br.com.poupeAi.helper;

import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class UsuarioHelper {
    private Usuario usuarioLogado;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioHelper(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public Usuario getUsuarioLogado() {
        if (getLoginUsuario() != null) {
            if (usuarioLogado != null && !usuarioLogado.getEmail().equals(getLoginUsuario())) {
                usuarioLogado = null;
            }
            if (usuarioLogado == null) {
                usuarioLogado = usuarioRepository.findByEmail(getLoginUsuario());
            }
        }
        return usuarioLogado;
    }

    public String getLoginUsuario() {
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;
    }
}

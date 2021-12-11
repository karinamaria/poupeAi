package br.com.poupeAi.controller;


import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.service.UsuarioService;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiModel(value = "Usuário Controller")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario criarUsuario(@Valid @RequestBody Usuario usuario) throws NegocioException {
        System.out.println("Post: " + usuario.getEmail());
        return this.usuarioService.salvar(usuario);
    }

    @GetMapping("/{id}")
    public Usuario buscarUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        return this.usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@Valid @RequestBody Usuario usuario) throws NegocioException, ResourceNotFoundException {
        return this.usuarioService.atualizar(usuario);
    }

    @DeleteMapping("/{id}")
    public void removerUsuarioPorId(@PathVariable Long id) throws NegocioException, ResourceNotFoundException {
       this.usuarioService.remover(id);
    }
}

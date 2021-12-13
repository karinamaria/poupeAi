package br.com.poupeAi.controller;


import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Schema(name="Usuário Controller")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario criarUsuario(@Valid @RequestBody Usuario usuario) throws NegocioException {
        return this.usuarioService.salvar(usuario);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar um usuário cadastrado")
    public Usuario buscarUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        return this.usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario atualizarUsuario(@Valid @RequestBody Usuario usuario) throws NegocioException, ResourceNotFoundException {
        return this.usuarioService.atualizar(usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerUsuarioPorId(@PathVariable Long id) throws NegocioException, ResourceNotFoundException {
       this.usuarioService.remover(id);
    }
}

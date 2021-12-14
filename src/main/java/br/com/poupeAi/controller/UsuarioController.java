package br.com.poupeAi.controller;


import br.com.poupeAi.dto.UsuarioInputDto;
import br.com.poupeAi.dto.UsuarioOutputDto;
import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.mapper.UsuarioMapper;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Schema(name="Usuário Controller")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar um novo usuário")
    public UsuarioOutputDto criarUsuario(@Valid @RequestBody UsuarioInputDto usuarioInputDto) throws NegocioException {
        Usuario usuario = usuarioMapper.usuarioInputDtoToUsuario(usuarioInputDto);
        return usuarioMapper.usuarioToUsuarioOutputDto(usuarioService.salvar(usuario));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar um usuário cadastrado")
    public UsuarioOutputDto buscarUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        return usuarioMapper.usuarioToUsuarioOutputDto(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar um usuário cadastrado")
    public UsuarioOutputDto atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioInputDto usuarioInputDto)
            throws NegocioException, ResourceNotFoundException {
        Usuario usuario = usuarioMapper.usuarioInputDtoToUsuario(usuarioInputDto);
        usuario.setId(id);
        return usuarioMapper.usuarioToUsuarioOutputDto(usuarioService.atualizar(usuario));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover um usuário cadastrado")
    public void removerUsuarioPorId(@PathVariable Long id) throws NegocioException, ResourceNotFoundException {
       this.usuarioService.remover(id);
    }
}

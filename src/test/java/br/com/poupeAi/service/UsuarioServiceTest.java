package br.com.poupeAi.service;

import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.helper.UsuarioHelper;
import br.com.poupeAi.model.Usuario;
import br.com.poupeAi.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.poupeAi.mother.UsuarioMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Usuário Service Test")
public class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    UsuarioHelper usuarioHelper;

    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    void findUsuarioByExistingEmail() {
        Usuario usuario = getAnExistingUsuario();
        given(usuarioRepository.findByEmail(usuario.getEmail())).willReturn(usuario);

        Usuario usuarioReturn = usuarioService.buscarPorEmail(usuario);

        assertThat(usuarioReturn)
                .isNotNull()
                .isEqualTo(usuario);
    }

    @Test
    void saveAValidNewUsuario() {
        Usuario usuario = createAValidUsuario();
        given(usuarioRepository.findByEmail(usuario.getEmail())).willReturn(any(Usuario.class));

        Usuario usuarioExpected = getAnExistingUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuarioExpected);

        Usuario usuarioReturn = usuarioService.salvar(usuario);

        assertThat(usuarioReturn.getId())
                .isNotNull()
                .isEqualTo(usuarioExpected.getId());
        assertThat(usuarioReturn.getNome())
                .isNotNull()
                .isEqualTo(usuarioExpected.getNome());
        assertThat(usuarioReturn.getEmail())
                .isNotNull()
                .isEqualTo(usuarioExpected.getEmail());
        assertThat(usuarioReturn.getSenha())
                .isNotNull()
                .isEqualTo(usuarioExpected.getSenha());
    }

    @Test
    void trySaveNewUsuarioWhenEmailIsAlreadyBeingUsed() {
        Usuario usuario = createAValidUsuario();
        Usuario usuarioUsingEmail = getAnExistingUsuario();

        given(usuarioRepository.findByEmail(usuario.getEmail())).willReturn(usuarioUsingEmail);

        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> usuarioService.salvar(usuario))
                .withMessage("Este endereço de e-mail já está em uso.");
    }

    @Test
    void findUsuarioByExistingId() {
        Usuario usuario = getAnExistingUsuario();
        given(usuarioRepository.findById(usuario.getId())).willReturn(Optional.of(usuario));

        Usuario usuarioReturn = usuarioService.buscarPorId(usuario.getId());

        assertThat(usuarioReturn)
                .isNotNull()
                .isEqualTo(usuario);
    }

    @Test
    void findUsuarioByIdNotAvailable() {
        Long id = 1L;
        given(usuarioRepository.findById(id))
                .willReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> usuarioService.buscarPorId(id))
                .withMessage("Usuário não encontrado(a).");
    }

    @Test
    void editAValidUsuario() {
        Usuario usuarioEdited = getAnEditedUsuario();
        Usuario usuarioUnedited = getAnExistingUsuario();

        given(usuarioRepository.findById(usuarioEdited.getId())).willReturn(Optional.of(usuarioUnedited));
        given(usuarioHelper.getUsuarioLogado()).willReturn(usuarioUnedited);
        given(usuarioRepository.findByEmail(usuarioEdited.getEmail())).willReturn(usuarioUnedited);

        when(usuarioRepository.save(usuarioEdited)).thenReturn(usuarioEdited);
        Usuario usuarioReturn = usuarioService.atualizar(usuarioEdited);

        assertThat(usuarioReturn.getId())
                .isNotNull()
                .isEqualTo(usuarioEdited.getId());
        assertThat(usuarioReturn.getNome())
                .isNotNull()
                .isEqualTo(usuarioEdited.getNome());
        assertThat(usuarioReturn.getEmail())
                .isNotNull()
                .isEqualTo(usuarioEdited.getEmail());
        assertThat(usuarioReturn.getSenha())
                .isNotNull()
                .isEqualTo(usuarioEdited.getSenha());
    }

    @Test
    void tryEditUsuarioWhenEmailIsAlreadyBeingUsed() {
        Usuario usuarioEdited = getAnEditedUsuario();
        Usuario usuarioUnedited = getAnExistingUsuario();

        given(usuarioRepository.findById(usuarioEdited.getId())).willReturn(Optional.of(usuarioUnedited));
        given(usuarioHelper.getUsuarioLogado()).willReturn(usuarioUnedited);
        given(usuarioRepository.findByEmail(usuarioEdited.getEmail())).willReturn(getAnOtherExistingUsuario());

        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> usuarioService.atualizar(usuarioEdited))
                .withMessage("Este endereço de e-mail já está em uso.");
    }

    @Test
    void tryEditOtherUsuario() {
        Usuario usuario = getAnExistingUsuario();

        given(usuarioRepository.findById(usuario.getId())).willReturn(Optional.of(usuario));
        given(usuarioHelper.getUsuarioLogado()).willReturn(getAnOtherExistingUsuario());

        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> usuarioService.atualizar(usuario))
                .withMessage("Você não tem permissão para editar esse usuário.");
    }

    @Test
    void tryRemoveOtherUsuario() {
        Usuario usuario = getAnExistingUsuario();

        given(usuarioRepository.findById(usuario.getId())).willReturn(Optional.of(usuario));
        given(usuarioHelper.getUsuarioLogado()).willReturn(getAnOtherExistingUsuario());

        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> usuarioService.remover(usuario.getId()))
                .withMessage("Você não tem permissão para remover esse usuário.");
    }
}

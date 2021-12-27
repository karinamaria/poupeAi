package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.UsuarioInputDto;
import br.com.poupeAi.dto.UsuarioOutputDto;
import br.com.poupeAi.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.poupeAi.mother.UsuarioMother.getAnExistingUsuario;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Usuário Mapper Test")
public class UsuarioMapperTest {

    @Test
    void usuarioInputDtoToUsuario(){
        UsuarioInputDto usuarioInputDto = UsuarioInputDto
                .builder()
                .nome("Hailey")
                .email("hailey@email.com")
                .senha("hailey1234")
                .build();
        Usuario usuarioReturn = UsuarioMapper.INSTANCE.usuarioInputDtoToUsuario(usuarioInputDto);

        assertThat(usuarioReturn).isNotNull();
        assertThat(usuarioReturn.getNome())
                .isNotNull()
                .isEqualTo(usuarioInputDto.getNome());
        assertThat(usuarioReturn.getEmail())
                .isNotNull()
                .isEqualTo(usuarioInputDto.getEmail());
        assertThat(usuarioReturn.getSenha())
                .isNotNull()
                .isEqualTo(usuarioInputDto.getSenha());
    }

    @Test
    void usuarioToUsuarioOutputDto(){
        Usuario usuario = getAnExistingUsuario();

        UsuarioOutputDto usuarioOutputDto = UsuarioMapper.INSTANCE.usuarioToUsuarioOutputDto(usuario);

        assertThat(usuarioOutputDto).isNotNull();
        assertThat(usuarioOutputDto.getId())
                .isNotNull()
                .isEqualTo(usuario.getId());
        assertThat(usuarioOutputDto.getNome())
                .isNotNull()
                .isEqualTo(usuario.getNome());
        assertThat(usuarioOutputDto.getEmail())
                .isNotNull()
                .isEqualTo(usuario.getEmail());
    }
}

package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.UsuarioInputDto;
import br.com.poupeAi.dto.UsuarioOutputDto;
import br.com.poupeAi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario usuarioInputDtoToUsuario(UsuarioInputDto usuarioInputDto);
    UsuarioOutputDto usuarioToUsuarioOutputDto(Usuario usuario);
}

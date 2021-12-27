package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.UsuarioInputDto;
import br.com.poupeAi.dto.UsuarioOutputDto;
import br.com.poupeAi.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper( UsuarioMapper.class);

    Usuario usuarioInputDtoToUsuario(UsuarioInputDto usuarioInputDto);
    UsuarioOutputDto usuarioToUsuarioOutputDto(Usuario usuario);
}

package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="UsuarioOutputDto")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class UsuarioOutputDto {
    private Long id;
    private String nome;
    private String email;
}

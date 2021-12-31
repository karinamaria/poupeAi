package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Schema(name="UsuarioInputDto")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class UsuarioInputDto {
    @NotNull
    private String nome;
    @NotNull
    @Email(message = "E-mail inválido.")
    private String email;
    @NotNull
    @Length(min = 6, message = "A senha deve possuir no mínimo 6 caracteres.")
    private String senha;
}

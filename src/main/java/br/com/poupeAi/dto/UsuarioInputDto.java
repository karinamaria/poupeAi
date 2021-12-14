package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Schema(name="UsuarioInputDto")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
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

package br.com.poupeAi.mother;

import br.com.poupeAi.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * UsuarioMother é uma classe object mother
 * usada para ajudar a criar objetos de exemplo usados
 * nos testes
 */
public class UsuarioMother {
    public static Usuario createAValidUsuario(){
        return Usuario.builder()
                .nome("Beatriz")
                .email("beatriz@email.com")
                .senha("senha/beatriz")
                .build();
    }

    public static Usuario getAnExistingUsuario(){
        return Usuario.builder()
                .id(1L)
                .nome("Beatriz")
                .email("beatriz@email.com")
                .senha(new BCryptPasswordEncoder().encode("senha/beatriz"))
                .build();
    }

    public static Usuario getAnEditedUsuario(){
        return Usuario.builder()
                .id(1L)
                .nome("Beatriz Teste")
                .email("beatriz.teste@email.com")
                .senha(new BCryptPasswordEncoder().encode("senha/beatriz.teste"))
                .build();
    }

    public static Usuario getAnOtherExistingUsuario(){
        return Usuario.builder()
                .id(2L)
                .nome("Eva")
                .email("eva@email.com")
                .senha(new BCryptPasswordEncoder().encode("senha/eva"))
                .build();
    }
}

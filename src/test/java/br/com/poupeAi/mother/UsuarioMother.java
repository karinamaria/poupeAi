package br.com.poupeAi.mother;

import br.com.poupeAi.model.Usuario;

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
                .build();
    }

    public static Usuario getAnExistingUsuario(){
        return Usuario.builder()
                .id(1L)
                .nome("Beatriz")
                .senha("beatriz@email.com")
                .email("beatriz@email.com")
                .build();
    }

    public static Usuario getAnOtherExistingUsuario(){
        return Usuario.builder()
                .id(2L)
                .nome("Eva")
                .senha("eva@email.com")
                .build();
    }
}

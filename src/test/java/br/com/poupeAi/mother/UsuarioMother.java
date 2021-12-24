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
}

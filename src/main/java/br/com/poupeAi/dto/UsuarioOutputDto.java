package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="UsuarioOutputDto")
public class UsuarioOutputDto {
    private Long id;
    private String nome;
    private String email;
}

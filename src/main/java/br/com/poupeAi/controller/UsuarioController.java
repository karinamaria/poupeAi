package br.com.poupeAi.controller;


import io.swagger.annotations.ApiModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiModel(value = "Usuário Controller")
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
}

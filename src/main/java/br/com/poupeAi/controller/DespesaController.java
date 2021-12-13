package br.com.poupeAi.controller;

import br.com.poupeAi.model.Despesa;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Schema(name="Despesa Controller")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/despesas")
public class DespesaController {

    @PostMapping
    public Despesa addDespesa(){
        return new Despesa();
    }

    @PatchMapping
    public Despesa atualizarDespesa(){
        return new Despesa();
    }
}

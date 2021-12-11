package br.com.poupeAi.controller;

import br.com.poupeAi.model.PlanejamentoMensal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Schema(name="Planejamento Mensal Controller")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/planejamentos")
public class PlanejamentoMensalController {

    @PostMapping
    public PlanejamentoMensal criarPlanejamento(){
        return new PlanejamentoMensal();
    }

    @DeleteMapping("/{id}")
    public void excluirPlanejamento(){

    }

    @GetMapping("/{id}")
    public void buscarPlanejamento(){

    }

    @PostMapping("/{id}/adicionar-envelope")
    public PlanejamentoMensal addEnvelope(){
        return new PlanejamentoMensal();
    }

    @PatchMapping("/{id}/atualizar-envelope/{idEnvelope}")
    public PlanejamentoMensal atualizarEnvelope(){
        return new PlanejamentoMensal();
    }

    @DeleteMapping("/{idPlanejamento}/remover-envelope/{idEnvelope}")
    public PlanejamentoMensal removerEnvelope(){
        return new PlanejamentoMensal();
    }
}

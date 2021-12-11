package br.com.poupeAi.controller;

import br.com.poupeAi.dto.PlanejamentoMensalInputDto;
import br.com.poupeAi.mapper.PlanejamentoMensalMapper;
import br.com.poupeAi.model.PlanejamentoMensal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Schema(name="Planejamento Mensal Controller")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/planejamentos")
public class PlanejamentoMensalController {

    private final PlanejamentoMensalMapper planejamentoMensalMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanejamentoMensal criarPlanejamento(@Valid @RequestBody PlanejamentoMensalInputDto planejamentoMensalInputDto){
        PlanejamentoMensal planejamentoMensal = planejamentoMensalMapper.planejamentoMensalDtoToPlanejamento(planejamentoMensalInputDto);
        return new PlanejamentoMensal();
    }

    @DeleteMapping("/{id}")
    public void excluirPlanejamento(){

    }

    @GetMapping("/{id}")
    public void buscarPlanejamento(){

    }

    @PostMapping("/{idPlanejamento}/envelopes")
    public PlanejamentoMensal addEnvelope(@PathVariable Long idPlanejamento){
        return new PlanejamentoMensal();
    }

    @PatchMapping("/{idPlanejamento}/envelopes/{idEnvelope}")
    public PlanejamentoMensal atualizarEnvelope(@PathVariable Long idPlanejamento,
                                                @PathVariable Long idEnvelope){
        return new PlanejamentoMensal();
    }

    @DeleteMapping("/{idPlanejamento}/envelopes/{idEnvelope}")
    public PlanejamentoMensal removerEnvelope(@PathVariable Long idPlanejamento,
                                              @PathVariable Long idEnvelope){
        return new PlanejamentoMensal();
    }
}

package br.com.poupeAi.controller;

import br.com.poupeAi.dto.DespesaInputDto;
import br.com.poupeAi.dto.DespesaOutputDto;
import br.com.poupeAi.dto.PlanejamentoMensalOutputDto;
import br.com.poupeAi.exception.*;
import br.com.poupeAi.mapper.DespesaMapper;
import br.com.poupeAi.mapper.PlanejamentoMensalMapper;
import br.com.poupeAi.model.Despesa;
import br.com.poupeAi.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Schema(name = "Despesa Controller")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/planejamentos/{idPlanejamento}/envelopes/{idEnvelope}/despesas")
public class DespesaController {
    private final DespesaService despesaService;
    private final DespesaMapper despesaMapper;
    private final PlanejamentoMensalMapper planejamentoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar despesa ao envelope")
    public PlanejamentoMensalOutputDto addDespesa(@PathVariable Long idPlanejamento, @PathVariable Long idEnvelope,
                                                  @Valid @RequestBody DespesaInputDto despesaInputDto) throws NegocioException {
        return planejamentoMapper.planejamentoToPlanejamentoOutput(
                despesaService.salvarDespesa(idPlanejamento, idEnvelope,
                        despesaMapper.despesaInputDtoToDespesa(despesaInputDto))
        );
    }

    @GetMapping("/{idDespesa}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar despesa pelo ID")
    public DespesaOutputDto buscarDespesa(@PathVariable Long idPlanejamento, @PathVariable Long idEnvelope,
                                          @PathVariable Long idDespesa) throws ResourceNotFoundException {
        return despesaMapper.despesaToDespesaOutputDto(
                despesaService.buscarDespesaPorId(idPlanejamento, idEnvelope, idDespesa)
        );
    }

    @PatchMapping("/{idDespesa}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar despesa do envelope")
    public PlanejamentoMensalOutputDto atualizarDespesa(@PathVariable Long idPlanejamento, @PathVariable Long idEnvelope,
                                                        @PathVariable Long idDespesa, @Valid @RequestBody DespesaInputDto despesaInputDto)
            throws NegocioException, ResourceNotFoundException {
        Despesa despesa = despesaMapper.despesaInputDtoToDespesa(despesaInputDto);
        despesa.setId(idDespesa);

        return planejamentoMapper.planejamentoToPlanejamentoOutput(
                despesaService.atualizarDespesa(idPlanejamento, idEnvelope, despesa)
        );
    }

    @DeleteMapping("/{idDespesa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover envelope do planejamento")
    public void removerDespesa(@PathVariable Long idPlanejamento, @PathVariable Long idEnvelope,
                               @PathVariable Long idDespesa) throws ResourceNotFoundException {
        despesaService.removerDespesa(idPlanejamento, idEnvelope, idDespesa);
    }
}

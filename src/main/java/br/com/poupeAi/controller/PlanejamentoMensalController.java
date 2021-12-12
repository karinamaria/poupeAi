package br.com.poupeAi.controller;

import br.com.poupeAi.dto.EnvelopeInputDto;
import br.com.poupeAi.dto.EnvelopeInputUpdateDto;
import br.com.poupeAi.dto.PlanejamentoMensalInputDto;
import br.com.poupeAi.dto.PlanejamentoMensalOutputDto;
import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.mapper.EnvelopeMapper;
import br.com.poupeAi.mapper.PlanejamentoMensalMapper;
import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.service.PlanejamentoMensalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Schema(name="Planejamento Mensal Controller")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/planejamentos")
public class PlanejamentoMensalController {

    private final PlanejamentoMensalService planejamentoService;
    private final PlanejamentoMensalMapper mapper;
    private final EnvelopeMapper envelopeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar um novo planejamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode="201", description = "Planejamento criado")
    })
    public PlanejamentoMensalOutputDto criarPlanejamento(
            @Valid @RequestBody PlanejamentoMensalInputDto planejamentoMensalInputDto) throws NegocioException {
        PlanejamentoMensal planejamentoMensal = mapper.planejamentoMensalDtoToPlanejamento(planejamentoMensalInputDto);
        return mapper.planejamentoToPlanejamentoOutput(planejamentoService.salvar(planejamentoMensal));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar todos os planejamentos do usuário logado")
    public Set<PlanejamentoMensalOutputDto> listarPlanejamentos(){
        return mapper.convertListPlanejamento(planejamentoService.buscarPorUsuario());
    }


    @GetMapping("/{idPlanejamento}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar planejamento pelo ID")
    public PlanejamentoMensalOutputDto buscarPlanejamento(@PathVariable Long idPlanejamento) throws ResourceNotFoundException {
        return mapper.planejamentoToPlanejamentoOutput(planejamentoService.buscarPorId(idPlanejamento));
    }

    @PostMapping("/{idPlanejamento}/envelopes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar envelope ao planejamento")
    public PlanejamentoMensalOutputDto addEnvelope(@PathVariable Long idPlanejamento,
                                                   @Valid @RequestBody EnvelopeInputDto envelope){

        return mapper.planejamentoToPlanejamentoOutput(
                planejamentoService.adicionarEnvelope(idPlanejamento, envelopeMapper.envelopeInputToEnvelope(envelope)));
    }

    @PatchMapping("/{idPlanejamento}/envelopes")
    @Operation(summary = "Atualizar envelope do planejamento")
    public PlanejamentoMensalOutputDto atualizarEnvelope(@PathVariable Long idPlanejamento,
                                                         @Valid @RequestBody EnvelopeInputUpdateDto envelope){
        return mapper.planejamentoToPlanejamentoOutput(
                planejamentoService.atualizarEnvelope(idPlanejamento, envelopeMapper.envelopeUpdateToEnvelope(envelope))
        );
    }

    @DeleteMapping("/{idPlanejamento}/envelopes")
    @Operation(summary = "Remover envelope do planejamento")
    public PlanejamentoMensalOutputDto removerEnvelope(@PathVariable Long idPlanejamento,
                                              @Valid @RequestBody EnvelopeInputDto envelope){
        return new PlanejamentoMensalOutputDto();
    }
}

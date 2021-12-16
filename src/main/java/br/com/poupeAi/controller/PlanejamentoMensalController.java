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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public PlanejamentoMensalOutputDto criarPlanejamento(
            @Valid @RequestBody PlanejamentoMensalInputDto planejamentoMensalInputDto) throws NegocioException {
        PlanejamentoMensal planejamentoMensal = mapper.planejamentoMensalDtoToPlanejamento(planejamentoMensalInputDto);
        return mapper.planejamentoToPlanejamentoOutput(planejamentoService.salvar(planejamentoMensal));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar todos os planejamentos do usuário logado")
    public Page<PlanejamentoMensalOutputDto> listarPlanejamentos(@Parameter(hidden = true) @PageableDefault Pageable pageable){
        Page<PlanejamentoMensal> planejamentoMensal = planejamentoService.buscarPorUsuario(pageable);

        return planejamentoMensal.map(planejamentoMensal1 -> mapper.planejamentoToPlanejamentoOutput(planejamentoMensal1));
    }


    @GetMapping("/{idPlanejamento}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar planejamento pelo ID")
    public PlanejamentoMensalOutputDto buscarPlanejamento(@PathVariable Long idPlanejamento) throws ResourceNotFoundException {
        return mapper.planejamentoToPlanejamentoOutput(planejamentoService.buscarPlanejamentoPeloId(idPlanejamento));
    }

    @PostMapping("/{idPlanejamento}/envelopes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar envelope ao planejamento")
    public PlanejamentoMensalOutputDto addEnvelope(@PathVariable Long idPlanejamento,
                                                   @Valid @RequestBody EnvelopeInputDto envelope){

        return mapper.planejamentoToPlanejamentoOutput(
                planejamentoService.adicionarEnvelope(idPlanejamento, envelopeMapper.envelopeInputToEnvelope(envelope)));
    }

    @PatchMapping("/{idPlanejamento}/envelopes/{idEnvelope}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar envelope do planejamento")
    public PlanejamentoMensalOutputDto atualizarEnvelope(@PathVariable Long idPlanejamento,
                                                         @PathVariable Long idEnvelope,
                                                         @RequestParam(name = "orcamento") double orcamento){
        return mapper.planejamentoToPlanejamentoOutput(
                planejamentoService.atualizarEnvelope(idPlanejamento, idEnvelope, orcamento)
        );
    }

    @DeleteMapping("/{idPlanejamento}/envelopes/{idEnvelope}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover envelope do planejamento")
    public void removerEnvelope(@PathVariable Long idPlanejamento,
                                @PathVariable Long idEnvelope){
        planejamentoService.deletarEnvelopeDoPlanejamento(idPlanejamento, idEnvelope);
    }
}

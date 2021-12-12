package br.com.poupeAi.controller;

import br.com.poupeAi.dto.EnvelopeInputDto;
import br.com.poupeAi.dto.PlanejamentoMensalInputDto;
import br.com.poupeAi.dto.PlanejamentoMensalOutputDto;
import br.com.poupeAi.mapper.EnvelopeMapper;
import br.com.poupeAi.mapper.PlanejamentoMensalMapper;
import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.service.PlanejamentoMensalService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @DeleteMapping("/{id}")
    public void excluirPlanejamento(){

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlanejamentoMensalOutputDto> listarPlanejamentos(){
        return mapper.convertList(planejamentoService.buscarPorUsuario());
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlanejamentoMensalOutputDto buscarPlanejamento(@PathVariable Long idPlanejamento) throws ResourceNotFoundException{
        return mapper.planejamentoToPlanejamentoOutput(planejamentoService.buscarPorId(idPlanejamento));
    }

    @PostMapping("/{idPlanejamento}/envelopes")
    public PlanejamentoMensalOutputDto addEnvelope(@PathVariable Long idPlanejamento,
                                          @Valid @ResponseBody EnvelopeInputDto envelope){

        return mapper.planejamentoToPlanejamentoOutput(
                planejamentoService.adicionarEnvelope(idPlanejamento, envelopeMapper.envelopeInputToEnvelope(envelope)));
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

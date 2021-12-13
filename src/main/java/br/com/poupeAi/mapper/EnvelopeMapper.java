package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.EnvelopeInputDto;
import br.com.poupeAi.dto.EnvelopeInputUpdateDto;
import br.com.poupeAi.model.Envelope;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnvelopeMapper {
    Envelope envelopeInputToEnvelope(EnvelopeInputDto envelopeInputDto);
    Envelope envelopeUpdateToEnvelope(EnvelopeInputUpdateDto envelopeInputUpdateDto);
}

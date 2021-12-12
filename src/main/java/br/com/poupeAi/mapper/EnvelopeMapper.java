package br.com.poupeAi.mapper;

import br.com.poupeAi.dto.EnvelopeInputDto;
import br.com.poupeAi.model.Envelope;

@Mapper(componentModel = "spring")
public interface EnvelopeMapper {
    Envelope envelopeInputToEnvelope(EnvelopeInputDto envelopeInputDto);
}

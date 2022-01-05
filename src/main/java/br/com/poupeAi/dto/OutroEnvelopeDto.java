package br.com.poupeAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Schema(name = "OutroEnvelopeDto")
@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode
public class OutroEnvelopeDto {
    @NotNull
    @Positive
    private Long id;
}

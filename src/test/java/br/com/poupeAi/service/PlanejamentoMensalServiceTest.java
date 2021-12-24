package br.com.poupeAi.service;

import br.com.poupeAi.helper.UsuarioHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlanejamentoServiceTest {
    @InjectMocks
    private PlanejamentoMensalService planejamentoMensalService;

    @Mock
    private UsuarioHelper usuarioHelper;

    void saveAValidNewPlanejamento(){
        //[GIVEN] a valid planejamento

        //[WHEN] create a planejamento

        //[THEN] return entity planejamento
    }

}

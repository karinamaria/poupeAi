package br.com.poupeAi.service;

import br.com.poupeAi.config.EmailConfig;
import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.repository.PlanejamentoMensalRepository;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

@Service
@EnableScheduling
public class EmailService {
    private static final String TIME_ZONE = "America/Sao_Paulo";
    private final EmailConfig config;
    private final PlanejamentoMensalRepository planejamentoRepository;
    private final RelatorioService relatorioService;

    @Autowired
    public EmailService(EmailConfig config, PlanejamentoMensalRepository planejamentoRespository,
                              RelatorioService relatorioService) {
        this.config = config;
        this.planejamentoRepository = planejamentoRespository;
        this.relatorioService = relatorioService;
    }

    private void enviarEmailComRelatorio(PlanejamentoMensal planejamento, ByteArrayOutputStream dadosRelatorio) {
        String diaEMesPlanejamento = planejamento.getMes() + "/" + planejamento.getAno();
        String nomeDestinatario = planejamento.getUsuario().getNome();

        try {
            // Cria e configura email
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName(config.getHostname());
            email.setSmtpPort(config.getPort());
            email.setAuthenticator(new DefaultAuthenticator(config.getUsername(), config.getPassword()));
            email.setStartTLSEnabled(config.isTlsEnable());
            email.setStartTLSRequired(config.isTlsRequired());

            // Define informações gerais do email
            email.setFrom(config.getUsername(), "PoupeAí");
            email.addTo(planejamento.getUsuario().getEmail(), nomeDestinatario);
            email.setSubject("Relatório - Planejamento Mensal " + diaEMesPlanejamento);
            email.setMsg("Olá, " + nomeDestinatario + "!"
                    + "\n\n\tSegue em anexo o relatório do planejamento mensal de " + diaEMesPlanejamento + "."
                    + "\n\nPoupeAí."
                    + "\n\n\nNota: Este e-mail foi gerado automaticamente. Por favor não responda esta mensagem.");

            // Define nome do arquivo do relatório
            String nomeAnexo = "planejamento_mensal-" + diaEMesPlanejamento + "_" + nomeDestinatario;

            // Adiciona anexo ao email
            DataSource relatorio = new ByteArrayDataSource(dadosRelatorio.toByteArray(), "application/pdf");
            email.attach(relatorio, nomeAnexo, "", EmailAttachment.ATTACHMENT);

            // Envia email
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método executado no primeiro dia de todos os meses, responsável por enviar o relatório do planejamento mensal
     * do mês anterior aos usuários.
     */
    @Scheduled(cron = "0 0 12 1 * *", zone = TIME_ZONE)
//    @Scheduled(cron = "* * * * * *", zone = TIME_ZONE) // a cada minuto para teste
    public void enviarRelatorios() {
        Calendar calendario = Calendar.getInstance();

        int mesAnterior = calendario.get(Calendar.MONTH);
        int anoDoMesAnterior = calendario.get(Calendar.YEAR);
        if(mesAnterior == 0){
            mesAnterior = 12;
            anoDoMesAnterior--;
        }

        List<PlanejamentoMensal> planejamentoDoMes = planejamentoRepository
                .findAllByMesAndAnoAndAtivoTrue(mesAnterior, anoDoMesAnterior);

        for(PlanejamentoMensal planejamento : planejamentoDoMes) {
            enviarEmailComRelatorio(planejamento, relatorioService.gerarRelatorioDoPlanejamentoMensal(planejamento));
        }
    }
}

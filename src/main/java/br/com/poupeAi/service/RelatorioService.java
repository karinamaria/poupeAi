package br.com.poupeAi.service;

import br.com.poupeAi.model.Despesa;
import br.com.poupeAi.model.Envelope;
import br.com.poupeAi.model.EnvelopeDefaultEnum;
import br.com.poupeAi.model.PlanejamentoMensal;
import br.com.poupeAi.repository.UsuarioRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

@Service
public class RelatorioService {
    private static final NumberFormat FORMATO_QUANTIA = NumberFormat.getCurrencyInstance();
    private static final Font FONTE_TITULO = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
    private static final Font FONTE_CABECALHO_TABELA = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RelatorioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private void extrairDadosDoPlanejamento(PlanejamentoMensal planejamentoMensal, Map<String, Double> saldo,
                                            Map<String, Double> totalDespesas, Map<String, Double> totalEmprestado,
                                            Map<String, Double> totalRecebidoEmprestado,
                                            Map<String, Double> totalPlanejamento) {
        double orcamentoDoPlanejamento = 0;
        double saldoDoPlanejamento = 0;
        for (Envelope envelope : planejamentoMensal.getEnvelopes()) {
            String nomeEnvelopeAtual = envelope.getNome();
            double totalDespesasDoEnvelope = 0;
            double totalEmprestadoPeloEnvelope = 0;

            for (Despesa despesaDoEnvelope : envelope.getDespesas()) {
                // Incrementa a soma de despesas do envelope
                totalDespesasDoEnvelope += despesaDoEnvelope.getQuantia();

                // Verifica se a despesa é referente a outro envelope
                if(despesaDoEnvelope.isEhParaOutroEnvelope()) {
                    // Se for, incrementa o total do que foi emprestado a outros envelopes
                    totalEmprestadoPeloEnvelope += despesaDoEnvelope.getQuantia();

                    // E, incrementa o total recebido emprestado do envelope indicado na despesa
                    String nomeOutroEnvelope = despesaDoEnvelope.getEnvelope().getNome();
                    if(!totalRecebidoEmprestado.containsKey(nomeOutroEnvelope)) {
                        totalRecebidoEmprestado.put(nomeOutroEnvelope, despesaDoEnvelope.getQuantia());
                    }
                    else {
                        double somaEmprestimosOutroEnvelope = totalRecebidoEmprestado.get(nomeOutroEnvelope)
                                + despesaDoEnvelope.getQuantia();
                        totalRecebidoEmprestado.replace(nomeOutroEnvelope, somaEmprestimosOutroEnvelope);
                    }
                }
            }

            // Define saldo do envelope
            double saldoDoEnvelope = envelope.getOrcamento() - totalDespesasDoEnvelope;

            saldo.put(nomeEnvelopeAtual, saldoDoEnvelope);
            totalDespesas.put(nomeEnvelopeAtual, totalDespesasDoEnvelope);
            totalEmprestado.put(nomeEnvelopeAtual, totalEmprestadoPeloEnvelope);

            // Incrementa orçamento e saldo do planejamento de acordo com informações do envelope
            orcamentoDoPlanejamento += envelope.getOrcamento();
            saldoDoPlanejamento += saldoDoEnvelope;
        }
        totalPlanejamento.put("orcamento", orcamentoDoPlanejamento);
        totalPlanejamento.put("saldo", saldoDoPlanejamento);
    }

    private double calcularSaldoAcumuladoEnvelope(PlanejamentoMensal planejamento, String nomeEnvelope) {
        Long idUsuario = planejamento.getUsuario().getId();
        double orcamentoAcumuladoReserva = usuarioRepository.orcamentoAcumuladoPorEnvelope(idUsuario, nomeEnvelope,
                planejamento.getMes(), planejamento.getAno());
        double despesasAcumuladasReserva = usuarioRepository.despesasAcumuladasPorEnvelope(idUsuario, nomeEnvelope,
                planejamento.getMes(), planejamento.getAno());

        return orcamentoAcumuladoReserva - despesasAcumuladasReserva;
    }

    private PdfPTable criarTabelaSaldoEnvelopes(PlanejamentoMensal planejamento, Map<String, Double> totalDespesas,
                                                Map<String, Double> saldo) {
        PdfPTable tabela = new PdfPTable(4);

        // Cabeçalho da tabela
        tabela.addCell(new Paragraph("Envelope", FONTE_CABECALHO_TABELA));
        tabela.addCell(new Paragraph("Orçamento", FONTE_CABECALHO_TABELA));
        tabela.addCell(new Paragraph("Total gasto", FONTE_CABECALHO_TABELA));
        tabela.addCell(new Paragraph("Saldo", FONTE_CABECALHO_TABELA));

        // Popular tabela
        for (Envelope envelope : planejamento.getEnvelopes()) {
            String nomeEnvelope = envelope.getNome();

            tabela.addCell(nomeEnvelope);
            tabela.addCell(FORMATO_QUANTIA.format(envelope.getOrcamento()));
            tabela.addCell(FORMATO_QUANTIA.format(totalDespesas.get(nomeEnvelope)));
            tabela.addCell(FORMATO_QUANTIA.format(saldo.get(nomeEnvelope)));
        }
        return tabela;
    }

    private PdfPTable criarTabelaEmprestadoEnvelopes(PlanejamentoMensal planejamento,
                                                     Map<String, Double> totalEmprestado) {
        PdfPTable tabela = new PdfPTable(2);

        // Cabeçalho da tabela
        tabela.addCell(new Paragraph("Envelope", FONTE_CABECALHO_TABELA));
        tabela.addCell(new Paragraph("Total emprestado", FONTE_CABECALHO_TABELA));

        // Popular tabela
        for (Envelope envelope : planejamento.getEnvelopes()) {
            String nomeEnvelope = envelope.getNome();

            if(totalEmprestado.get(nomeEnvelope) > 0) {
                tabela.addCell(nomeEnvelope);
                tabela.addCell(FORMATO_QUANTIA.format(totalEmprestado.get(nomeEnvelope)));
            }
        }
        return tabela;
    }

    private PdfPTable criarTabelaEmprestimoEnvelopes(PlanejamentoMensal planejamento,
                                                      Map<String, Double> totalRecebidoEmprestado) {
        PdfPTable tabela = new PdfPTable(2);

        // Cabeçalho da tabela
        tabela.addCell(new Paragraph("Envelope", FONTE_CABECALHO_TABELA));
        tabela.addCell(new Paragraph("Total recebido emprestado", FONTE_CABECALHO_TABELA));

        // Popular tabela
        for (Envelope envelope : planejamento.getEnvelopes()) {
            String nomeEnvelope = envelope.getNome();

            if(totalRecebidoEmprestado.containsKey(nomeEnvelope)) {
                tabela.addCell(nomeEnvelope);
                tabela.addCell(FORMATO_QUANTIA.format(totalRecebidoEmprestado.get(nomeEnvelope)));
            }
        }

        return tabela;
    }

    public ByteArrayOutputStream gerarRelatorioDoPlanejamentoMensal(PlanejamentoMensal planejamento) {
        Map<String, Double> saldo = new HashMap<>();
        Map<String, Double> totalDespesas = new HashMap<>();
        Map<String, Double> totalEmprestado = new HashMap<>();
        Map<String, Double> totalRecebidoEmprestado = new HashMap<>();
        Map<String, Double> totalPlanejamento = new HashMap<>();

        extrairDadosDoPlanejamento(planejamento, saldo, totalDespesas, totalEmprestado, totalRecebidoEmprestado,
                totalPlanejamento);

        // Cria documento do relatório
        Document relatorio = new Document();
        ByteArrayOutputStream relatorioOutput = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(relatorio, relatorioOutput);
            relatorio.open();

            // Define título do relatório
            Paragraph tituloRelatorio = new Paragraph("Relatório \n Planejamento mensal - " + planejamento.getMes()
                    + "/" + planejamento.getAno(), FONTE_TITULO);
            tituloRelatorio.setAlignment(Element.ALIGN_CENTER);
            relatorio.add(tituloRelatorio);
            relatorio.add(new Paragraph("\n"));

            // Adiciona informações gerais do planejamento
            relatorio.add(new Paragraph("Orçamento: " + FORMATO_QUANTIA.format(totalPlanejamento.get("orcamento"))));
            relatorio.add(new Paragraph("Saldo: " + FORMATO_QUANTIA.format(totalPlanejamento.get("saldo"))));
            relatorio.add(new Paragraph("\n"));

            // Adiciona tabela com informações dos saldos dos envelopes ao relatório
            PdfPTable tabelaSaldo = criarTabelaSaldoEnvelopes(planejamento, totalDespesas, saldo);
            relatorio.add(tabelaSaldo);
            relatorio.add(new Paragraph("\n"));

            // Verifica se algum envelope recebeu dinheiro emprestado de outro
            if(!totalRecebidoEmprestado.isEmpty()) {
                // Se sim, adiciona tabelas com informações sobre os empréstimos entre os envelopes ao relatório
                PdfPTable tabelaEmprestado = criarTabelaEmprestadoEnvelopes(planejamento, totalEmprestado);
                relatorio.add(tabelaEmprestado);
                relatorio.add(new Paragraph("\n"));
                PdfPTable tabelaEmprestimos = criarTabelaEmprestimoEnvelopes(planejamento, totalRecebidoEmprestado);
                relatorio.add(tabelaEmprestimos);
            }
            else {
                relatorio.add(new Paragraph("*Nenhum empréstimo entre envelopes foi registrado."));
            }

            // Adiciona separador
            relatorio.add(new Paragraph("\n"));
            relatorio.add(new LineSeparator());
            relatorio.add(new Paragraph("\n"));

            // Calcula saldo acumulado nos envelopes de Reserva de Emergência e Investimentos
            double saldoAcumuladoReserva = calcularSaldoAcumuladoEnvelope(planejamento,
                    EnvelopeDefaultEnum.RESERVA_EMERGENCIA.getDescricao());
            double saldoAcumuladoInvestimentos = calcularSaldoAcumuladoEnvelope(planejamento,
                    EnvelopeDefaultEnum.INVESTIMENTOS.getDescricao());

            // Adiciona acumulado nos envelopes de Reserva de Emergência e Investimentos
            relatorio.add(new Paragraph("Saldo acumulado nos envelopes de reserva de emergência: "
                    + FORMATO_QUANTIA.format(saldoAcumuladoReserva)));
            relatorio.add(new Paragraph("Saldo acumulado nos envelopes de investimentos: "
                    + FORMATO_QUANTIA.format(saldoAcumuladoInvestimentos)));
            relatorio.add(new Paragraph("\n"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        relatorio.close();

        return relatorioOutput;
    }
}

package br.com.poupeAi.model;

public enum EnvelopeDefaultEnum {
    CARRO("Carro"), CASA("Casa"), SAUDE("Saúde"),
    COMPRAS("Compras"),SUPERMERCADO("Supermercado"),LAZER("Lazer"),
    EDUCAÇÃO("Educação"), DIVIDAS("Dividas"), RESERVA_EMERGENCIA("Reserva de Emergência"),
    INVESTIMENTOS("Investimentos");

    private String descricao;

    EnvelopeDefaultEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

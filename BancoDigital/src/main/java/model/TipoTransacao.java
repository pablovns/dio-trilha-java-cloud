package model;

import lombok.Getter;

@Getter
public enum TipoTransacao {

    DEPOSITO("Depósito", "D", 0.0, false),
    SAQUE("Saque", "S", 0.0, false),
    TRANSFERENCIA("Transferência", "T", 0.01, true),
    PIX("Pix", "P", 0.0, true)
    ;

    private final String descricao;
    private final String codigo;
    private final double taxa; // porcentagem da taxa (de 0 a 1)
    private final boolean permiteAgendamento;

    TipoTransacao(String descricao, String codigo, double taxa, boolean permiteAgendamento) {
        this.descricao = descricao;
        this.codigo = codigo;
        this.taxa = taxa;
        this.permiteAgendamento = permiteAgendamento;
    }

}

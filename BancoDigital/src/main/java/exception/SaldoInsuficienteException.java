package exception;

import lombok.Getter;
import model.TipoTransacao;

@Getter
public class SaldoInsuficienteException extends Exception {
    private final TipoTransacao tipoTransacao;
    private final double saldoAtual;
    private final double valorFaltando;

    public SaldoInsuficienteException(TipoTransacao tipoTransacao, double saldoAtual, double valorFaltando) {
        super("Tentativa de %s de R$ %.2f com saldo de R$ %.2f".formatted(tipoTransacao.getDescricao(), valorFaltando, saldoAtual));
        this.tipoTransacao = tipoTransacao;
        this.saldoAtual = saldoAtual;
        this.valorFaltando = valorFaltando;
    }

    @Override
    public String toString() {
        return "SaldoInsuficienteException: saldo atual = R$ " + saldoAtual +
                ", valor faltando = R$ " + valorFaltando;
    }
}

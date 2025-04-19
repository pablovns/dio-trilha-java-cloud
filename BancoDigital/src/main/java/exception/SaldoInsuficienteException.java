package exception;

import lombok.Getter;

@Getter
public class SaldoInsuficienteException extends Exception {
    private final double saldoAtual;
    private final double valorFaltando;

    public SaldoInsuficienteException(double saldoAtual, double valorFaltando) {
        super("Tentativa de saque de R$ " + valorFaltando + " com saldo de R$ " + saldoAtual);
        this.saldoAtual = saldoAtual;
        this.valorFaltando = valorFaltando;
    }

    @Override
    public String toString() {
        return "SaldoInsuficienteException: saldo atual = R$ " + saldoAtual +
                ", valor faltando = R$ " + valorFaltando;
    }
}

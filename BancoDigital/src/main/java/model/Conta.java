package model;

import exception.SaldoInsuficienteException;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public abstract class Conta implements IConta {

	private static final int AGENCIA_PADRAO = 1;
	private static int contador = 1;

	protected int id;
	protected int agencia;
	protected Cliente cliente;
	protected double saldo;
	protected List<Transacao> transacoes;

	protected Conta(Cliente cliente) {
		this.agencia = Conta.AGENCIA_PADRAO;
		this.id = contador++;
		this.cliente = cliente;
	}

	@Override
	public void depositar(double valor) {
		saldo += valor;
	}

	@Override
	public void sacar(double valor) throws SaldoInsuficienteException {
		if (valor > saldo) {
			throw new SaldoInsuficienteException(saldo, valor - saldo);
		}
		saldo -= valor;
	}

	@Override
	public void transferir(double valor, IConta contaDestino) throws SaldoInsuficienteException {
		sacar(valor);
		contaDestino.depositar(valor);
	}

	public void imprimirInformacoes() {
		System.out.printf("Id: %d%n", id);
		System.out.printf("Titular: %s%n", cliente.getNome());
		System.out.printf("Agencia: %d%n", agencia);
		System.out.printf("Saldo: %.2f%n", saldo);
	}

	@Override
	public void imprimirExtrato() {
		Comparator<Transacao> porData = Comparator.comparing(Transacao::getData).reversed();
		List<Transacao> transacoesOrdenadas = transacoes.stream().sorted(porData).toList();

		for (Transacao transacao : transacoesOrdenadas) {
			System.out.println(transacao);
		}
	}
}

package model;

import exception.SaldoInsuficienteException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class Conta implements IConta {

	private static final int AGENCIA_PADRAO = 1;
	private static int contador = 1;

	private final int id;
	private final Cliente cliente;
	private int agencia;
	private double saldo;
	private List<Transacao> transacoes;

	protected Conta(Cliente cliente) {
		this.id = contador++;
		this.cliente = cliente;
		this.agencia = AGENCIA_PADRAO;
		this.saldo = 0;
		this.transacoes = new ArrayList<>();
	}

	@Override
	public void depositar(double valor) {
		saldo += valor;
		adicionarTransacao(new Transacao(LocalDateTime.now(), TipoTransacao.DEPOSITO, valor));
	}

	@Override
	public void sacar(double valor) throws SaldoInsuficienteException {
		if (valor > saldo) {
			throw new SaldoInsuficienteException(saldo, valor - saldo);
		}
		saldo -= valor;
		adicionarTransacao(new Transacao(LocalDateTime.now(), TipoTransacao.SAQUE, valor));
	}

	@Override
	public void transferir(double valor, IConta contaDestino) throws SaldoInsuficienteException {
		sacar(valor);
		contaDestino.depositar(valor);
		adicionarTransacao(new Transacao(LocalDateTime.now(), TipoTransacao.TRANSFERENCIA, valor));
	}

	private void adicionarTransacao(Transacao transacao) {
		transacoes.add(transacao);
	}

	public void imprimirInformacoes() {
		System.out.printf("Id: %d%n", id);
		System.out.printf("Titular: %s%n", cliente.getNome());
		System.out.printf("Agência: %d%n", agencia);
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

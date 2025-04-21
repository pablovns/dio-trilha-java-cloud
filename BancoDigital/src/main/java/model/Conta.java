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
	public static final String O_VALOR_DEVE_SER_MAIOR_QUE_ZERO = "O valor deve ser maior que zero.";
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
		if (valor <= 0) {
			throw new IllegalArgumentException(O_VALOR_DEVE_SER_MAIOR_QUE_ZERO);
		}
		final TipoTransacao tipoTransacao = TipoTransacao.DEPOSITO;
		saldo += valor;
		adicionarTransacao(new Transacao(LocalDateTime.now(), tipoTransacao, valor, tipoTransacao.getTaxa()));
	}

	@Override
	public void sacar(double valor) throws SaldoInsuficienteException {
		if (valor <= 0) {
			throw new IllegalArgumentException(O_VALOR_DEVE_SER_MAIOR_QUE_ZERO);
		}
		final TipoTransacao tipoTransacao = TipoTransacao.SAQUE;
		if (valor > saldo) {
			throw new SaldoInsuficienteException(TipoTransacao.SAQUE, saldo, valor - saldo);
		}
		saldo -= valor;
		adicionarTransacao(new Transacao(LocalDateTime.now(), tipoTransacao, valor, tipoTransacao.getTaxa()));
	}

	@Override
	public void transferir(double valor, IConta contaDestino) throws SaldoInsuficienteException {
		if (valor <= 0) {
			throw new IllegalArgumentException(O_VALOR_DEVE_SER_MAIOR_QUE_ZERO);
		}

		final TipoTransacao tipoTransacao = TipoTransacao.TRANSFERENCIA;
		final double valorTaxa = valor * tipoTransacao.getTaxa();
		final double valorTotal = valor + valorTaxa;

		if (valorTotal > saldo) {
			System.out.println("Não foi possível realizar a transferência. Saldo insuficiente.");
			throw new SaldoInsuficienteException(TipoTransacao.TRANSFERENCIA, saldo, valorTotal - saldo);
		}

		saldo -= valorTotal;
		Conta contaDestinoCast = (Conta) contaDestino;
		contaDestinoCast.setSaldo(valor);

		adicionarTransacao(new Transacao(LocalDateTime.now(), tipoTransacao, valor, tipoTransacao.getTaxa()));
	}

	public void adicionarTransacao(Transacao transacao) {
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

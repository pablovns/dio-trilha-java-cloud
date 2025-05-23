package model;

import exception.SaldoInsuficienteException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContaCorrente extends Conta {

	private boolean possuiChequeEspecial;
	private double limiteChequeEspecial;

	public ContaCorrente(Cliente cliente, boolean possuiChequeEspecial, double limiteChequeEspecial) {
		super(cliente);
		this.possuiChequeEspecial = possuiChequeEspecial;
		this.limiteChequeEspecial = limiteChequeEspecial;
	}

	public ContaCorrente(Cliente cliente, boolean possuiChequeEspecial) {
		super(cliente);
		this.possuiChequeEspecial = possuiChequeEspecial;
		this.limiteChequeEspecial = 500;
	}

	public ContaCorrente(Cliente cliente) {
		super(cliente);
		this.possuiChequeEspecial = false;
		this.limiteChequeEspecial = 0.0;
	}

	@Override
	public void sacar(double valor) throws SaldoInsuficienteException {
		final TipoTransacao tipoTransacao = TipoTransacao.SAQUE;

		validaSaldoSuficiente(valor);
		setSaldo(getSaldo() - valor);
		adicionarTransacao(new Transacao(LocalDateTime.now(), tipoTransacao, valor, tipoTransacao.getTaxa()));
	}

	private void validaSaldoSuficiente(double valor) throws SaldoInsuficienteException {
		if (valor <= 0) {
			throw new IllegalArgumentException(O_VALOR_DEVE_SER_MAIOR_QUE_ZERO);
		}

		final TipoTransacao tipoTransacao = TipoTransacao.SAQUE;
		double saldoDisponivel = possuiChequeEspecial ? getSaldo() + limiteChequeEspecial : getSaldo();
		if (valor > saldoDisponivel) {
			throw new SaldoInsuficienteException(tipoTransacao, getSaldo(), valor - (getSaldo() + limiteChequeEspecial));
		}
	}

	@Override
	public void imprimirInformacoes() {
		System.out.println("=== Informações - Conta Corrente ===");
		super.imprimirInformacoes();
	}

	@Override
	public void imprimirExtrato() {
		System.out.println("=== Extrato - Conta Corrente ===");
		super.imprimirExtrato();
	}
	
}

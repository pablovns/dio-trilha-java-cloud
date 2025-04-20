package model;

import exception.SaldoInsuficienteException;
import lombok.Getter;
import lombok.Setter;

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

	@Override
	public void sacar(double valor) throws SaldoInsuficienteException {
		if (valor > getSaldo() + limiteChequeEspecial) {
			throw new SaldoInsuficienteException(getSaldo(), valor - (getSaldo() + limiteChequeEspecial));
		} else {
			super.sacar(valor);
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

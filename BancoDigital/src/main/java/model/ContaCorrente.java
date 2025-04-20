package model;

import exception.SaldoInsuficienteException;

public class ContaCorrente extends Conta {

	private boolean possuiChequeEspecial;
	private double limiteChequeEspecial;

	public ContaCorrente(Cliente cliente) {
		super(cliente);
	}

	@Override
	public void depositar(double valor) {

	}

	@Override
	public void sacar(double valor) throws SaldoInsuficienteException {
		if (valor > saldo + limiteChequeEspecial) {
			throw new SaldoInsuficienteException(saldo, valor - (saldo + limiteChequeEspecial));
		}
	}

	@Override
	public void imprimirInformacoes() {
		System.out.println("=== Informações - Conta Corrente ===");
		super.imprimirInformacoes();
	}
	
}

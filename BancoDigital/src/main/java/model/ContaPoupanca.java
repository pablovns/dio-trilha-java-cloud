package model;

import exception.SaldoInsuficienteException;

public class ContaPoupanca extends Conta {

	public ContaPoupanca(Cliente cliente) {
		super(cliente);
	}

	@Override
	public void sacar(double valor) throws SaldoInsuficienteException {

	}

	@Override
	public void depositar(double valor) {

	}

	@Override
	public void imprimirInformacoes() {
		System.out.println("=== Informações - Conta Poupança ===");
		super.imprimirInformacoes();
	}

	@Override
	public void imprimirExtrato() {
		System.out.println("=== Extrato - Conta Poupança ===");
		super.imprimirExtrato();
	}
}

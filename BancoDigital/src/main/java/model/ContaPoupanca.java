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
	public void imprimirExtrato() {
		System.out.println("=== Extrato model.Conta Poupança ===");
		super.imprimirInformacoes();
	}
}

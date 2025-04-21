package model;

import exception.SaldoInsuficienteException;

public interface IConta {

	void depositar(double valor);

	void sacar(double valor) throws SaldoInsuficienteException;

	void transferir(double valor, IConta contaDestino) throws SaldoInsuficienteException;
	
	void imprimirExtrato();
}

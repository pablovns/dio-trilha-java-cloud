package model;

import exception.SaldoInsuficienteException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ContaCorrenteTest {

    public static final int LIMITE_CHEQUE_ESPECIAL = 500;
    private static final double SALDO_INICIAL = 100;
    private ContaCorrente conta;
    private ContaCorrente contaComChequeEspecial;

    @BeforeEach
    void setUp() {
        System.out.println("Preparando o teste...");
        conta = new ContaCorrente(new Cliente("João"));
        contaComChequeEspecial = new ContaCorrente(new Cliente("Maria"), true, LIMITE_CHEQUE_ESPECIAL);

        conta.depositar(SALDO_INICIAL);
        contaComChequeEspecial.depositar(SALDO_INICIAL);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Limpando após o teste...");
        conta.setSaldo(0);
        contaComChequeEspecial.setSaldo(0);
    }

    @Test
    void testSaque() {
        assertDoesNotThrow(() -> conta.sacar(40));
        assertEquals(60, conta.getSaldo());
        assertThrows(SaldoInsuficienteException.class, () -> conta.sacar(100));
    }

    @Test
    void testSaqueChequeEspecial() {
        assertDoesNotThrow(() -> contaComChequeEspecial.sacar(SALDO_INICIAL + LIMITE_CHEQUE_ESPECIAL));
        assertEquals(-LIMITE_CHEQUE_ESPECIAL, contaComChequeEspecial.getSaldo());
    }

    @Test
    void testSaqueMaiorQueSaldoLancaExcecao() {
        assertThrows(SaldoInsuficienteException.class, () -> conta.sacar(SALDO_INICIAL + 1));
    }

    @Test
    void testSaqueMaiorQueSaldoLancaExcecaoComChequeEspecial() {
        assertThrows(SaldoInsuficienteException.class, () -> contaComChequeEspecial.sacar(SALDO_INICIAL + LIMITE_CHEQUE_ESPECIAL + 1));
        assertEquals(SALDO_INICIAL, contaComChequeEspecial.getSaldo());
    }

    @Test
    void testTransferenciaMaiorQueSaldoLancaExcecao() {
        ContaCorrente destino = new ContaCorrente(new Cliente("Destino"));

        final double valorTransferencia = 100;
        // Captura a saída padrão
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            assertThrows(SaldoInsuficienteException.class, () -> conta.transferir(valorTransferencia, destino));

            String saida = outputStream.toString();
            assertTrue(saida.contains("Não foi possível realizar a transferência. Saldo insuficiente."));

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testDeposito() {
        final int VALOR_TRANSFERENCIA = 150;
        assertDoesNotThrow(() -> conta.depositar(VALOR_TRANSFERENCIA));
        assertEquals(SALDO_INICIAL + VALOR_TRANSFERENCIA, conta.getSaldo());
    }

    @Test
    void testTransferencia() {
        final double valorTransferencia = 50;
        final double valorTransferenciaComTaxa = valorTransferencia + valorTransferencia * TipoTransacao.TRANSFERENCIA.getTaxa();

        ContaCorrente contaDestino = new ContaCorrente(new Cliente("Maria"));

        double saldoInicial = conta.getSaldo();
        assertDoesNotThrow(() -> conta.transferir(valorTransferencia, contaDestino));

        assertEquals(saldoInicial - valorTransferenciaComTaxa, conta.getSaldo());
        assertEquals(valorTransferencia, contaDestino.getSaldo());
    }

    @Test
    void testImprimirExtrato() {
        assertDoesNotThrow(() -> conta.imprimirExtrato());
    }
}

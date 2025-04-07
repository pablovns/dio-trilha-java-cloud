import java.util.Scanner;

public class ContaTerminal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Por favor, digite o número da conta:");
        int conta = Integer.parseInt(sc.nextLine());

        System.out.println("Por favor, digite o número da agência:");
        String agencia = sc.nextLine();

        System.out.println("Por favor, digite o seu nome:");
        String nomeCliente = sc.nextLine();

        System.out.println("Por favor, digite o saldo da conta:");
        double saldo = Double.parseDouble(sc.nextLine());

        System.out.printf("\nOlá %s, obrigado por criar uma conta em nosso banco, sua agência é %s, conta %d e seu saldo %.2f já está disponível para saque%n", nomeCliente, agencia, conta, saldo );
    }
}
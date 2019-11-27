package com.br.diego.banco;

import static java.lang.System.out;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.NumberFormat;
import java.util.Scanner;

//ClientBank
public class Agencia {

	static Scanner leia = new Scanner(System.in, "latin1");

  //numero da agência
	private final int numero;
	private static int conta;
	private static String nomeTitular = null;

	private static Registry registry;
  //--------------------------------------GETTERs & SETTERs-------------------------------------------------

  public Agencia(int numero)
  {s
    this.numero = numero;
  }

  public int getNumero()
  {
    return numero;
  }

  //------------------------------------------FUNCAO MAIN---------------------------------------------------
	public static void main(String[] args) {

    //cria um objeto de agenciadd
    Agencia agencia = new Agencia(2345);
    RMIClient terminal = new RMIClient(agencia, "127.0.0.1", 2001);

    //seleciona um servidor para que a agência possa se comunicar diretamente
    //em primeira instancia
    terminal.setup("S1");



    //inicializa a exibição das opções iniciais
	  opInicial();

	}

  //--------------------------------------------FUNCOES EXTRAS-----------------------------------------------


	private static void novaConta() {
		do {
			out.println("\n\n\n     CADASTRAMENTO DE NOVA CONTA");
			out.println("\nAgência atual: " + numero);
			out.print("\nEntre com o nº da Agência de destino da conta ou 0 (zero) para sair: ");

			int ag = leiaInt(99999999);
			if (ag == 0)
				return;

			out.print("\nEntre com o nº da Conta ou 0 (zero) para sair: ");
			int n = leiaInt(99999999);
			if (n == 0)
				return;

			try {
				if (terminal.reqExisteConta(n)) {
					out.print("\nCONTA JÁ EXISTE! Tente novamente:");
					continue;
				}
			} catch (RemoteException e1) {
				out.print("\"COMPORTAMENTO INESPERADO:\nFalha no servidor!\"");
				return;
			}
			/// CADASTRO DOS DADOS DO CLIENTE
			out.print("\nEntre com o nome titular da conta ou 0 (zero) para sair: ");
			String titular = leiaNome();
			if (titular.equals("0"))
				return;

			out.print("\nEntre com o CPF do titular da conta ou 0 (zero) para sair: ");
			String cpf = leiaNome();
			if (cpf.equals("0"))
				return;

			out.print("\nEntre com o EMAIL do titular da conta ou 0 (zero) para sair: ");
			String email = leiaNome();
			if (email.equals("0"))
				return;

			out.print("\nEntre com o SENHA da conta ou 0 (zero) para sair: ");
			String senha = leiaNome();
			if (senha.equals("0"))
				return;

			out.print("\nENTRE COM O VALOR INCIAL DE ABERTURA DA CONTA ou 0 (zero) para sair: ");
			double valor = leiaDouble();
			if (valor == 0)
				return;

			try {
				if (terminal.reqInserirCliente(n, ag, valor, cpf, titular, email, senha)) {
					out.println("\n\n\n**********CONTA CRIADA COM SUCESSO!**********");
					out.println("\nPressione a tecla ENTER para continuar...\n");
					leia.nextLine();
					return;
				}
			} catch (RemoteException e) {
				out.println("COMPORTAMENTO INESPERADO:\nFalha no servidor!");
				return;
			}
			out.println("COMPORTAMENTO INESPERADO\nCONTA NÃO CRIADA!\nOCORREU UM PROBLEMA AO TENTAR CRIAR NOVA CONTA!");
		} while (true);
	}

	

	

	private static void transferencia() {
		// FALTA IMPLEMENTAR

	}

	private static void deposito() {
		// FALTA IMPLEMENTAR

	}

	private static void saque() {
		String processamento = null;
		
		out.println("\n\n\n\n     SAQUE EM CONTA");
		out.println("\nAgência atual: " + numero);
		out.print("\nENTRE COM O VALOR A SACAR ou 0 (zero) para sair: ");
		double valor = leiaDouble();
		if (valor == 0)
		return;
		out.print("\nEntre com o SENHA da conta ou 0 (zero) para sair: ");
		String senha = leiaNome();
		if (senha.equals("0"))
			return;

		try {
			processamento = banco.saque(conta, numero, new BigDecimal(valor), senha);
		} catch (RemoteException e) {
			out.println("\n\n>>COMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
			leia.nextLine();
			return;
		}

		if (processamento.charAt(0) == 'F') {//saque não pôde ser realizado
			out.println("\n\n\n\n___________________________________\n");
			out.println("\n**********" + processamento + "**********");
			out.println("\nPressione a tecla ENTER para continuar...\n");
			leia.nextLine();
			return;
		}
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		out.println("\n**********" + processamento + "**********\n");
		out.println("\n\nImprimindo comprovante...");
		out.println("\n___________________________________\n");
		out.println("\n\nAgencia atual: " + numero + "  Conta: " + conta + "\nTitular: " + nomeTitular);
		out.println("\n\n\n\n    SAQUE: " + nf.format(valor));
		out.println("\n\n\n\n___________________________________\n");
		out.println("\n**********OPERAÇÃO REALIZADA COM SUCESSO!**********");
		out.println("\nPressione a tecla ENTER para continuar...\n");
		leia.nextLine();

	}

	private static void extrato() {
		// FALTA IMPLEMENTAR

	}

	/// IMPRIME O SALDO DA CONTA ATUAL, cujo número está em conta
	private static void saldo() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		out.println("\n\nImprimindo comprovante...");
		out.println("\n___________________________________\n");
		try {
			out.println("\n\nAgencia atual: " + numero + "  Conta: " + conta + "\nTitular: " + nomeTitular);
			out.println("\n\n\n\n    Saldo atual: " + nf.format(banco.saldo(conta).doubleValue()));
		} catch (RemoteException e) {
			out.println(
					"\n\n>>COMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
			leia.nextLine();
			return;

		}
		out.println("\n\n\n\n___________________________________\n");
		out.println("\n**********OPERAÇÃO REALIZADA COM SUCESSO!**********");
		out.println("\nPressione a tecla ENTER para continuar...\n");
		leia.nextLine();

	}

	private static void acessaConta() {
		while (true) {
			out.println("\n    ACESSAR CONTA");
			out.println("\nAgência atual: " + numero);

			out.print("\nEntre com o nº da Conta ou 0 (zero) para sair: ");
			int id_conta = leiaInt(999999999);
			if (id_conta == 0)
				return;

			out.print("\nEntre com senha dessa conta ou 0 (zero) para sair: ");
			String senha = leia.nextLine();
			if (senha.equals("0"))
				return;

			Boolean acesso = null;
			try {
				acesso = banco.acessaConta(id_conta, senha);
			} catch (RemoteException e) {
				out.println(
						"\nCOMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
				leia.nextLine();
				return;
			}
			if (acesso == null) {
				out.println("\nCONTA OU SENHA INVÁLIDA!\nPressione a tecla ENTER para continuar...");
				leia.nextLine();
				continue;
			}

			try {
				nomeTitular = banco.getNomeCliente(id_conta);
			} catch (RemoteException e) {
				out.println(
						"\nCOMPORTAMENTO INESPERADO! - Falha no banco de dados!\nPressione a tecla ENTER para continuar...");
				leia.nextLine();
				return;
			}
			conta = id_conta;

			opConta();
		}
	}

}

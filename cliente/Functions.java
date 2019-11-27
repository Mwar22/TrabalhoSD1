


static int menuConta() {
		out.println("\n\n\n\n\n*************ACESSANDO A CONTA*************\n            <>MENU DA CONTA<> ");
		out.println("\n\nAgencia atual: " + nDestaAgencia + "  Conta: " + nContaAtual + "\nTitular: " + nomeTitular);
		out.println("\nOPÇÕES:");
		out.println("       0 - SAIR DA CONTA");
		out.println("       1 - Saldo");
		out.println("       2 - Extrato detalhado");
		out.println("       3 - Saque");
		out.println("       4 - Depósito");
		out.println("       5 - Transferência");
		out.print("\nEntre com o número da opção: ");

		return leiaInt(5);
	}

	static void opConta() {
		while (true) {
			int opcao = menuConta();
			switch (opcao) {
			case 0:
				return;
			case 1:
				saldo();
				break;
			case 2:
				extrato();
				break;
			case 3:
				saque();
				break;
			case 4:
				deposito();
				break;
			case 5:
				transferencia();
				break;
			}
		}
	}

/**
	 * recebe um número inteiro >=0 até o valor máximo
	 * 
	 * @param maximo: valor máximo que pode ser recebido
	 * @return numero rebido
	 */
	static int leiaInt(int maximo) {
		int i;
		do {
			try {
				i = leia.nextInt();
				leia.nextLine();
				if (i >= 0 && i <= maximo)
					return i;
				out.print("NÚMERO INVÁLIDO! Tente novamente: ");
			} catch (Exception e) {
				out.print("DIGITE SOMENTE NÚMEROS: ");
				leia.nextLine();
			}
		} while (true);
	}

	static double leiaDouble() {
		double d;
		do {
			try {
				d = leia.nextDouble();
				leia.nextLine();
				if (d < 0) {
					out.print("O VALOR DEVE SER MAIOR OU IGUAL A ZERO: ");
					continue;
				}
				return d;
			} catch (Exception e) {
				out.print("DIGITE SOMENTE NÚMEROS: ");
				leia.nextLine();
			}
		} while (true);
	}

	static String leiaNome() {
		do {
			String temp = leia.nextLine();
			if (!temp.isEmpty())
				return temp;
			out.print("Digite um nome: ");
		} while (true);
	}

	static int menuInicial() {
		out.println("\n\n          SISTEMA BANCÁRIO\n");
		out.println("\nAgência: " + nDestaAgencia);
		out.println("OPÇÕES:");
		out.println("       0 - ENCERRAR PROGRAMA");
		out.println("       1 - Criar NOVA conta");
		out.println("       2 - ACESSAR uma conta");
		out.print("\nEntre com o número da opção: ");
		return leiaInt(2);
	}

	static void opInicial() {
		while (true) {
			int i = menuInicial();
			switch (i) {
			case 0:
				out.println("\n\n     SISTEMA BANCÁRIO ENCERRADO!\n\n");
				System.exit(0);
			case 1:
				novaConta();
				break;
			case 2:
				acessaConta();
				break;
			}
		}
	}


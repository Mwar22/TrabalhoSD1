package br.com.diego.banco;

import java.math.BigDecimal;

public class Main {
	public static void main(String[] args) {
		ClienteDAOImplementacao clienteDAO = new ClienteDAOImplementacao();
		ContaDAOImplementacao contaDAO = new ContaDAOImplementacao();
		Cliente Joao = new Cliente("João","77766633312", "testando@hotmail.com");
		/*boolean clienteJoao = clienteDAO.inserir(Joao);
		System.out.println(clienteJoao);*/
		boolean ForaJoao = clienteDAO.excluir(Joao);
		
	}
}

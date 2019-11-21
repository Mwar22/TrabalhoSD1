package br.com.diego.banco;

import java.math.BigDecimal;
import java.util.Date;

public class Main {
	public static void main(String[] args) {
		ClienteDAOImplementacao clienteDAO = new ClienteDAOImplementacao();
		ContaDAOImplementacao contaDAO = new ContaDAOImplementacao();
		Cliente Joao = new Cliente("João","77766633312", "testando@hotmail.com");
		/*boolean clienteJoao = clienteDAO.inserir(Joao);
		System.out.println(clienteJoao);*/
		//boolean ForaJoao = clienteDAO.excluir(Joao);
		Conta conta1 = new Conta(Joao,BigDecimal.valueOf(500),"asdfg");
		conta1.setAgencia(45);
		conta1.setIdConta(11156897);
		conta1.setCpf("76894677");
		contaDAO.saldo(123456789);
		System.out.println(contaDAO.saldo(123456789));
		
	}
}

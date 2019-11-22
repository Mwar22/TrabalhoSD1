package com.br.diego.banco;

import java.math.BigDecimal;
import java.util.List;


public class Main {
	public static void main(String[] args) {
		ClienteDAOImplementacao c1 = new ClienteDAOImplementacao();
		ContaDAOImplementacao contaDAO = new ContaDAOImplementacao();
		Cliente Joao = new Cliente("João","77766633312", "testando@hotmail.com");
		//insere Joao na tabela clientes
		//c1.inserir(Joao);
		
		Conta conta1 = new Conta(Joao,BigDecimal.valueOf(500),"asdfg");
		conta1.setAgencia(45);
		conta1.setIdConta(11156897);
		
		conta1.setSenha("asdfg");
		
		//insere conta1 na tabela contas
		//contaDAO.inserir(conta1, Joao);
		
		//deposito de 300 reais registrado na tabela movimento
		//contaDAO.Deposito(conta1, BigDecimal.valueOf(300));
		
		//TESTE DO LISTAR
		List<Conta> lista= contaDAO.listar("J%") ;
		for (int i=0; i<lista.size(); i++)
		System.out.println(lista.get(i).toString());
		
		
	}
}

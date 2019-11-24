package com.br.diego.banco;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;


public class Main {
	public static void main(String[] args) {

		BancoDAOImplementacao bancoDAO= null;
		try {
			bancoDAO = new BancoDAOImplementacao();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	

	
		

		
		//TESTE DO MÉTODO ACESSACONTA
		Boolean acesso=false;
		try {
			acesso = bancoDAO.acessaConta(11156897, "asdfg");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Teste de conta:\n"+acesso);
		
		try {
			System.out.println("Teste de saque:\n"+bancoDAO.saque(38, 2, new BigDecimal(44), "aaaa"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

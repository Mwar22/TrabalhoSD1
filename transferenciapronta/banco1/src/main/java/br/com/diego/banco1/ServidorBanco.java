package br.com.diego.banco1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public class ServidorBanco {

	public static void main(String[] args) {
		BancoDAO banco;
		Registry registry;

		try {
			// Cria o Registry
			registry = LocateRegistry.createRegistry(2019);

			banco = new BancoDAOImplementacao();
			registry.rebind("BancoServ", banco);

			System.out.println("Servidor do banco em execução com Java RMI");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

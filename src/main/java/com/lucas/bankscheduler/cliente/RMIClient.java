/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucas.bankscheduler.cliente;

import com.lucas.bankscheduler.DisponibleToClient;
import java.rmi.RemoteException;
import com.lucas.bankscheduler.DisponibleToServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author lucasdejesus
 */
public class RMIClient extends UnicastRemoteObject implements DisponibleToServer{
    private final String id;
    
    public RMIClient(String id) throws RemoteException
    {
        this.id = id;
    }
    
    public String getId()
    {
        return id;
    }

    @Override
    public boolean feedback(double d) throws RemoteException {
        System.out.println("Worked! : "+d);
        return true;
    }
    
    
    public static void main(String[] args) throws RemoteException
    {          
        RMIClient cliente = new RMIClient("C1");
        DisponibleToClient obj;
        Registry registry;
        
        try {
		// Localiza o registry criado pelo RMIServer
		registry = LocateRegistry.getRegistry("127.0.0.1", 2001);

		//Inicializa um "server" do lado do cliente para permitir
                //que o RMIServer comunique o resultado de uma operação limitada
                //ao RMIClient por meio de uma chamada de metodo remoto.
		registry.rebind(cliente.getId(), cliente);
		System.out.println("Listener do cliente em execução");
                
                //Inicializa um "Cliente" do lado do cliente(maquina) para que se possa
                //requisitar o RMIServer algum tipo de serviço
                //por exemplo um saldo (abaixo)
                obj = (DisponibleToClient) registry.lookup("S1");
		obj.reqSaldo(null, cliente.getId());
                
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e)
        {
            System.out.println("Client exception: " + e.getMessage());   
        }
        
        
    }
    
}

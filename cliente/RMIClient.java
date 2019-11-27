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

    
    private final Agencia agencia;
    private final int port;
    private String reg_addr;


    //variáveis resposáveis pela comunicação RMI
    private  Registry registry;
    private  DisponibleToClient obj;

    /** Construtor da classe
     *@author lucasdejesus
     *@param agencia Referência à um objeto agência em que um cliente RMI irá ser anexado
     *@param reg_addr Endereço ip de onde será buscado o registro.
     *@param port Porta em que este registro estará associado.
     */
    public RMIClient(Agencia agencia, String reg_addr, int port) throws RemoteException
    {
      this.agencia = agencia;
      this.port = port;
      this.reg_addr = reg_addr;
      this.obj = null;
    }


    /** Função que realiza a associação/config. do cliente RMI com um server RMI
     *@author lucasdejesus
     *@param server String identificadora do server de interesse (anexada ao registro).
     */
    public void setup(String server)
    {
      try {
		      // Localiza o registry criado pelo RMIServer
		      registry = LocateRegistry.getRegistry(reg_addr, port);

	      	//Inicializa um "server" do lado do cliente para permitir
          //que o RMIServer comunique o resultado de uma operação limitada
          //ao RMIClient por meio de uma chamada de metodo remoto.
		      registry.rebind(id.toString(), this);
		      System.out.println("Listener do cliente em execução");
                
          //Inicializa um "Cliente" do lado do cliente(maquina) para que se possa
          //requisitar o RMIServer algum tipo de serviço
          //por exemplo um saldo (abaixo)
          obj = (DisponibleToClient) registry.lookup(server);

         }catch (RemoteException e) {
		        e.printStackTrace();
	       } catch (Exception e){
            System.out.println("Client exception: " + e.getMessage());   
         }
        
    }
    

    //-------------------------------------GETTERs & SETTERs-------------------------------------
    public int getId()
    {
        return id;
    }

    //------------------------------IMPLEMENTAÇÂO DE INTERFACES REMOTAS--------------------------
    @Override
    public boolean feedback(double d) throws RemoteException {
        System.out.println("Worked! : "+d);
        return true;
    }
    






















    
    /*
    public static void main(String[] args) throws RemoteException
    {          
        RMIClient cliente = new RMIClient(id);
        DisponibleToClient obj;
        Registry registry;
        
        try {
		// Localiza o registry criado pelo RMIServer
		registry = LocateRegistry.getRegistry(reg_addr, port);

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
*/
    
}

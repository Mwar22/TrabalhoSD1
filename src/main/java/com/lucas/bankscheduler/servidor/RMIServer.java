/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucas.bankscheduler.servidor;

import com.lucas.bankscheduler.Scheduler;
import com.lucas.bankscheduler.Task;
import com.lucas.bankscheduler.Type;
import com.lucas.bankscheduler.Conta;
import com.lucas.bankscheduler.DisponibleToClient;
import com.lucas.bankscheduler.DisponibleToServer;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 *
 * @author lucasdejesus
 */
public class RMIServer extends UnicastRemoteObject implements DisponibleToClient, Runnable{
    
    
    private final String server_id;
    private static  int port;
    private final Scheduler tsk_manager;
    private final ServerTable servers;
    private  Registry registry;

    
    
    /**Método Construtor da classe
     * @author lucasdejesus
     * @param server_id "Nome" de identificacao do servidor
     * @param port  Porta em que ele ouvirá.
     * @param scheduler Referência ao objeto do escalonador
     * @param servers Referência ao objeto que contêm uma lista de servers associados
     * @throws java.lang.CloneNotSupportedException
     * @throws java.rmi.RemoteException
     */
    public RMIServer(String server_id, int port, Scheduler scheduler,
                     ServerTable servers) throws CloneNotSupportedException, RemoteException
    {
        this.server_id = server_id;
        RMIServer.port = port;
        
        //instancei um escalonador e já inicia sua thread
        tsk_manager = scheduler;
        this.servers = servers;

    }
    
    /** Retorna o identificador do servidor
     * @author lucasdejesus
     * @return Identificador do servidor
     */
    public String getServer_id() {
        return server_id;
    }
    
    /** Retorna a porta do servidor
     * @author lucasdejesus
     * @return Identificador do servidor
     */
    public static int getPort() {
        return port;
    }
   
    /**Configura um registry para o objeto em questão
     * @author lucasdejesus
     * @param registry Registro
     */
    public void setRegistry(Registry registry)
    {
        this.registry = registry;
    }
    
    //metodo run da thread que vai de fato executar o metodo selecionado pelo es
    //calonador
    @Override
    public void run()
    {
        DisponibleToServer obj;
        
        while(true)
        {
            
            //Verifica se tem alguma tarefa nova
            Task tsk = tsk_manager.getOutput();
           
            if (tsk != null)
            {
                 
                try {
                    //obtem o objeto remoto do cliente
                    obj = (DisponibleToServer) registry.lookup(tsk.getTerminal());
                    
                    //Reage de acordo com o tipo da tarefa
                    switch(tsk.getType())
                    {
                        case SAQUE:
                      
                  
                            break;
                        case SALDO:
                            //realiza o saldo no bando de dados usando as
                            //informações disponíveis no objeto tsk.
                            //...
                            
                            //retorna uma mensagem ao objeto requerinte
                            obj.feedback(2.5);
                            
                            //libera a saída pardrão para que possa ser escrita
                            tsk_manager.outputHasBeenRead();
                            break;
                        case EXTRATO:
                            break;
                        case TRANSFERENCIA:
                            break;
                    }
                 } catch (Exception e) {
                    System.out.println("Client exception: " + e.getMessage());
                 }
            }
        }
    }
    
    //--------------------------------FUNÇOES RMI-------------------------------
    
   
    @Override
    public boolean reqSaldo(Conta ct, String terminal) throws RemoteException {
        //cria uma nova tarefa e a adiciona ao escalonador
        Task tsk = new Task(4, Type.SALDO, ct, terminal);
        
        //se estiver cheio, repassa para outro server
        if (tsk_manager.isFull())
        {
           Server next =  servers.nextServer();
           Registry rg = LocateRegistry.getRegistry(next.getIp(), 2001);
            try {
                DisponibleToClient so = (DisponibleToClient) registry.lookup(next.getId());
                so.reqSaldo(ct, terminal);
            } catch (Exception ex) {
                System.out.println("Erro ao repassar requisição ao server < "+next.getId()+" >");
                return false;
            } 
           
        }
        else
        {
            tsk_manager.setInput(tsk);
        }
        return true;
    }

    
    @Override
    public boolean reqExtrato(Conta ct, String terminal) throws RemoteException {
        //cria uma nova tarefa e a adiciona a conta de orígem
        // e o destino conforme o javadoc para Task()
        Task tsk = new Task(4, Type.EXTRATO, ct, terminal);

        //se estiver cheio, repassa para outro server
        if (tsk_manager.isFull())
        {
           Server next =  servers.nextServer();
           Registry rg = LocateRegistry.getRegistry(next.getIp(), 2001);
            try {
                DisponibleToClient so = (DisponibleToClient) registry.lookup(next.getId());
                so.reqExtrato(ct, terminal);
            } catch (Exception ex) {
                System.out.println("Erro ao repassar requisição ao server < "+next.getId()+" >");
                return false;
            } 
           
        }
        else
        {
            tsk_manager.setInput(tsk);
        }
        return true;
    }

    @Override
    public boolean reqSaque(Conta ct, double valor, String terminal) throws RemoteException {
       //cria uma nova tarefa e a adiciona a conta de orígem
        // e o destino conforme o javadoc para Task()
 
        Task tsk = new Task(4, Type.SAQUE, ct, valor, terminal);

        //se estiver cheio, repassa para outro server
        if (tsk_manager.isFull())
        {
           Server next =  servers.nextServer();
           Registry rg = LocateRegistry.getRegistry(next.getIp(), 2001);
            try {
                DisponibleToClient so = (DisponibleToClient) registry.lookup(next.getId());
                so.reqSaque(ct, valor, terminal);
            } catch (Exception ex) {
                System.out.println("Erro ao repassar requisição ao server < "+next.getId()+" >");
                return false;
            } 
           
        }
        else
        {
            tsk_manager.setInput(tsk);
        }
        return true;
    }

    @Override
    public boolean reqTransferencia(Conta origem, Conta destino, double valor, String terminal) throws RemoteException {
        Task tsk = new Task(4, Type.SAQUE, origem, destino, valor, terminal);
        
        //se estiver cheio, repassa para outro server
        if (tsk_manager.isFull())
        {
           Server next =  servers.nextServer();
           Registry rg = LocateRegistry.getRegistry(next.getIp(), 2001);
            try {
                DisponibleToClient so = (DisponibleToClient) registry.lookup(next.getId());
                so.reqTransferencia(origem, destino, valor, terminal);
            } catch (Exception ex) {
                System.out.println("Erro ao repassar requisição ao server < "+next.getId()+" >");
                return false;
            } 
           
        }
        else
        {
            tsk_manager.setInput(tsk);
        }
        return true;
    }
    
    //--------------------------------------------------------------------------
    
    public static void main(String[] args) throws CloneNotSupportedException, RemoteException{
        Registry registry;
        
        //Instancia os objetos do server, da lista de servers e do escalonador
        Scheduler scheduler = new Scheduler(1);
        ServerTable server_list = new ServerTable(5);
	RMIServer server = new RMIServer("S1", 2001, scheduler, server_list);
        
      
        //Inicializa a lista de servers
        server_list.addServer("S2", "127.0.0.1");
        //server_list.addServer("S3", "192.168.2.2");
        //server_list.addServer("S4", "192.168.2.3");
        //server_list.addServer("S5", "192.168.2.4");
            
        
       
	try {
		// Cria o registro e adiciona o server no mesmo
		registry = LocateRegistry.createRegistry(RMIServer.getPort());
                
                //faz uma referencia do registro no server e adiciona o mesmo ao registro
                server.setRegistry(registry);
		registry.rebind(server.getServer_id(), server);
                
                //inicia as threads
                new Thread(scheduler).start();
                new Thread(server).start();
               
                
		System.out.println("Servidor < "+server.getServer_id()+" > em execução");
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        
       

	

        
        
        
    }
   

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucas.bankscheduler;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author lucasdejesus
 */
public interface DisponibleToClient extends Remote{
    
    //Requisições das operaçẽos
    
    public boolean reqExtrato(Conta ct, String terminal)throws RemoteException;
    
    /** Requisição de saldo
     * 
     * @param ct    Conta em questão
     * @param terminal  Naming do registry do objeto requerinte
     * @return true: Requisição bem sucedida. false caso contrário.
     * @throws RemoteException 
     */
    public boolean reqSaldo(Conta ct, String terminal)throws RemoteException;
    public boolean reqSaque(Conta ct, double valor, String terminal)throws RemoteException;
    public boolean reqTransferencia(Conta origem, Conta destino, double valor, String terminal)throws RemoteException;


    public boolean reqExisteConta(int numero) throws RemoteException;
    public boolean reqInserirCliente(Conta ct, String cpf, String titular, String email, String senha) throws RemoteException;
    
}

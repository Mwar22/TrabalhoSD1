package com.br.diego.banco;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface BancoDAO extends Remote{
	public Boolean acessaConta(Integer id_conta, String senha) throws RemoteException;
	public Boolean seContaJaExiste(Integer id_conta) throws RemoteException;
	public BigDecimal saldo(String cpf) throws RemoteException;
	public BigDecimal saldo(Integer id_conta) throws RemoteException;
	public boolean inserir (Conta conta, Cliente cliente) throws RemoteException;
	public boolean excluir (Conta conta) throws RemoteException;
	public String saque(int id_conta, int ag, BigDecimal valor, String senha) throws RemoteException;
	public String deposito(int id_conta, int ag, BigDecimal valor, String senha) throws RemoteException;
	public Boolean inserirClienteConta(int id_conta, int ag, double saldo, String cpf, String nome, String email, String senha)throws RemoteException;
	public String teste() throws RemoteException;
	public String getNomeCliente(int id_conta) throws RemoteException;


}

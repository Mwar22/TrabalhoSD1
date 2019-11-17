package br.com.diego.banco;

import java.util.List;

public interface ContaDAO {
	public Conta consultar(String nome);
	public Conta consultar(Integer id);
	public boolean inserir (Conta conta);
	public boolean excluir (Conta conta);
	public List<Conta> listar(String nome);
}

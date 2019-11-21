package br.com.diego.banco;

import java.math.BigDecimal;
import java.util.List;

public interface ContaDAO {
	public Conta saldo(String nome);
	public Conta saldo(Integer id);
	public boolean inserir (Conta conta, Cliente cliente);
	public boolean excluir (Conta conta);
	public List<Conta> listar(String nome);
	public Conta Saque(Conta conta, BigDecimal valor);
	public Conta Deposito(Conta conta, BigDecimal valor);
}

package com.br.diego.banco;

import java.math.BigDecimal;
import java.util.List;

public interface ContaDAO {
	public BigDecimal saldo(String cpf);
	public BigDecimal saldo(Integer id_conta);
	public boolean inserir (Conta conta, Cliente cliente);
	public boolean excluir (Conta conta);
	public List<Conta> listar(String nome);
	public String saque(Integer id_conta, String senha, BigDecimal valor);
	public String deposito(Integer id_conta, String senha, BigDecimal valor);
}

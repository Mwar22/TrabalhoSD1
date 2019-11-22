package com.br.diego.banco;

import java.math.BigDecimal;

public interface MovimentoDAO {

	public boolean inserirMov(Conta conta, String tipo, BigDecimal valor);
	public boolean excluirMov(Conta conta);
	
}

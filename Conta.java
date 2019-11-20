package br.com.diego.banco;

import java.math.BigDecimal;

public class Conta {
	private Integer idConta;
	private String cpf;
	private BigDecimal saldo;
	private String senha;
	private Integer agencia;
	

	public Conta(Cliente cliente, BigDecimal saldo, String senha) {
		this.cpf = cliente.getCpf();
		this.saldo = saldo;
		this.senha = senha;
	}

	public Conta() {
		this.saldo = new BigDecimal(0);
	}

	public Integer getIdConta() {
		return idConta;
	}

	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public String toString() {
		StringBuilder strRetorno = new StringBuilder();
		strRetorno.append("-------- ");
		strRetorno.append("\nConta: ");
		strRetorno.append("\nId: "+getIdConta());
		strRetorno.append("\nCPF:"+getCpf());
		strRetorno.append("\nSaldo: "+getSaldo().doubleValue());
		strRetorno.append("\n-------- ");
		
		return strRetorno.toString(); 
	}

}

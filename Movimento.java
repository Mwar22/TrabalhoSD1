package br.com.diego.banco;

import java.math.BigDecimal;

public class Movimento {
	private BigDecimal valor;
	private String tipo;
	private Integer idMov;
	private Integer idConta;
	private BigDecimal saldoAnt;
	private String data;
	public  Movimento(Conta conta,String tipo, BigDecimal valor) {
		this.idConta = conta.getIdConta();
		this.valor = valor;
		this.saldoAnt = conta.getSaldo();
		this.tipo = tipo;
	}
	public Movimento() {
		this.valor= BigDecimal.valueOf(0);
		this.saldoAnt = BigDecimal.valueOf(0);
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getIdMov() {
		return idMov;
	}
	public void setIdMov(Integer idMov) {
		this.idMov = idMov;
	}
	public Integer getIdConta() {
		return idConta;
	}
	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}
	public BigDecimal getSaldoAnt() {
		return saldoAnt;
	}
	public void setSaldoAnt(BigDecimal saldoAnt) {
		this.saldoAnt = saldoAnt;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	
}

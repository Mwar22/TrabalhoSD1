package br.com.diego.banco;

public class Cliente {
	private String nome;
	private String cpf;
	private String email;
	
	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}
	public Cliente(String nome, String cpf, String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String toString() {
		StringBuilder strRetorno = new StringBuilder();
		strRetorno.append("-------- ");
		strRetorno.append("\nCliente: ");
		strRetorno.append("\nNome: "+getNome());
		strRetorno.append("\nCPF:"+getCpf());
		strRetorno.append("\nEmail: "+getEmail());
		strRetorno.append("\n-------- ");
		
		return strRetorno.toString(); 
	}

}

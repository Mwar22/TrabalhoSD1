package com.br.diego.banco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContaDAOImplementacao implements ContaDAO {
	
	//String url = "jdbc:postgresql://172.16.5.130/banco?user=postgres&password=diego";
	String url = "jdbc:postgresql://localhost/Banco?user=postgres&password=postgres";

	
	/*
	 * retorna o saldo da conta do CPF especificado
	 * 
	 */
	public BigDecimal saldo(String cpf) {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		try {
			
			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select saldo from contas where cpf=?");
			ps.setString(1, cpf);
			rs = ps.executeQuery();

			if (rs.next()) {				
				return (rs.getBigDecimal("saldo"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conexaoBanco != null) {
				try {
					conexaoBanco.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean inserir(Conta conta, Cliente cliente) {
		PreparedStatement ps = null;
		int rs;
		Connection conexaoBanco = null;
		try {


			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("insert into contas (id_conta, senha, cpf, saldo, agencia) values (?,?,?,?,?)");
			ps.setInt(1, conta.getIdConta());
			ps.setString(2, conta.getSenha());
			ps.setString(3, cliente.getCpf());
			ps.setBigDecimal(4, conta.getSaldo());
			ps.setInt(5, conta.getAgencia());
			rs = ps.executeUpdate();

			if (rs > 0) {

				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conexaoBanco != null) {
				try {
					conexaoBanco.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	
	/* 
	 * retorna o saldo da conta
	 * @param id : número da conta
	 */
	public BigDecimal saldo(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		try {
			
			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select saldo from contas where id_conta=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				return (rs.getBigDecimal("saldo"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conexaoBanco != null) {
				try {
					conexaoBanco.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public boolean excluir(Conta conta) {
		PreparedStatement ps = null;
		Connection conexaoBanco = null;
		try {

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("delete from contas where id_conta =?");
			ps.setInt(1, conta.getIdConta());

			if (ps.executeUpdate() > 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conexaoBanco != null) {
				try {
					conexaoBanco.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public List<Conta> listar(String nome) {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		Conta conta;
		List<Conta> listaconta = new ArrayList<Conta>();
		try {

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select ct.agencia, ct.id_conta, ct.cpf, ct.saldo  "
					+ "from Clientes cl INNER JOIN Contas ct ON cl.cpf = ct.cpf where cl.nome like ?");
			ps.setString(1, nome);
			rs = ps.executeQuery();

			while(rs.next() == true) {
				conta = new Conta();
				
				conta.setAgencia(rs.getInt("agencia"));
				conta.setIdConta(rs.getInt("id_conta"));
				conta.setCpf(rs.getString("cpf"));
				conta.setSaldo(rs.getBigDecimal("saldo"));
				listaconta.add(conta);
				
			} 
			return listaconta;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conexaoBanco != null) {
				try {
					conexaoBanco.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * Realiza saque ATÔMICO
	 */
	public String saque(Integer id_conta, String senha, BigDecimal valor) {
		int result=-1;
		String sql;
		if (valor.compareTo(new BigDecimal(0)) == -1) return "ERRO: O valor deve ser positivo!";
		
		Statement s= null;
		Connection conexaoBanco = null;
			try {
				conexaoBanco = DriverManager.getConnection(url);

				conexaoBanco.setAutoCommit(false);
				s = conexaoBanco.createStatement();
				
				//insere o movimento com o saldo anterior à operação:
				sql= 	"INSERT INTO Movimento (valor, tipo, id_conta, saldo_ant)"
						+ " VALUES ("+valor+", 'SAQUE', "+id_conta
						+", (select saldo from Contas where id_conta = "+id_conta+"));";

				result = s.executeUpdate(sql);
				if (result == 0) {//se não conseguir
					conexaoBanco.rollback();//cancela tudo
					return "Falha na operação";
				}
				
				//Realiza a operação, atualizando o saldo:
				sql =  " UPDATE Contas SET saldo = saldo -"+valor
						+ "WHERE id_conta = "+id_conta+" AND saldo >= "+valor+"AND senha = '"+senha+"';";	
				result = s.executeUpdate(sql);
				
				///(ATOMICIDADE) realiza todas as operações de uma vez, ou cancela tudo:
				if (result == 0) {
					conexaoBanco.rollback();
					return "Falha na operação";
				}
				else {
					conexaoBanco.commit();
					return "Saque realizado com sucesso!";
				}
				
			} catch (SQLException e) {
				e.printStackTrace();		
				throw new RuntimeException(e);
			} finally {
				if (conexaoBanco != null) {
					try {
						conexaoBanco.close();
						s.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
	}
	
	/*
	 * Realiza depósito ATÔMICO
	 */
	public String deposito(Integer id_conta, String senha, BigDecimal valor) {
		int result=-1;
		String sql;
		if (valor.compareTo(new BigDecimal(0)) == -1) return "ERRO: O valor deve ser positivo!";
		
		Statement s= null;
		Connection conexaoBanco = null;
			try {
				conexaoBanco = DriverManager.getConnection(url);

				conexaoBanco.setAutoCommit(false);
				s = conexaoBanco.createStatement();
				
				//insere o movimento com o saldo anterior à operação:
				sql= 	"INSERT INTO Movimento (valor, tipo, id_conta, saldo_ant)"
						+ " VALUES ("+valor+", 'DEPÓSITO', "+id_conta
						+", (select saldo from Contas where id_conta = "+id_conta+"));";

				result = s.executeUpdate(sql);
				if (result == 0) {//se não conseguir
					conexaoBanco.rollback();//cancela tudo
					return "Falha na operação";
				}
				
				//Realiza a operação, atualizando o saldo:
				sql =  " UPDATE Contas SET saldo = saldo +"+valor
						+ "WHERE id_conta = "+id_conta+"AND senha = '"+senha+"';";	
				result = s.executeUpdate(sql);
				
				///(ATOMICIDADE) realiza todas as operações de uma vez, ou cancela tudo:
				if (result == 0) {
					conexaoBanco.rollback();
					return "Falha na operação";
				}
				else {
					conexaoBanco.commit();
					return "Depósito realizado com sucesso!";
				}
				
			} catch (SQLException e) {
				e.printStackTrace();		
				throw new RuntimeException(e);
			} finally {
				if (conexaoBanco != null) {
					try {
						conexaoBanco.close();
						s.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
	}
	
/*	DESNECESSÁRIO / DEPOIS ESTA PARTE SERÁ EXCLUÍDA...
	public Conta AtualizaSaldo(Conta conta) {
		PreparedStatement ps = null;
		int rs;
		Connection conexaoBanco = null;
		
		try {

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("update movimento set saldo_ant = (select saldo from contas where id_conta = (?))");
			ps.setInt(1, conta.getIdConta());
			rs = ps.executeUpdate();

			if (rs > 0) {

				return conta;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conexaoBanco != null) {
				try {
					conexaoBanco.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	*/

}
	



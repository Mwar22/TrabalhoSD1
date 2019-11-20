package br.com.diego.banco;

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

	public Conta saldo(String cpf) {
		PreparedStatement ps = null;
		ResultSet rs;
		String url;
		Connection conexaoBanco = null;
		Conta conta;
		try {
			
			url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";
			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select id_conta, cpf, saldo from contas where cpf=?");
			ps.setString(1, cpf);
			rs = ps.executeQuery();

			if (rs.next()) {
				conta = new Conta();
				
				conta.setIdConta(rs.getInt("id_conta"));
				conta.setCpf(rs.getString("cpf"));
				conta.setSaldo(rs.getBigDecimal("saldo"));

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

	public boolean inserir(Conta conta) {
		PreparedStatement ps = null;
		int rs;
		String url;
		Connection conexaoBanco = null;
		try {
			//url = "jdbc:postgresql://172.16.5.130/banco?user=postgres&password=diego";
			url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("insert into contas (id_conta, cpf, saldo, agencia) values (?,?,?,?)");
			ps.setInt(1, conta.getIdConta());
			ps.setString(2, conta.getCpf());
			ps.setBigDecimal(3, conta.getSaldo());
			ps.setInt(4, conta.getAgencia());
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

	public Conta saldo(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs;
		String url;
		Connection conexaoBanco = null;
		Conta conta;
		try {
			
			url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select id_conta, cpf, saldo from contas where id_conta=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				conta = new Conta();

				conta.setIdConta(rs.getInt("id_conta"));
				conta.setCpf(rs.getString("cpf"));
				conta.setSaldo(rs.getBigDecimal("saldo"));

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

	public boolean excluir(Conta conta) {
		PreparedStatement ps = null;
		String url;
		Connection conexaoBanco = null;
		try {
			
			url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";

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
		String url;
		Connection conexaoBanco = null;
		Conta conta;
		List<Conta> listaconta = new ArrayList<Conta>();
		try {
			
			url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("select id_conta, cpf, saldo from contas where nome=?");
			ps.setString(1, nome);
			rs = ps.executeQuery();

			while(rs.next() == true) {
				conta = new Conta();

				conta.setIdConta(rs.getInt("id"));
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
	public Conta Saque(Conta conta, BigDecimal valor) {
		PreparedStatement ps = null;
		int rs;
		String url;
		Connection conexaoBanco = null;
		BigDecimal saldo = conta.getSaldo().subtract(valor) ;
		try {
			//url = "jdbc:postgresql://172.16.5.130/banco?user=postgres&password=diego";
			url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("update contas set saldo = (?) where id_conta = (?)");
			ps.setBigDecimal(1, saldo);
			ps.setInt(2, conta.getIdConta());
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
	public Conta Deposito(Conta conta, BigDecimal valor) {
		PreparedStatement ps = null;
		int rs;
		String url;
		Connection conexaoBanco = null;
		BigDecimal saldo = conta.getSaldo().add(valor) ;
		try {
			//url = "jdbc:postgresql://172.16.5.130/banco?user=postgres&password=diego";
			url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("update contas set saldo = (?) where id_conta = (?)");
			ps.setBigDecimal(1, saldo);
			ps.setInt(2, conta.getIdConta());
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

}
	



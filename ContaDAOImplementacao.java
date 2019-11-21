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
	
	//String url = "jdbc:postgresql://172.16.5.130/banco?user=postgres&password=diego";
	String url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";

	public Conta saldo(String cpf) {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		Conta conta;
		try {
			
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

	public Conta saldo(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conexaoBanco = null;
		Conta conta;
		try {
			
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
			ps = conexaoBanco.prepareStatement("select id_conta, cpf, saldo from contas where nome like ?");
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
		Connection conexaoBanco = null;
		//compara valor com saldo, se o valor da saque for maior que o saldo a operação não é realizada.
		if (valor.compareTo(conta.getSaldo()) == -1 ) {
			return null;
		}else {
			BigDecimal saldo = conta.getSaldo().subtract(valor) ;
			try {
	
				conexaoBanco = DriverManager.getConnection(url);
				ps = conexaoBanco.prepareStatement("update contas set saldo = (?) where id_conta = (?)");
				ps.setBigDecimal(1, saldo);
				ps.setInt(2, conta.getIdConta());
				rs = ps.executeUpdate();
	
				if (rs > 0) {
					//insere a operacao de saque e o valor na tabela movimento do B.D
					MovimentoDAOImplementacao mov1 = new MovimentoDAOImplementacao();
					mov1.inserirMov(conta, "saque", valor);
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
	public Conta Deposito(Conta conta, BigDecimal valor) {
		PreparedStatement ps = null;
		int rs;
		Connection conexaoBanco = null;
		BigDecimal saldo = conta.getSaldo().add(valor) ;
		try {

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("update contas set saldo = (?) where id_conta = (?)");
			ps.setBigDecimal(1, saldo);
			ps.setInt(2, conta.getIdConta());
			rs = ps.executeUpdate();

			if (rs > 0) {
				MovimentoDAOImplementacao mov1 = new MovimentoDAOImplementacao();
				mov1.inserirMov(conta, "depósito", valor);

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

}
	



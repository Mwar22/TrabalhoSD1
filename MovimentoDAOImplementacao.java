package br.com.diego.banco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;




public class MovimentoDAOImplementacao {
	
	public boolean inserirMov(Conta conta, Integer id_mov, String tipo, BigDecimal valor, Date data) {
		PreparedStatement ps = null;
		int rs;
		String url;
		Connection conexaoBanco = null;
		
		try {
			//url = "jdbc:postgresql://172.16.5.130/banco?user=postgres&password=diego";
			url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("insert into movimento (valor,tipo,id_mov,id_conta, saldo_ant,data) values (?,?,?,?,?,?)");
			ps.setBigDecimal(1, valor);
			ps.setString(2, tipo);
			ps.setInt(3, id_mov);
			ps.setInt(4, conta.getIdConta());
			ps.setBigDecimal(5, conta.getSaldo());
			ps.setDate(6,  data);
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
	public boolean excluirMov(Conta conta) {
		PreparedStatement ps = null;
		String url;
		Connection conexaoBanco = null;
		try {
			
			url = "jdbc:postgresql://localhost/Banco?user=postgres&password=84067890";

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("delete from movimento where id_conta =?");
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
	
	
}
	

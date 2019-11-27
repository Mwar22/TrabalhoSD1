package br.com.diego.banco1;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;




public class MovimentoDAOImplementacao implements MovimentoDAO {
	
	//String url = "jdbc:postgresql://172.16.5.130/banco?user=postgres&password=diego";
	String url = "jdbc:postgresql://localhost/banco?user=postgres&password=victor";
	
	public boolean inserirMov(Conta conta, String tipo, BigDecimal valor) {
		PreparedStatement ps = null;
		int rs;
		Connection conexaoBanco = null;
		
		try {

			conexaoBanco = DriverManager.getConnection(url);
			ps = conexaoBanco.prepareStatement("insert into movimento (valor,tipo,id_conta, saldo_ant) values (?,?,?,?)");
			ps.setBigDecimal(1, valor);
			ps.setString(2, tipo);
			ps.setInt(3, conta.getIdConta());
			ps.setBigDecimal(4, conta.getSaldo());
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
		Connection conexaoBanco = null;
		try {

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
	

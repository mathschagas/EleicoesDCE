package br.uece.eleicoes.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChapaDAO extends DAO {
	private Connection connection;

	public ChapaDAO() {
		connection = ConnectionSingleton.getConnection();
	}

	public void adiciona(Chapa chapa) throws SQLException {
		String sql = "insert into chapas " + "(nome, mat_presidente, mat_secretario, mat_tesoureiro, num_votos)"
				+ "values (?,?,?,?,?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, chapa.getNome());
			stmt.setLong(2, chapa.getMatPresidente());
			stmt.setLong(3, chapa.getMatSecretario());
			stmt.setLong(4, chapa.getMatTesoureiro());
			stmt.setInt(5, 0);

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	public Boolean pesquisaChapa(Long matricula) {
		String sql = "SELECT * FROM chapas WHERE mat_presidente = ? OR mat_secretario = ? OR mat_tesoureiro = ?;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, matricula);
			stmt.setLong(2, matricula);
			stmt.setLong(3, matricula);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public Boolean pesquisaChapa(String nome) {
		String sql = "SELECT * FROM chapas WHERE nome = ?;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public ObservableList<String> getListaNomesChapas() {
		ObservableList<String> listaNomesChapas = FXCollections.observableArrayList();
		String sql = "SELECT nome FROM chapas;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listaNomesChapas.add(rs.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaNomesChapas;
	}
	
	public Chapa getChapaPorNome(String nome) {
		Chapa c = new Chapa();
		String sql = "SELECT * FROM chapas WHERE nome = ?;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				c.setNome(rs.getString("nome"));
				c.setMatPresidente(rs.getLong("mat_presidente"));
				c.setMatSecretario(rs.getLong("mat_secretario"));
				c.setMatTesoureiro(rs.getLong("mat_tesoureiro"));
				c.setId(rs.getInt("idchapas"));
				c.setNumVotos(rs.getInt("num_votos"));
			}
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void update(Integer id, Chapa chapa) {
		String sql = "update chapas set nome=?, mat_presidente=?, mat_secretario=?, mat_tesoureiro=?, num_votos=? WHERE idchapas=?;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, chapa.getNome());
			stmt.setLong(2, chapa.getMatPresidente());
			stmt.setLong(3, chapa.getMatSecretario());
			stmt.setLong(4, chapa.getMatTesoureiro());
			stmt.setInt(5, chapa.getNumVotos());
			stmt.setInt(6, id);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Chapa getChapaPorId(Integer idChapaVotada) {
		Chapa c = new Chapa();
		String sql = "SELECT * FROM chapas WHERE idchapas = ?;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idChapaVotada);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				c.setNome(rs.getString("nome"));
				c.setMatPresidente(rs.getLong("mat_presidente"));
				c.setMatSecretario(rs.getLong("mat_secretario"));
				c.setMatTesoureiro(rs.getLong("mat_tesoureiro"));
				c.setId(rs.getInt("idchapas"));
				c.setNumVotos(rs.getInt("num_votos"));
			}
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Chapa> getChapas() {
		ArrayList<Chapa> chapas = new ArrayList<Chapa>();
		String sql = "SELECT * FROM chapas;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Chapa chapaAtual = new Chapa();
				chapaAtual.setNome(rs.getString("nome"));
				chapaAtual.setMatPresidente(rs.getLong("mat_presidente"));
				chapaAtual.setMatSecretario(rs.getLong("mat_secretario"));
				chapaAtual.setMatTesoureiro(rs.getLong("mat_tesoureiro"));
				chapaAtual.setId(rs.getInt("idchapas"));
				chapaAtual.setNumVotos(rs.getInt("num_votos"));
				chapas.add(chapaAtual);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chapas;
	}

	public Integer getTotalDeVotos() {
		String sql = "SELECT SUM(num_votos) AS total FROM chapas;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

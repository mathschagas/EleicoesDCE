package br.uece.eleicoes.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO extends DAO {
	private Connection connection;

	public AlunoDAO() {
		connection = ConnectionSingleton.getConnection();
	}

	public void adiciona(Aluno aluno) throws SQLException {
		String sql = "insert into alunos " + "(matricula,nome,curso,senha)" + "values (?,?,?,?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, aluno.getMatricula());
			stmt.setString(2, aluno.getNome());
			stmt.setString(3, aluno.getCurso());
			stmt.setString(4, aluno.getSenha());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	public Boolean pesquisaAluno(Aluno aluno) throws SQLException {
		String sql = "select * from alunos where matricula = ? AND senha = ?;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, aluno.getMatricula());
			stmt.setString(2, aluno.getSenha());
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				rs.close();
				stmt.close();
				return true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw e;
		}
		return false;

	}

	public Aluno getAluno(long matricula) throws SQLException {
		String sql = "select * from alunos where matricula = ?;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setLong(1, matricula);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Aluno alunoEncontrado = new Aluno();
				alunoEncontrado.setMatricula(rs.getLong("matricula"));
				alunoEncontrado.setNome(rs.getString("nome"));
				alunoEncontrado.setCurso(rs.getString("curso"));
				alunoEncontrado.setIdChapaVotada(rs.getInt("id_chapa_votada"));
				rs.close();
				stmt.close();
				return alunoEncontrado;
			}
		} catch (SQLException e) {
			throw e;
		}
		return null;
	}

	public void update(Long matricula, Aluno alunoEleitor) {
		String sql = "update alunos set nome=?, matricula=?, curso=?, id_chapa_votada=? WHERE matricula=?;";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, alunoEleitor.getNome());
			stmt.setLong(2, alunoEleitor.getMatricula());
			stmt.setString(3, alunoEleitor.getCurso());
			stmt.setInt(4, alunoEleitor.getIdChapaVotada());
			stmt.setLong(5, matricula);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}

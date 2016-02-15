package br.uece.eleicoes.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

	private static Connection connection = null;

	protected ConnectionSingleton() {}

	public static Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost/eleicoesdce", "root", "12345678");
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return connection;
	}

}

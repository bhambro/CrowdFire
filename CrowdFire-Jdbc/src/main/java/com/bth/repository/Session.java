package com.bth.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Session implements AutoCloseable {
	
	private Connection connection;

	
	public Session(Connection connection) {
		this.connection = connection;
	}
	
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}
	
	public PreparedStatement prepareStatementForKeyReturn(String sql) throws SQLException {
		return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}
	
	public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}
	
	public Connection connection() {
		return connection;
	}
	
	public void rollBack() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

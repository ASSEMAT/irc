package com.cfranc.irc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConMySql 
{
	
	private static ConMySql _instance = null;
	private Connection conn;
	
	private ConMySql() throws SQLException {
		try {
			String s="jdbc:mysql://localhost:3306/irc";
			conn = DriverManager.getConnection(s,"root","root");
		}
		catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}

	public static ConMySql getInst() throws SQLException
	{
		if (_instance == null)
			_instance = new ConMySql();
		return _instance;
	}
		
	public Connection getConn()
	{
		return conn;
	}
	
}

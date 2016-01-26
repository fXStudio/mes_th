package com.qm.mes.framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public final class ConnectionPool {
	public static Connection getCon() {
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		String strConnectInfo = "jdbc:microsoft:sqlserver://10.22.110.71:1433;databasename=zgl_soa_test";
		Connection connection;
		try {
			connection = DriverManager.getConnection(strConnectInfo, "sa",
					"pcc5995606");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Connection getConOracle() {
		try
	     {
	       DataSource ds;
	       Context ctx;
	       ctx = new InitialContext();
	       ds = (DataSource)ctx.lookup("mes_framework");//Oradb is JNDI name   other JNDI name is soa_mes
	       return ds.getConnection();
	     }
	     catch (Exception e) 
	     {
	       System.out.println("JNDI DataSource Pool connection:"+e);
	       return null;
	     }
	}
	
	public static Connection getConOracle1() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		String strConnectInfo = "jdbc:oracle:thin:@10.22.110.10:1521:soa";
		Connection connection;
		try {
			connection = DriverManager.getConnection(strConnectInfo, "soa",
					"soa");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static void main(String args[]) {
		try {
			System.out.println(ConnectionPool.getConOracle().getMetaData()
					.getDatabaseProductName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

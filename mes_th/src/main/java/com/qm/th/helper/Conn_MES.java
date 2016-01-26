package com.qm.th.helper;

import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 数据库连接池
 * 
 * @author Administrator
 */
public class Conn_MES {
	/** Tomcat JNDI连接描述符 */
	private static final String JNDI_EXPRESS = "java:comp/env/mes_th";

	/** 数据源对象 */
	private static DataSource DS;

	/**
	 * 通过JNDI实例化数据源对象
	 */
	static {
		try {
			DS = (DataSource) new InitialContext().lookup(JNDI_EXPRESS);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建数据库连接
	 * 
	 * @return
	 */
	public java.sql.Connection getConn() {
		try {
			return DS.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
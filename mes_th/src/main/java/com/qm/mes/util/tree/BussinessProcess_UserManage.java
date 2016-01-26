package com.qm.mes.util.tree;

import java.sql.*;

public class BussinessProcess_UserManage {
	private Connection conn = null;
	private IDAO_UserManage dao = null;

	public BussinessProcess_UserManage(Connection conn) throws Exception {
		this.conn = conn;
		dao = DAOFactory_UserManage.getInstance(conn);
	}

	public void updateUserInterface(String userid, String color)
			throws Exception {
		String sql = dao.getSQL_updateUserInterface(userid, color);
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.out
					.println("BussinessProcess_UserManage类updateUserInterface(String userid,String color)方法抛出异常"
							+ e);
			throw e;
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

}
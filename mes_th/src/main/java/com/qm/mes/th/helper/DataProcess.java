package com.qm.mes.th.helper;

import java.sql.*;
import java.util.*;

/**
 * 数据批量处理工具
 * 
 * @author Administrator
 */
public class DataProcess {

	/**
	 * 批量更新
	 * 
	 * @param con
	 * @param v_sql
	 * @return
	 */
	public static boolean updateBatch(Connection con, Vector v_sql) {
		Statement stmt = null;

		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();

			for (int i = 0; i < v_sql.size(); i++) {
				if (v_sql.elementAt(i) != null) {
					stmt.addBatch((String) v_sql.elementAt(i));
				}
			}
			stmt.executeBatch();
			con.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
				if (stmt != null) {
					try {
						stmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						stmt = null;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
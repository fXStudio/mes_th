package com.qm.th.helpers;

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
	public static boolean updateBatch(Connection con, Vector<String> v_sql) {
		Statement stmt = null;

		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			
			// 批量执行SQL语句
			for (String sql : v_sql) {
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			con.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					con.setAutoCommit(true);
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
		return false;
	}
}
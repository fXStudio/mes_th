package common;

import java.sql.*;
import java.util.*;

public class DataProcess {
	public static String message = "";

	public static boolean updateBatch(Connection con, Vector v_sql) {
		Statement stmt = null;

		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();

			for (int i = 0; i < v_sql.size(); i++) {
				if (v_sql.elementAt(i) != null) {
					System.out.println(v_sql.elementAt(i));
					stmt.addBatch((String) v_sql.elementAt(i));
				}
			}
			stmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);
			if (stmt != null)
				stmt.close();

			return true;
		} catch (Exception e) {
			System.out.println("DataProcess类 updateBatch()方法抛出异常！" + e.getMessage());
			message = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				con.setAutoCommit(true);
			} catch (SQLException se) {
				System.out.println("DataProcess类 updateBatch()方法抛出异常！" + se);
			}
		}
		return false;
	}

	public String getErrMessage() {
		return this.message;
	}
}
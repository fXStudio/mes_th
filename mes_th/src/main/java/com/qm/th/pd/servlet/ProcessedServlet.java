package com.qm.th.pd.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qm.th.helper.Conn_MES;

/**
 * 已处理报文查询
 * 
 * @author GaoHF
 */
public class ProcessedServlet extends HttpServlet {
	/***/
	private static final long serialVersionUID = 1L;
	/** 连接池 */
	private static Conn_MES cm = new Conn_MES();

	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = cm.getConn();
			stmt = conn.createStatement();

			StringBuilder strSql = new StringBuilder();
			strSql.append("SELECT TOP 5 A.CFILENAME, A.COPERTIME FROM (");
			strSql.append("   SELECT * FROM (");
			strSql.append("     SELECT TOP 5 CFILENAME, COPERTIME FROM PRODUCTDATA_W ORDER BY COPERTIME DESC");
			strSql.append("   ) TBL_1 UNION");
			strSql.append("   SELECT * FROM (");
			strSql.append("     SELECT TOP 5 CFILENAME, COPERTIME FROM PRODUCTDATA_A ORDER BY COPERTIME DESC");
			strSql.append("   ) TBL_2 UNION");
			strSql.append("   SELECT * FROM (");
			strSql.append("     SELECT TOP 5 CFILENAME, COPERTIME FROM PRODUCTDATA_A1 ORDER BY COPERTIME DESC");
			strSql.append("   ) TBL_3");
			strSql.append(") A ORDER BY A.COPERTIME DESC");

			rs = stmt.executeQuery(strSql.toString());

			List<String> list = new ArrayList<String>();
			while (rs.next()) {
				list.add( "[\"" + rs.getString("CFILENAME") + "\"");
				list.add("\"" + rs.getString("COPERTIME") + "\"]");
			}
			resp.getWriter().println(java.util.Arrays.toString(list.toArray()));
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().println(false);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					conn = null;
				}
			}
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}

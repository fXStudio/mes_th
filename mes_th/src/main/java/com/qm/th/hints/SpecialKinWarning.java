package com.qm.th.hints;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qm.th.helpers.Conn_MES;

/**
 * 特殊KIN号警告
 * 
 * @author Administrator
 */
public class SpecialKinWarning extends HttpServlet {
	/** SerialCode */
	private static final long serialVersionUID = 1L;
	/** 数据库连接池 */
	private static final Conn_MES cm = new Conn_MES();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(specKinInfo());
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 特殊KIN号信息
	 * 
	 * @return
	 */
	private String specKinInfo() {
		StringBuilder strSql  = new StringBuilder();
		strSql.append(" SELECT MAX(A.DABEGIN) AS ID");
		strSql.append(" FROM CARDATA A INNER JOIN SPECIALKIN B ");
		strSql.append(" ON A.CCARNO LIKE B.CCARNO + '%' AND DATEDIFF(DAY, B.DTODATE, GETDATE()) < 0");
		strSql.append(" HAVING MAX(A.DABEGIN) IS NOT NULL");
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// 出现特殊KIN号标识
		String discovery = "{}";
		
		try{
			conn = cm.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql.toString());
			
			if(rs.next()){
				discovery = "{\"id\":\"" + rs.getString("ID") + "\"}";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// 资源清理
			if(rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					rs = null;
				}
			}
			if(stmt != null){
				try{
					stmt.close();
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					stmt = null;
				}
			}
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					conn = null;
				}
			}
		}
		return discovery;
	}
}

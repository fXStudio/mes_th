<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="db.Terminal"%>

<%
	Connection con = null;
	try {
			con = new Conn_MES().getConn();
			String cPageNo = request.getParameter("cPageNo");
			Terminal ter = new Terminal();
			String emp = (String) session.getAttribute("username");
			
			if(pagenoExists(con, cPageNo)) {// 不能重复写入数据
					return;
			}
			ter.insertPageNo_State(con, cPageNo, emp);
	} finally {
			if (con != null) {
					try {
							con.close();
					} catch (java.sql.SQLException e) {
							e.printStackTrace();
					} finally {
							con = null;
					}
			}
	}
%>

<%!
	 synchronized boolean pagenoExists(Connection conn, String pageno){
		  PreparedStatement stmt = null;
		  ResultSet rs = null;
		  
		  try {
				stmt = conn.prepareStatement("SELECT id FROM pageno_state WHERE pageno = ?");
				stmt.setString(1, pageno);
				rs = stmt.executeQuery();
				
				return rs.next();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (java.sql.SQLException e) {
						e.printStackTrace();
					} finally {
						rs = null;
					}
				}
				if (stmt != null) {
					try {
						stmt.close();
					} catch (java.sql.SQLException e) {
						e.printStackTrace();
					} finally {
						stmt = null;
					}
		 		}
			}
	}
%>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>

<%
	Connection con = null;
	try {
		con = new Conn_MES().getConn();
		System.out.println("terminalCarSuccessOut.jsp --- Open Connection");
		String recorddate = request.getParameter("recorddate");
		Terminal ter = new Terminal();
		String emp = (String) session.getAttribute("username");
		ter.updateCarOut(con, emp, recorddate);
		response.getWriter().print("success");
	} finally {
		if (con != null) {
			try {
				con.close();
				System.out.println("terminalCarSuccessOut.jsp --- Close Connection");
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			} finally {
				con = null;
			}
		}
	}
%>
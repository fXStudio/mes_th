<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>

<%
	Connection con = null;
	try {
		con = new Conn_MES().getConn();
		String doornum = request.getParameter("doornum");
		String car = request.getParameter("car");
		Terminal ter = new Terminal();
		ter.updateDoorNum(con, doornum, car);
		response.getWriter().print("success");
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
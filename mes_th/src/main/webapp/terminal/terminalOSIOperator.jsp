<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>
<%@ page import="common.Conn_MES"%>

<%
	Connection con = null;
	try {
		con = new Conn_MES().getConn();
		String osin = request.getParameter("osin");
		String car = request.getParameter("car");
		Terminal ter = new Terminal();
		ter.insertOSIn(con, car, osin);
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

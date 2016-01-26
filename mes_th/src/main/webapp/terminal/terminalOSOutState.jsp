<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.qm.th.terminal.Terminal"%>

<%
	Connection con = null;
	try {
		con = new Conn_MES().getConn();
		String car = request.getParameter("car");
		Terminal ter = new Terminal();
		ter.insertOSOut_State(con, car);
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

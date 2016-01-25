<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>

<%
	Connection con = null;
	try {
		con = new Conn_MES().getConn();
		String cPageNo = request.getParameter("cPageNo");
		Terminal ter = new Terminal();
		String emp = (String) session.getAttribute("username");
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

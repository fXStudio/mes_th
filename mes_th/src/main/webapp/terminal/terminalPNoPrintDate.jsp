<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>

<%
	Connection con = null;
	try {
		con = new Conn_MES().getConn();
		String pageno = request.getParameter("pageno");
		Terminal ter = new Terminal();
		Date printdate = ter.getPrintDate(con, pageno);
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		response.getWriter().print(format.format(printdate));
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

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
		boolean flag = ter.insertCar(con, car);
		String json = "";
		if (flag == false)
			json = "{success:true,info:'数据添加失败'}";
		else
			json = "{success:true,msg:\'ok\'}";
		response.getWriter().print(json);
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
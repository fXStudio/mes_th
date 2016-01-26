<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.qm.th.helpers.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.qm.th.terminal.Terminal"%>

<%
	Connection con = null;
	try {
		con = new Conn_MES().getConn();
		String partname = request.getParameter("partname");
		String code = request.getParameter("code");
		Terminal ter = new Terminal();
		boolean flag = ter.insertPartName_Code(con, partname, code);
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

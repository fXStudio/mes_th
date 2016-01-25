<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>

<% 
	Connection con = null;
	try{
		con= new Conn_MES().getConn();
	   	String pageno = request.getParameter("cPageNo");
		Terminal ter = new Terminal();
		int num = ter.getStateExist(con, pageno);
		
		response.getWriter().print(num);
	}finally{
		
		if(con != null){
			try{
				con.close();
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}finally{
				con = null;
			}
		}
	}
%>

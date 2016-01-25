<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
 
<% 
	Connection con = null;
	try{
		
		con = new Conn_MES().getConn();
		String car = request.getParameter("car");
		Terminal ter = new Terminal();
		int num = ter.getPageNoCount(con, car);
		
		response.getWriter().print(String.valueOf(num));
	}
	finally{
		
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

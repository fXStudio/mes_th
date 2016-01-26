<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.qm.th.terminal.Terminal"%>
<%@ page import="com.qm.th.helpers.Conn_MES"%>
 
<% 
	Connection con = null;
	try{
		con= new Conn_MES().getConn();
		String pageno = request.getParameter("cPageNo");
		//String car = request.getParameter("car");
		Terminal ter = new Terminal();
		String state = ter.getPageNoState(con,pageno);
		int existnum=ter.getPageNoExist(con,pageno);
		
		response.getWriter().print(state+","+existnum);
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
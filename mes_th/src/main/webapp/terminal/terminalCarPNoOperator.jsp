<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>
<%@ page import="common.Conn_MES"%>
 
<% 
	Connection con = null;
	try{
		
		con = new Conn_MES().getConn();
		String pageno = request.getParameter("pageno");
		String car = request.getParameter("car");
		Terminal ter = new Terminal(); 
		String emp=(String)session.getAttribute("username");
		ter.insertCarPNo(con, car, pageno,emp);
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

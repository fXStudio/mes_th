<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>
<%@ page import="common.Conn_MES"%>

<% 
	Connection con = null;
	try{
		con= new Conn_MES().getConn();
	   	String pageno = request.getParameter("pageno");
		Terminal ter = new Terminal();
		String num = ter.getPageNoState(con, pageno);
		boolean flag=false;
		if(!"1".equals(num))
			flag=ter.deleteAll(con, pageno);
		
		String str="";
		if(flag)
			str="1";
		else
			str="0";
		response.getWriter().print(str);
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

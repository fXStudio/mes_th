<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
 
<% 
	Connection con = null;
	try{
		con= new Conn_MES().getConn();
		String car = request.getParameter("car");
		Terminal ter = new Terminal();
		boolean flag=ter.deleteCancelFull(con, car);
		int num = ter.getPageNoCount(con, car);
		String str="";
		if(flag)
			str="1";
		else
			str="0";
		response.getWriter().print(str+","+num);
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

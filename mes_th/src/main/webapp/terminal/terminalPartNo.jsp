<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.qm.th.terminal.Terminal"%>
 
<% 
	Connection con = null;
	try{
		con = new Conn_MES().getConn();
		String pageno = request.getParameter("cPageNo");
		String partseq = request.getParameter("partseq");
		Terminal ter = new Terminal();
		int cou=ter.getSeqRows(con,pageno,partseq);
		if(cou>0){
			response.getWriter().print("{success:false}");
		}else{
			ter.updatePartNo(con, partseq, pageno);
			response.getWriter().print("{success:true}");
		}
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
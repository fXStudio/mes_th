<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>
<%@ page import="common.Conn_MES"%>
<%@page import="db.TerminalBean"%>
 
<% 
	
	    int index = 0;
		String cPageNo = request.getParameter("cPageNo");
		Terminal ter = new Terminal();
		
		Connection con = null;
		
		try{
			con = new Conn_MES().getConn();
			index = ter.getRows(con, cPageNo);
			ArrayList<TerminalBean> list = ter.getCTFAssList(con, cPageNo);
			StringBuffer str = new StringBuffer("({items:[");
			
			for (int num = 0; num < list.size(); num++) {
				if (num > 0) {
					str.append(",");
					str.append("\""+((TerminalBean)list.get(num)).getCtfass()+"\"");
				} else
					str.append("\""+((TerminalBean)list.get(num)).getCtfass()+"\"");
			}
			str.append("],vins:[");
			for (int num = 0; num < list.size(); num++) {
				if (num > 0) {
					str.append(",");
					str.append("\""+((TerminalBean)list.get(num)).getCvincode()+"\"");
				} else
					str.append("\""+((TerminalBean)list.get(num)).getCvincode()+"\"");
			}
			str.append("], index:\"");
			str.append(index);
			str.append("\"})");
			System.out.println(str.toString());
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

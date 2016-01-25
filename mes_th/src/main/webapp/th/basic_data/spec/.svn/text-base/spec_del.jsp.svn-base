<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.SQLException"%>

<% 
      /** Ö÷¼üID */
      String id = request.getParameter("intId");
      /** Ò³Âë */
      String page_num = request.getParameter("page");
    
      Connection conn = null;
      Statement stmt = null;
      
      try{
    	  conn = new Conn_MES().getConn();
    	  stmt = conn.createStatement();
    	  stmt.execute("DELETE FROM SPECPART WHERE ID = " + id);
      }catch(Exception e){
    	  e.printStackTrace();
      }finally{
    	  // resource release
    	  if(stmt != null){
    		  try{
    			  stmt.close();
    		  }catch(SQLException e){
    			  e.printStackTrace();
    		  }finally{
    			  stmt = null;
    		  }
    	  }
    	  if(conn != null){
    		  try{
    			  conn.close();
    		  }catch(SQLException e){
    			  e.printStackTrace();
    		  }finally{
    			  conn = null;
    		  }
    	  }
      }
      response.sendRedirect("spec_manage.jsp?page=" + page_num);
%>
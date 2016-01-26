<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="com.qm.mes.framework.*,java.sql.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 
  </head>
  
  <body>
  <% 
  Connection con = null;
  try{
  	con=ConnectionPool.getConOracle();
  	MessageAdapterFactory.loadAllMessageAdapter(con);
	out.print("ok");
  }catch(Exception e){
	e.printStackTrace(); 
  }finally{
  	if(con!=null)con.close();
  }
  
  %>

  </body>
</html>

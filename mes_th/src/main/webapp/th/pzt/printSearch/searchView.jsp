<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.sql.*"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES" />

<html>
  <head>
  <meta http-equiv=content-type content="text/html;charset=GBK">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <link rel="stylesheet" type="text/css" href="../../../cssfile/css.css">
    <link rel="stylesheet" type="text/css" href="../../../cssfile/th_style.css">
	<script type="text/javascript" src="../../../JarResource/META-INF/tag/taglib_common.js"></script>		 
    <title>配货单打印</title>	
  </head>
  
  <%

     Connection con = null;
     Statement stmt = null;
     ResultSet rs = null;
 	
 	String sql="";
 	String rq=request.getParameter("rq");
 	String cjs=request.getParameter("cjs");
 	String printId=request.getParameter("id");
 %>
 <body>

      <div align="center">
      <font size="+1" >配货单查询</font> 
      </div>
      <form id="form1" name="form1" method="post" action="">
     <table width="500" border="1" align="center" height="97">
     <tr>	
    	 <td width="5%">零件</td>	
    	<td width="10%">数量</td>

     </tr>
 <%
     sql = "select ctfass ,count(ctfass) from print_data where ctfass <> 'null' and cremark='"+rq+"' and iprintgroupid='"+printId+"' "
     +"and icarno<='"+cjs+"' group by ctfass ";
     System.out.println(sql);
     try{
    	 con = Conn.getConn();
    	 stmt = con.createStatement();
     	 rs = stmt.executeQuery(sql);
		 while (rs.next())
		 {
		 	out.write("<tr>");
		 	out.write("<td>"+rs.getString(1)+"</td>");
		 	out.write("<td>"+rs.getString(2)+"</td>");
		 	out.write("</tr>");
		 }
 	 }catch(Exception e){
   		 e.printStackTrace();
 	 }finally{
  
    if(stmt!=null)
    	stmt.close();
    if(rs!=null)
    	rs.close();
    if(con!=null) 
   		con.close();
  
  }
  
   %>
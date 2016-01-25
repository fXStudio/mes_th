<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn_MES" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<%@page import="mes.util.tree.*"%>
<% 
	String userid=(String)session.getAttribute("userid");
	if(userid==null) 
	{
		out.write("·ÃÎÊ±»¾Ü¾ø£¡");
		return;
	}
	
	String cssFile =(String)session.getAttribute("file");
 	if(cssFile==null||cssFile.trim().equals("")) 
 		cssFile="blue";
	//System.out.println("4***mobile_log*");
	
	Connection con=null;
	DataServer_UserManage ds=null;
	BussinessProcess_UserManage bp=null;
	
	try{
		con=Conn_MES.getConn();
		ds = new DataServer_UserManage(con);
		String color=request.getParameter("color");
		if(color!=null)
		{
			session.setAttribute("file",color);
			cssFile=color;
			bp=new BussinessProcess_UserManage(con);
			bp.updateUserInterface(userid,color);
		}
		
		
		
		String roleno=request.getParameter("roleno");
		//String rolename=request.getParameter("rolename");
		String url=ds.getWelcomePage(roleno);
		//System.out.println("5***mobile_log*"+url);
		
%>

<html>
<link rel="stylesheet" type="text/css" href="cssfile/<%=cssFile%>.css">
<head>
<title>mes-mobile¿ò¼Ü</title>
</head>

 
  
  <frameset  id="left" border="0" >
    <frame NAME="ProductionShow" ID="ProductionShow"  src=<%=url%> >
 </frameset><noframes></noframes>
<% 
	}
	catch(Exception e)
	{
		throw e;
	}
	finally
	{
		if(con!=null)con.close();
	}
%>
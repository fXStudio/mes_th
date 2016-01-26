<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn_MES" scope="page" class="com.qm.th.helpers.Conn_MES"/>
<%@page import="com.qm.mes.util.tree.*"%>

<%
	String userid=(String)session.getAttribute("userid");
	if(userid==null) 
	{
		out.write("访问被拒绝！");
		return;
	}
	
	String cssFile =(String)session.getAttribute("file");
 	if(cssFile==null||cssFile.trim().equals("")) 
 		cssFile="blue";
	
	Connection con=null;
	DataServer_UserManage ds=null;
%>
<html>
<link href="cssfile/<%=cssFile%>.css" rel="stylesheet" type="text/css">
<head>

<TITLE>系统功能菜单</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
</head>
<style>
.treeicon{background-color:#E8F3FD};
</style>
<body bgcolor="#E8F3FD" style="padding:0px;margin:0px"> 
<link rel="stylesheet" type="text/css" href="tree.css">
<script language="javascript" src="tree.js" ></script>
<div id = "div_tree" style="padding:5px;font-size:10pt;"></div>
<%
	try
	{
		//获得连接
		con=Conn_MES.getConn();
		ds = new DataServer_UserManage(con);
		String roleno="";
		//roleno=ds.getRoleNo(userid);
		roleno=request.getParameter("roleno");
		String powerstring=ds.getPowerString(roleno);
		//BuildTree功能树的类名
		BuildTree bft = new BuildTree(con,powerstring);
		
		Function root=ds.getFuncitonInfo("1");
%>
<script type="text/javascript">
<!--
var t = mes.taglib.tree('div_tree');
//控制是否有连线=============start (在tree.js中也有设置)
t.iml.add("plus","collapse_top");
t.iml.add("plus","collapse_end");
t.iml.add("plus","collapse");

t.iml.add("minus","expand_top");
t.iml.add("minus","expand");
t.iml.add("minus","expand_end");

t.iml.add("blank","branch_end");
t.iml.add("blank","branch");
//控制是否有连线=============end

//---- 控制功能树打开与关闭时的图片样式 start----
t.iml.add("book1_open","open2");
t.iml.add("book1_close","close2");
//---- 控制功能树打开与关闭时的图片样式 end----

//link、sun是在类中固定的，都是图片名
t.iml.add('html','link');
t.iml.add("sun","sun");
//下面中间的null是链接地址，有地址的点击后显示链接地址的内容，没有则不是超连接
var node<%=root.getFunctionID()%> = t.addNode('<%=root.getFunctionName()%>',null,'open2','close2');

<%=bft.expand(root) %>
//ProductionShow是显示到右面窗口的名
t.target = "ProductionShow";
-->
</script>
<%	
	}catch(Exception e)
	{
		System.out.println(e);
		throw e;
	}
	finally
	{
		if(con!=null)con.close();
	}
%>
</body>
</html>
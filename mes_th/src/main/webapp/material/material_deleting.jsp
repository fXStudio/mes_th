<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@page import="java.util.*" %> 
<%@page import="com.qm.mes.framework.*" %>  
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>删除物料</title>

</head>
<body>
	<% 
	//获取请求参数
	String element_name = request.getParameter("element_name");
	if(element_name==null||element_name.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='material_view.jsp';
 -->
</script>
<%
		return;
	}	
	
	Connection con=null;
	ExecuteResult er=null;
	ServiceException se=null;
	
	List list=null;
	try
	{
		//获取资源
		con=Conn.getConn();
		
		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
%>
<%
		//设置用户参数
		message.setUserParameter("element_name", element_name);
		message.setOtherParameter("con", con);
		//调用soa中的流程 暂时设为1 
		//todo "58"在流程定义表中是"添加物料类型"
		er=bus.doProcess("58", message);
%>
<%
		//释放资源
		if(con!=null)con.close();
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
%>
<script>
<!-- 
alert("操作成功！");window.location.href='material_view.jsp';
 -->
</script>
<%
		}
		else
		{
			list=message.getServiceException();
			if(list==null||list.size()==0)
			{
%>
<script>
<!-- 
alert("操作失败！原因不明！请与管理员联系！");window.location.href='material_view.jsp';
 -->
</script>
<%
			}
			else
			{
				se=(ServiceException)list.get(list.size()-1);
%>
<script>
<!-- 
alert('操作失败！<%=se.getDescr().replaceAll("\n","")%>');window.location.href='material_view.jsp';
 -->
</script>
<%
			}
		}
		
	}
	catch(Exception e)
	{	
		//释放资源
		if(con!=null)con.close();
		
%>
<script>
<!--  
alert("发生异常！<%=e.toString().replaceAll("\n","")%>");window.location.href='material_view.jsp';
-->
</script>	
<%
	throw e;
	}
%>
</body>
</html>

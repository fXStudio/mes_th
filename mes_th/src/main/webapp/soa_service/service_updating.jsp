<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
<%@page import="java.util.*" %>
<%@page import="com.qm.mes.framework.*" %>

<%
	/*
	 * 业务描述：添加适配器信息，校验适配器是否重复配置由相应服务完成
	 */
	 
	//获取请求参数
	String serviceid = request.getParameter("serviceid");
	String servicename=request.getParameter("servicename");
	String classname=request.getParameter("classname");
	String servicedesc=request.getParameter("servicedesc");
	String namespace=request.getParameter("namespace");
	String intpage="";
	intpage=request.getParameter("intpage");
    String info = request.getParameter("info");
		info = info==null?"":info;
	if(serviceid==null||serviceid.trim().equals("")||servicename==null||servicename.trim().equals("")||classname==null||classname.trim().equals("")||servicedesc==null||servicedesc.trim().equals("")||namespace==null||namespace.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='service_manage.jsp?page=<%=intpage%>&eid=<%=serviceid%>&info=<%=info%>';
 -->
</script>
<%
		return;
	}
	
	Connection con=null;
	ExecuteResult er=null;
	ServiceException se=null;
//	String questiondesc="";
	
	List list=null;
	try
	{
		//获取资源
		con=Conn.getConn();
		
		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
		//设置用户参数
		message.setUserParameter("serviceid",serviceid);
		message.setUserParameter("servicename", servicename);
		message.setUserParameter("classname",classname);
		message.setUserParameter("servicedesc", servicedesc);
		message.setUserParameter("namespace", namespace);
		message.setOtherParameter("con", con);
		
		//调用soa中"更新服务定义"的流程 流程号为7
		//todo
		er=bus.doProcess("7", message);
		
		//释放资源
		if(con!=null)con.close();
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
%>
<script>
	alert("操作成功！");window.location.href='service_manage.jsp?page=<%=intpage%>&eid=<%=serviceid%>&info=<%=info%>';
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
 	alert("操作失败！原因不明！请与管理员联系！");window.location.href='service_manage.jsp?page=<%=intpage%>&eid=<%=serviceid%>&info=<%=info%>';
 </script>
<%
			}
			else
			{
				se=(ServiceException)list.get(list.size()-1);
%>
<script>
 	alert("操作失败！<%=se.getDescr()%>");window.location.href='service_manage.jsp?page=<%=intpage%>&eid=<%=serviceid%>&info=<%=info%>';
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
	alert("发生异常！<%=e.toString()%>");window.location.href='service_manage.jsp?page=<%=intpage%>&eid=<%=serviceid%>&info=<%=info%>';
</script>	
<%   
	throw e;
	}
%>
		
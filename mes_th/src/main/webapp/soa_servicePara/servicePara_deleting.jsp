<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="common.Conn"/>
<%@page import="java.util.*" %>
<%@page import="mes.framework.*" %>

<%
	/*
	 * 业务描述：添加适流程服务信息，校验适配器是否重复配置由相应服务完成
	 */
	 
	//获取请求参数
	String serviceid=request.getParameter("serviceid");
	String setparaname = request.getParameter("setparaname");
	String setparatype = request.getParameter("setparatype");
	String eid = request.getParameter("eid");
    String intpage="";
	intpage=request.getParameter("intPage");
	String info = request.getParameter("info");
		info = info==null?"":info;
	if(serviceid==null||serviceid.trim().equals("")||setparaname==null||setparaname.trim().equals("")||setparatype==null||setparatype.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='servicePara_manage.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=eid%>';
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
		message.setUserParameter("serviceid", serviceid);
		message.setUserParameter("paratype", setparatype);
		message.setUserParameter("paraname", setparaname);
		message.setOtherParameter("con", con);
		
		
		//调用soa中的"删除服务参数信息"流程 流程号为13
		//todo
		er=bus.doProcess("13", message);
		//释放资源
		if(con!=null)con.close();
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
		
%>
<script>
<!-- 
alert("操作成功！");window.location.href='servicePara_manage.jsp?page=<%=intpage%>&info=<%=info%>';
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
alert("操作失败！原因不明！请与管理员联系！");window.location.href='servicePara_manage.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=eid%>';
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
alert("操作失败！<%=se.getDescr()%>");
window.location.href="servicePara_manage.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=eid%>";
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
alert("发生异常！<%=e.toString()%>");window.location.href='servicePara_manage.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=eid%>';
-->
</script>	
<%
	throw e;
	}
%>
		


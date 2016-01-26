<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
<%@page import="java.util.*" %>
<%@page import="com.qm.mes.framework.*"%>

<%
	/*
	 * 业务描述：添加适流程服务信息，校验适配器是否重复配置由相应服务完成
	 */
	 
	//获取请求参数

	String id=request.getParameter("id");
	String intpage="";
	intpage=request.getParameter("intPage");
	String info = request.getParameter("info");
		info = info==null?"":info;
	if(	id==null||id.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='user_view.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=id%>';
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
		String processid_run = "30";
		message.setUserParameter("id", id);
		
		message.setOtherParameter("con", con);		
		//调用soa中的流程 暂时设为1 
		//todo
		er=bus.doProcess(processid_run, message);
		
		//释放资源
		if(con!=null)con.close();
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
%>
<script>
<!-- 
alert("操作成功！");window.location.href='user_view.jsp?page=<%=intpage%>&info=<%=info%>';
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
alert("操作失败！原因不明！请与管理员联系！");window.location.href='user_view.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=id%>';
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
alert("操作失败！<%=se.getDescr().replaceAll("\n","")%>");window.location.href='user_view.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=id%>';
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
alert("发生异常！<%=e.toString().replaceAll("\n","")%>");window.location.href='user_view.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=id%>';
-->
</script>	
<%
	throw e;
	}
%>
		
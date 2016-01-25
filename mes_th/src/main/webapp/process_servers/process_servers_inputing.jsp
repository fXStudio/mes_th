<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<%@page import="java.util.*" %>
<%@page import="mes.framework.*" %>

<%
	/*
	 * 业务描述：添加适流程服务信息，校验适配器是否重复配置由相应服务完成
	 */
	 
	//获取请求参数
	String processid=request.getParameter("tfNPROCESSID");
	String sid=request.getParameter("tfNSID");
	String serverid=request.getParameter("tfCSERVERID");
	String aliasname=request.getParameter("tfCALIASNAME");
	String actid=request.getParameter("tfNACTID");
	String intpage="";
	intpage=request.getParameter("intpage");
	if(processid==null||processid.trim().equals("")||sid==null||sid.trim().equals("")||serverid==null||serverid.trim().equals("")||aliasname==null||aliasname.trim().equals("")||actid==null|actid.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='process_servers_input.jsp';
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
		message.setUserParameter("processid", processid);
		message.setUserParameter("sid", sid);
		message.setUserParameter("serverid",serverid);
		message.setUserParameter("aliasname", aliasname);
		//if(o_serveralias==null)o_serveralias="";
		message.setUserParameter("actid", actid);

		
		message.setOtherParameter("con", con);
		
		//调用soa中的流程 暂时设为1 
		//todo
		er=bus.doProcess("5", message);
		
		//释放资源
		if(con!=null)con.close();
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
%>
<script>
<!-- 
alert("操作成功！");window.location.href='process_servers_view.jsp?page=<%=intpage%>';
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
alert("操作失败！原因不明！请与管理员联系！");window.location.href='process_servers_view.jsp?page=<%=intpage%>';
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
alert("操作失败！<%=se.getDescr().replaceAll("\n","")%>");window.location.href='process_servers_view.jsp?page=<%=intpage%>';
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
alert("发生异常！<%=e.toString().replaceAll("\n","")%>");window.location.href='process_servers_view.jsp?page=<%=intpage%>';
-->
</script>	
<%
	throw e;
	}
%>
		
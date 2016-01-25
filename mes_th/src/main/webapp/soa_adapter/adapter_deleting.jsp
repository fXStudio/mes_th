<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<%@page import="java.util.List"%>
<%@ page import="mes.framework.*" %>
<%
	/* 
	 * 时间：2007-06-25
	 * 作者：吕智
	 * 业务描述：删除适配器信息
	 */
	 
	//获取请求参数
	String processid=request.getParameter("processid");
	String eid=request.getParameter("eid");
	String aliasname=request.getParameter("aliasname");
	String parametername=request.getParameter("parameter");
	String intpage="";
	intpage=request.getParameter("intPage");
	String info = request.getParameter("info");
		info = info==null?"":info;
	//String eid=request.getParameter("eid");
	
	if(processid==null||processid.trim().equals("")||aliasname==null||aliasname.trim().equals("")||parametername==null||parametername.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='adapter_manage.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=eid%>';
 -->
</script>
<%
		return;
	}

	
	
	try{
		//int int_processid=
		Integer.parseInt(processid);
	}
	catch(Exception e_tran2)
	{
%>
<script>
<!-- 
alert("流程号参数不是数字");window.location.href='adapter_deleting.jsp';
 -->
</script>
<%
		return;
	}
	
	
	
	Connection con=null;
	ExecuteResult er=null;
	ServiceException se=null;
	//String questiondesc="";
	
	List list=null;
	try
	{
		//获取资源
		con=Conn.getConn();
		
		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
		//设置用户参数
		
		message.setUserParameter("processid", processid);
		message.setUserParameter("i_serveralias", aliasname);
		message.setUserParameter("i_parameter",parametername);
		
		message.setOtherParameter("con", con);
		
		//调用soa中的流程 暂时设为2
		//todo
		er=bus.doProcess("2", message);
		
		//释放资源
		if(con!=null)con.close();
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
%>
<script>
<!-- 
alert("操作成功！");window.location.href='adapter_manage.jsp?page=<%=intpage%>&info=<%=info%>';
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
alert("操作失败！原因不明！请与管理员联系！");window.location.href='adapter_manage.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=eid%>';
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
alert("操作失败！<%=se.getDescr()%>");window.location.href='adapter_manage.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=eid%>';
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
alert("发生异常！<%=e.toString()%>");window.location.href='adapter_manage.jsp?page=<%=intpage%>&info=<%=info%>&eid=<%=eid%>';
-->
</script>
<%
		throw e;
	}
%>

<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%@page import="java.util.*" %>
<%@page import="com.qm.mes.framework.*" %>
<%
	/*
	 * 业务描述：添加适配器信息，校验适配器是否重复配置由相应服务完成
	 */
	 
	//获取请求参数
	String serviceid=request.getParameter("serviceid");
    String paracount =request.getParameter("paracount");
    String intpage="";
	intpage=request.getParameter("intpage");
	if(serviceid==null||serviceid==""||paracount==null||paracount=="")
	{
%>
<script type="text/javascript">
	alert("参数为空");window.location.href='servicePara_manage.jsp?page=<%=intpage%>';
</script>		

<%
	   return;
	}
	int count =Integer.parseInt(paracount);
	Connection con=null;
	ExecuteResult er=null;
	ServiceException se=null;
//	String questiondesc="";
	boolean success_sign=true;
    List list=null;
	try
	{
	    
	    //获取资源
		con=Conn.getConn();
		con.setAutoCommit(false);
		
		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
		//设置用户参数
		message.setUserParameter("serviceid", serviceid);
		
		int i=1;
		//使用循环用于执行多次“添加服务参数”的服务
		for(i=1;i<=count;i++)
		{
		
			message.setUserParameter("paraname", request.getParameter("para"+i));
			message.setUserParameter("paratype", request.getParameter("para"+i+"_type"));
			message.setUserParameter("paradesc", request.getParameter("para"+i+"_desc"));
			message.setOtherParameter("con", con);
			
			//调用soa中添加服务参数的流程 服务号为6 
			//todo
			er=bus.doProcess("6", message);
			if(er!=ExecuteResult.sucess)
			{
				success_sign=false;
				break;
				
			}
		}
		if(success_sign)
		{
			con.commit();
			con.setAutoCommit(true);
		}else
		{
			con.rollback();
			con.setAutoCommit(true);
		}
		//释放资源
		if(con!=null)con.close();
		
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
%>
<script>
<!-- 
alert("操作成功！");window.location.href='servicePara_manage.jsp?page=<%=intpage%>';
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
alert("操作失败！原因不明！请与管理员联系！");window.location.href='servicePara_manage.jsp?page=<%=intpage%>';
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
alert("操作失败！<%=se.getDescr()%>");window.location.href='servicePara_manage.jsp?page=<%=intpage%>';
 -->
</script>
<%
			}
		}
		
	}
	catch(Exception e)
	{	
		con.rollback();
		con.setAutoCommit(true);
		//释放资源
		if(con!=null)con.close();
		
%>
<script>
<!--  
alert("发生异常！<%=e.toString()%>");window.location.href='servicePara_manage.jsp?page=<%=intpage%>';
-->
</script>	
<%
	throw e;
	}
		

		
%>
		
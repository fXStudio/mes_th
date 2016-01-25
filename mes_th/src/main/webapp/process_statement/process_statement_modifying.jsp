<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="common.Conn"/>
<%@page import="java.util.*" %>
<%@page import="mes.framework.*" %>
<%
	/*
	 * 业务描述：添加适配器信息，校验适配器是否重复配置由相应服务完成
	 */
	 
	//获取请求参数
	String processid=request.getParameter("tfNPROCESSID");
	String processname=request.getParameter("tfCPROCESSNAME");
	String description=request.getParameter("tfCDESCRIPTION");
	String namespace=request.getParameter("tfCNAMESPACE");
	String intpage="";
	intpage=request.getParameter("intpage");
	String process_info = request.getParameter("process_info");
	process_info = process_info==null?"":new String(process_info);
	
	
	if(processid==null||processid.trim().equals("")||processname==null||processname.trim().equals("")||description==null||description.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='process_statement_view.jsp?page=<%=intpage%>&eid=<%=processid%>&process_info=<%=process_info%>';
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
		message.setUserParameter("processname", processname);
		message.setUserParameter("description",description);
		message.setUserParameter("namespace", namespace);
	
		message.setOtherParameter("con", con);
		
		//调用soa中的流程 暂时设为1 
		//todo
		er=bus.doProcess("21", message);
		
		//释放资源
		if(con!=null)con.close();
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
%>
<script>
<!-- 
alert("操作成功！");window.location.href='process_statement_view.jsp?page=<%=intpage%>&eid=<%=processid%>&process_info=<%=process_info%>';
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
alert("操作失败！原因不明！请与管理员联系！");window.location.href='process_statement_view.jsp?page=<%=intpage%>&eid=<%=processid%>&process_info=<%=process_info%>';
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
alert("操作失败！<%=se.getDescr().replaceAll("\n","")%>");window.location.href='process_statement_view.jsp?page=<%=intpage%>&eid=<%=processid%>&process_info=<%=process_info%>';
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
alert("发生异常！<%=e.toString().replaceAll("\n","")%>");window.location.href='process_statement_view.jsp?page=<%=intpage%>&eid=<%=processid%>&process_info=<%=process_info%>';
-->
</script>	
<%
	throw e;
	}
%>
		
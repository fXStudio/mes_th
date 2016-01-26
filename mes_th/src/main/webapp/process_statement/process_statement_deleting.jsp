<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%@page import="java.util.*" %>
<%@page import="com.qm.mes.framework.*" %>
<%@ page import="com.qm.mes.framework.dao.*" %>
<%
	/*
	 * 业务描述：添加适流程服务信息，校验适配器是否重复配置由相应服务完成
	 */
	 
	//获取请求参数
	String processid=request.getParameter("processid");
    String intpage="";
	intpage=request.getParameter("intPage");
	String process_info = request.getParameter("process_info");
	process_info = process_info==null?"":new String(process_info);
	if(processid==null||processid.trim().equals(""))
	
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='process_statement_view.jsp?page=<%=intpage%>&process_info=<%=process_info%>&eid=<%=processid%>';
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
		//谢静天修改 2009 2月26日 目的：在删除流程时判断是否有服务 id=1
		IDAO_Core idao_core=DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(con));
		 Statement stmt = con.createStatement();
       ResultSet rs= stmt.executeQuery(idao_core.getSQl_QueryProcessandserverbynprocessid(Integer.parseInt(processid)));
		if(rs.next()==true)
		{
		out.println("<script>alert(\"该流程有服务使用，不能删除\");window.location.href='process_statement_view.jsp?page="+intpage+"&process_info="+process_info+"&eid="+processid+"';</script>");
		}
		else{
		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
		//设置用户参数
		message.setUserParameter("processid", processid);
		message.setOtherParameter("con", con);
		
		//调用soa中的流程 暂时设为1 
		//todo
		er=bus.doProcess("22", message);
		
		//释放资源
		if(con!=null)con.close();
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
%>
<script>
<!-- 
alert("操作成功！");window.location.href='process_statement_view.jsp?page=<%=intpage%>&process_info=<%=process_info%>';
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
alert("操作失败！原因不明！请与管理员联系！");window.location.href='process_statement_view.jsp?page=<%=intpage%>&process_info=<%=process_info%>&eid=<%=processid%>';
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
alert("操作失败！<%=se.getDescr().replaceAll("\n","")%>");window.location.href='process_statement_view.jsp?page=<%=intpage%>&process_info=<%=process_info%>&eid=<%=processid%>';
 -->
</script>
<%
			}
		}
		}
		rs.close();
		stmt.close();
	//谢静天 结束; id=1
	}
	catch(Exception e)
	{	
		//释放资源
		if(con!=null)con.close();
		
%>
<script>
<!--  
alert("发生异常！<%=e.toString().replaceAll("\n","")%>");window.location.href='process_statement_view.jsp?page=<%=intpage%>&process_info=<%=process_info%>&eid=<%=processid%>';
-->
</script>	
<%
	throw e;
	}
%>
		


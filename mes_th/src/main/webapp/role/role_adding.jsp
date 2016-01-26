<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*" %> 
<%@page import="com.qm.mes.framework.*" %>  

<html><!-- InstanceBegin template="/Templates/处理摸板1.dwt.jsp" codeOutsideHTMLIsLocked="true" --> 
<!-- InstanceBeginEditable name="获得连接对象" -->
		<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
		<!-- InstanceEndEditable -->
 
<%	String nextpage="_manage.jsp";
	StringBuffer result = new StringBuffer("<script>");
%>
<!-- InstanceBeginEditable name="获得参数并验证" -->
<%
	String[] chs = request.getParameterValues("chk");
   String intpage="";
	intpage=request.getParameter("intpage");
	String rolename=request.getParameter("rolename");
	String rank=request.getParameter("rank");
	String welcomepage=request.getParameter("welcomepage");
	String note=request.getParameter("note");
	
	if(chs==null)
		chs=new String[]{""};
	
	if(	rolename==null||
		rank==null||
		welcomepage==null||
		note==null){
		out.println("<script>alert(\"参数为空\");window.location.href='role_manage.jsp';</script>");
		return;
	}
	String str_ch = "";
	for(String ch:chs)
		str_ch = str_ch + ":" + ch;
	chs=null;

%>
<!-- InstanceEndEditable --><%
	String userid = (String)session.getAttribute("userid");
	
	Connection con=null;
	ExecuteResult er=null;
	ServiceException se=null;
//	String questiondesc="";
	List list=null;
	try	{
		//获取资源
		con=Conn.getConn();
		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
		String processid_run = "";
		//设置用户参数 %>	
	<!-- InstanceBeginEditable name="设置用户参数和设置要运行的流程id" -->
	<%
		nextpage="role_manage.jsp";
		processid_run = "14";
		message.setUserParameter("userid", userid);
		message.setUserParameter("rolename", rolename);
		message.setUserParameter("rank", rank);
		message.setUserParameter("welcomepage", welcomepage);
		message.setUserParameter("note", note);
		message.setUserParameter("functionids", str_ch);
		message.setOtherParameter("con", con);
	%><!-- InstanceEndEditable -->
	<%	message.setOtherParameter("con", con);
		er=bus.doProcess(processid_run, message);
		if(con!=null)con.close();//释放资源
		if(er==ExecuteResult.sucess){//对执行结果的处理
			result.append("alert(\"操作成功！\");");
			%>
		<!-- InstanceBeginEditable name="执行成功" --><%
	%><!-- InstanceEndEditable -->	
			<%
		}else{
			list=message.getServiceException();
			if(list==null||list.size()==0){
				result.append("alert(\"操作失败！原因不明！请与管理员联系！\");");
			}else{
				se=(ServiceException)list.get(list.size()-1);
				result.append("alert(\"操作失败！"+se.getDescr().replaceAll("\n","")+"\");");
			}
		}
		result.append("window.location.href='"+nextpage+"?page="+ intpage+"'; ");
		result.append("</script>");
		out.println(result.toString());
	}catch(Exception e){	
		if(con!=null)con.close();//释放资源
		throw e;
	}
%>

<!-- InstanceEnd --></html>
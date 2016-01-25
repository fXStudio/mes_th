<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*" %> 
<%@page import="mes.framework.*" %>
<%@ page import="mes.framework.dao.*" %>  

<html><!-- InstanceBegin template="/Templates/处理摸板1.dwt.jsp" codeOutsideHTMLIsLocked="true" --> 
<!-- InstanceBeginEditable name="获得连接对象" -->
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
<!-- InstanceEndEditable -->
 
<%	String nextpage="_manage.jsp";
	StringBuffer result = new StringBuffer("<script>");
%>
<!-- InstanceBeginEditable name="获得参数并验证" -->
<%	nextpage="role_manage.jsp";
	String roleno=request.getParameter("roleno");
	String intpage="";
	intpage=request.getParameter("intPage");
	String info = request.getParameter("info");
		info = info==null?"":info;
	if(roleno==null||roleno.trim().equals("")){
	out.println("<script>alert(\"参数为空\");window.location.href='"+nextpage+"?page="+intpage+"&info="+info+"&eid="+roleno+"';</script>");
		return;
	}
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
		//谢静天修改 id=1 时间：2009 2月25日 目的：当用户使用该角色时不允许删除
		  IDAO_UserManager daor = DAOFactory_UserManager
		.getInstance(DataBaseType.getDataBaseType(con));
       Statement stmt = con.createStatement();
       ResultSet rs= stmt.executeQuery(daor.getSQl_QueryUserByrole(Integer.parseInt(roleno)));
		if(rs.next()==true)
		{
		out.println("<script>alert(\"有用户使用该角色，不能删除\");window.location.href='"+nextpage+"?page="+intpage+"&info="+info+"&eid="+roleno+"';</script>");
		}
		else
		{
		
		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
		String processid_run = "";
		//设置用户参数 %>	
	<!-- InstanceBeginEditable name="设置用户参数和设置要运行的流程id" -->
	<%
		processid_run = "24";
		message.setUserParameter("userid", userid);
		message.setUserParameter("roleno", roleno);
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
		result.append("window.location.href='"+nextpage+"?page="+ intpage+"&info="+info+"&eid="+roleno+"'; ");
		result.append("</script>");
		out.println(result.toString());
		}
		rs.close();
		stmt.close();
		//谢静天 结束; id=1
		
	}catch(Exception e){	
		if(con!=null)con.close();//释放资源
		throw e;
	}
%>

<!-- InstanceEnd --></html>
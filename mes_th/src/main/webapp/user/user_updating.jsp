<%@ page language="java" import="java.sql.*"
	contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES" />
<%@page import="common.*"%>
<%@page import="java.util.*"%>
<%@page import="mes.framework.*"%>
<%@ include file="security.jsp"%>

<%
	 /*
	 * 业务描述：添加适流程服务信息，校验适配器是否重复配置由相应服务完成
	 */

	MD5 m = new MD5();

	//来自安全页的请求,更改用户的用户的id
	String lastupdateuser = userid;
	//更改用户的时间：在service中设定；
	String lastupdatetime = "1";
	String enabled = "1";
    String intpage="";
	intpage=request.getParameter("intpage");
	String eid=request.getParameter("eid");
	//获取请求参数
	String usrno = request.getParameter("usrno");
	String usrname = request.getParameter("usrname");
	String password = request.getParameter("password");
	String employeeid = request.getParameter("employeeid");
	if (employeeid == null)
		employeeid = "";
	String state = request.getParameter("state");
	String note = request.getParameter("note");
	String default_roleno = request.getParameter("default_roleno");
	String old_default_roleno = request
			.getParameter("old_default_roleno");
	String haveRoleno = request.getParameter("haveRoleNo");
	String[] roleno = request.getParameterValues("roleno");
	String info = request.getParameter("info");
		info = info==null?"":info;

	if (roleno == null)
		roleno = new String[] { "" };

	if (usrno == null || usrno.trim().equals("") || usrname == null
			|| usrname.trim().equals("") || password == null
			|| password.trim().equals("") || employeeid == null
			|| employeeid.trim().equals("") || state == null
			|| state.trim().equals("") || note == null
			|| note.trim().equals("") || default_roleno == null
			|| default_roleno.equals("") ||  roleno == null
			|| haveRoleno == null || haveRoleno.trim().equals("")) {
			
%>
<script>
<!-- 

alert("参数为空");window.location.href='user_view.jsp?page=<%=intpage%>&eid=<%=eid%>&usrno=<%=usrno%>&info=<%=info%>';
 -->
</script>
<%
	return;
	}

	Connection con = null;
	String roleid = "";
	String str_default = "false";
	for (String i : roleno) {
		roleid = roleid + ":" + i;
		if(default_roleno.equals(i)){
		    str_default = "true";
		}
	}
	if(str_default.equals("false")){
%>
<script>
<!-- 
alert("默认角色不是已选中角色!!");window.location.href='user_view.jsp?page=<%=intpage%>&eid=<%=eid%>&usrno=<%=usrno%>&info=<%=info%>';
 -->
</script>
<%
	    return;
	}
	roleno = null;

	ExecuteResult er = null;
	ServiceException se = null;
	//	String questiondesc="";

	if (!password.equals("********"))
		password = m.getMD5ofStr(password);

	List list = null;
	try {
		//获取资源
		con = Conn.getConn();

		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
		//设置用户参数
		String processid_run = "28";
		message.setUserParameter("usrno", usrno);
		message.setUserParameter("usrname", usrname);
		message.setUserParameter("password", password);
		message.setUserParameter("employeeid", employeeid);
		message.setUserParameter("state", state);
		message.setUserParameter("note", note);
		message.setUserParameter("default_roleno", default_roleno);
		message.setUserParameter("old_default_roleno",
		old_default_roleno);
		message.setUserParameter("roleno", roleid);
		message.setUserParameter("oldRoleno", haveRoleno);

		message.setUserParameter("lastupdateuser", lastupdateuser); //待用 暂时为1
		message.setUserParameter("lastupdatetime", lastupdatetime); //无用 取自oracle服务器
		message.setUserParameter("enabled", enabled);//常量

		message.setOtherParameter("con", con);

		//调用soa中的流程 暂时设为1 
		//todo
		er = bus.doProcess(processid_run, message);

		//释放资源
		if (con != null)
			con.close();

		//对执行结果的处理
		if (er == ExecuteResult.sucess) {
%>
<script>
<!-- 
alert("操作成功！");window.location.href='user_view.jsp?page=<%=intpage%>&eid=<%=eid%>&usrno=<%=usrno%>&info=<%=info%>';
 -->
</script>
<%
			} else {
			list = message.getServiceException();
			if (list == null || list.size() == 0) {
%>
<script>
<!-- 
alert("操作失败！原因不明！请与管理员联系！");window.location.href='user_view.jsp?page=<%=intpage%>&eid=<%=eid%>&usrno=<%=usrno%>&info=<%=info%>';
 -->
</script>
<%
		} else {
		se = (ServiceException) list.get(list.size() - 1);
%>
<script>
<!-- 
alert("操作失败！<%=se.getDescr().replaceAll("\n", "")%>");window.location.href='user_view.jsp?page=<%=intpage%>&eid=<%=eid%>&usrno=<%=usrno%>&info=<%=info%>';
 -->
</script>
<%
		}
		}

	} catch (Exception e) {
		//释放资源
		if (con != null)
			con.close();
%>
<script>
<!--  
alert("发生异常！<%=e.toString().replaceAll("\n", "")%>");window.location.href='user_view.jsp?page=<%=intpage%>&eid=<%=eid%>&usrno=<%=usrno%>&info=<%=info%>';
-->
</script>
<%
	throw e;
	}
%>

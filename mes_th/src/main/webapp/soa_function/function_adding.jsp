<%@ page language="java" import="java.sql.*"
	contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*,java.util.regex.Pattern"%>
<%@page import="com.qm.mes.framework.*"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES" />

<%
	 /*
	 * 业务描述：添加适配器信息，校验适配器是否重复配置由相应服务完成
	 */
	//获取请求参数
	String functionname = request.getParameter("functionname");
    String intpage="";
	intpage=request.getParameter("intpage");
	String rank = request.getParameter("rank");
	String nodetype = request.getParameter("nodetype");
	String url = request.getParameter("url");
	String upnodeid = request.getParameter("upnodeid");
	String state = request.getParameter("state");
	String safemarkcode = request.getParameter("safemarkcode");
	String note = request.getParameter("note");
	String enable = "1";
	String userid = (String) session.getAttribute("userid");
	String flo_Order = request.getParameter("flo_Order");
	if(flo_Order==null||flo_Order.equals(""))
		flo_Order="0";

	if ((nodetype != null) && (nodetype.equals("2")))//节点
	{
		url = "";
		state = "1";
		safemarkcode = "";
		rank = "1";
	}

	/*System.out.println("begin");
	System.out.println("functionname:"+functionname);
	System.out.println("rank:"+rank);
	System.out.println("nodetype:"+nodetype);
	System.out.println("url:"+url);
	System.out.println("upnodeid:"+upnodeid);
	System.out.println("state:"+state);
	System.out.println("safemarkcode:"+safemarkcode);
	System.out.println("note:"+note);
	System.out.println(enable);
	System.out.println("d:"+userid);
	System.out.println("end");*/

	if (functionname == null || functionname.trim().equals("")
			|| nodetype == null || nodetype.trim().equals("")
			|| upnodeid == null || upnodeid.trim().equals("")
			|| note == null
			|| rank == null || url == null || safemarkcode == null
			|| state == null || rank == null || note == null) {
%>
<script type="text/javascript">
	alert("参数为空");window.location.href="function_manage.jsp";
</script>
<%
	return;
	}
	//String str="\d*\.\d{2}|\d*";
	boolean b = Pattern.compile("^[\\d]{1,5}(\\.[\\d]{1,3}$|$)").matcher(flo_Order).find();
	if(!b){
	%>
<script type="text/javascript">
	alert("功能顺序号应为Float浮点类型数,如2,12.5!");window.location.href="function_manage.jsp?page=<%=intpage%>';
</script>

 	<%
 	}
	Connection con = null;
	ExecuteResult er = null;
	ServiceException se = null;
	//	String questiondesc="";

	List<?> list = null;
	try {
		//获取资源
		con = Conn.getConn();

		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
		//设置用户参数
		message.setOtherParameter("con", con);
		message.setUserParameter("functionname", functionname);
		message.setUserParameter("nodetype", nodetype);
		message.setUserParameter("url", url);
		message.setUserParameter("upfunctionid", upnodeid);
		message.setUserParameter("safemarkcode", safemarkcode);
		message.setUserParameter("note", note);
		message.setUserParameter("state", state);
		message.setUserParameter("rank", rank);
		message.setUserParameter("userid", userid);
		message.setUserParameter("enable", enable);
		message.setUserParameter("flo_Order",flo_Order);

		//调用soa中的"删除功能"流程 流程号为29
		//todo
		er = bus.doProcess("29", message);
		//释放资源
		if (con != null)
			con.close();

		//对执行结果的处理
		if (er == ExecuteResult.sucess) {
%>
<script>
<!-- 
alert("操作成功！");window.location.href='function_manage.jsp?page=<%=intpage%>';
 -->
</script>
<%
			} else {
			list = message.getServiceException();
			if (list == null || list.size() == 0) {
%>
<script>
<!-- 
alert("操作失败！原因不明！请与管理员联系！");window.location.href='function_manage.jsp?page=<%=intpage%>';
 -->
</script>
<%
		} else {
		se = (ServiceException) list.get(list.size() - 1);
%>
<script>
<!-- 
alert("操作失败！<%=se.getSource()%>");
window.location.href="function_manage.jsp?page=<%=intpage%>';
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
alert("发生异常！<%=e.toString()%>");window.location.href='function_manage.jsp?page=<%=intpage%>';
-->
</script>
<%
	throw e;
	}
%>






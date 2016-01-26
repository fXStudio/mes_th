<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,java.util.regex.Pattern,com.qm.mes.system.dao.*" errorPage="" %>
<%@page import="java.util.*" %> 
<%@page import="com.qm.mes.framework.*" %>  
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>添加物料标识</title>
</head>
<body>
<%
	   String userid=(String)session.getAttribute("userid");	
	%>
	<% 
	//获取请求参数
	String element_name = request.getParameter("element_name");
	String codelength = request.getParameter("codelength");
	String description = request.getParameter("description");
	userid = userid==null?"-1":userid;   //登陆用户id
	description = description==null?"":description;
	if(codelength==null||codelength.trim().equals("")||userid==null||userid.trim().equals("")||element_name==null||element_name.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='materialidentify_insert.jsp';
 -->
</script>
<%
		return;
	}	
	String a[]=new String[element_name.length()];
	int sum=0;
	for(int i=0;i<element_name.length();i++){
		int j = i+1;
		a[i]=element_name.substring(i,j );
		boolean hanzi=Pattern.compile("[\u4e00-\u9fa5]").matcher(a[i]).find();
		boolean shuzi = Pattern.compile("[a-zA-Z_0-9]").matcher(a[i]).find();
		if(true==hanzi){
			sum = sum+2;
		}else if(true==shuzi){
			sum = sum+1;
		}
	}
		if(sum>40)
	{
%>
<script>
<!-- 
alert("参数值最多40个字符,每汉字占2个字符");window.location.href='materialidentify_insert.jsp';
 -->
</script>
<%
		return;
	}
	Connection con=null;
	ExecuteResult er=null;
	ServiceException se=null;
	
	List list=null;
	try
	{
		//获取资源
		con=Conn.getConn();
		
		IDAO_Element dao = (IDAO_Element) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Element.class);
		String sql=dao.getSQL_queryElementCount(element_name);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		String count = null;
		while(rs.next()){
		count = rs.getString(1); 
		}
		if(count.equals("1")){
		%>
<script>
<!-- 
alert("参数名已经存在,请重新输入!");window.location.href='materialidentify_insert.jsp';
 -->
</script>
		<%
			return;
		}
		
		IServiceBus bus = ServiceBusFactory.getInstance();
		IMessage message = MessageFactory.createInstance();
%>
<%
		//设置用户参数
		message.setUserParameter("element_name", element_name);
		message.setUserParameter("codelength", codelength);
		message.setUserParameter("description",description);
		message.setUserParameter("userid",userid);
		message.setOtherParameter("con", con);
		//调用soa中的流程 暂时设为1 
		//todo "50"在流程定义表中是"添加物料标识"
		stmt.close();
		er=bus.doProcess("50", message);
%>
<%
		//释放资源
		if(con!=null)con.close();
		
		//对执行结果的处理
		if(er==ExecuteResult.sucess)
		{
%>
<script>
<!-- 
alert("操作成功！");window.location.href='materialidentify_view.jsp';
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
alert("操作失败！原因不明！请与管理员联系！");window.location.href='materialidentify_insert.jsp';
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
alert('操作失败！<%=se.getDescr().replaceAll("\n","")%>');window.location.href='materialidentify_insert.jsp';
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
alert("发生异常！<%=e.toString().replaceAll("\n","")%>");window.location.href='materialidentify_insert.jsp';
-->
</script>	
<%
	throw e;
	}
%>
</body>
</html>

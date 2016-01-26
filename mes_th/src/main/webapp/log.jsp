<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn_MES" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%@page import="com.qm.mes.util.tree.*"%> 
<%@page import="com.qm.th.helper.StringHelper"%> 

<%
	//获取参数
	//获取用户名密码
	String username="",password="",userid="";
	username = request.getParameter("name").trim();
	password = request.getParameter("pass").trim();
	
	//获取登录日期
	String logindate=request.getParameter("dateInput");
	if(username==null||password==null||logindate==null||username.trim().equals("")||password.trim().equals("")||logindate.trim().equals(""))
	{
%>
<script language="JavaScript">
<!-- 
     alert("参数为空!");window.location.href='index.jsp';
 -->
</script>
<%
		return;
	}
	
	
	//获取连接
	Connection con=null;
	DataServer_UserManage ds=null;
	AccessCtrl ac=null;
	
	try
	{
		if(Conn_MES==null)
		{
%>
<script language="JavaScript">
<!-- 
     alert("连接失效！");window.location.href='index.jsp';
 -->
</script>
<%
			return;
		}

        username=StringHelper.clearSingleQuotationMarksFlaw(username);
        password=StringHelper.clearSingleQuotationMarksFlaw(password);
		
		con=Conn_MES.getConn();
		ds = new DataServer_UserManage (con);
		ac=new AccessCtrl(con);
		
		//验证用户
		if(ac.userProof(username,password))
		{
			userid=ds.getUserID(username);
			session.setAttribute("userid",userid);
			session.setAttribute("username",username);
			session.setAttribute("logindate",logindate);
			
			//获取该用户的样式
			String cssfile=ds.getCssFile(userid);
			session.setAttribute("file",cssfile);
			//分解成年月日
			String year=logindate.substring(0,4);
			String month=logindate.substring(4,6);
			String day=logindate.substring(6);
			
			session.setMaxInactiveInterval(-1);
			
			String url="log4.jsp?year="+year+"&month="+month+"&day="+day;
			
			
			
			if(ds.getParameterValue("fullscreen").equals("1"))
			{
%>

<script language="JavaScript">
<!--
    window.open('<%=url%>','','scrollbars=0,fullscreen=yes');
    window.opener=null;
    window.close();
 -->
</script>
<%
			}
			else
			{
				response.sendRedirect(url);
			}
		}
		else
		{
			String message=ac.getMessage().replaceAll("\"","'").replaceAll("\n","");
%>
<script language="JavaScript">
<!-- 
     alert("<%=message%>");window.location.href='index.jsp';
 -->
</script>
<%
			return;
		}

	}catch(Exception e)
	{
		throw e;
	}finally{
		if(con!=null)con.close();
	}
%>

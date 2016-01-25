<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn_MES" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<%@page import="java.util.Vector"%>
<%@page import="mes.util.tree.*"  %>
<%@page 
	import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>log4.jsp</title>
  	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <%
  final Log log = LogFactory.getLog("log4_terminal.jsp");
  if(request.getParameter("roleno")!=null){
	String roleno=request.getParameter("roleno");
	String rolename=request.getParameter("rolename");
	Connection con=null;
	Statement stmt=null;
	DataServer_UserManage ds=null;
	//BussinessProcess_UserManage bp=null;
	try{
	con=Conn_MES.getConn();
	String userid = (String)session.getAttribute("userid");
	if(userid==null) 
	{
		out.write("访问被拒绝！");
		return;
	}
	String sql="update DATA_USER set NROLENO="+roleno+" where nusrno="+userid;
	//System.out.println(con);
	ds = new DataServer_UserManage (con);
	//System.out.println("ds:"+ds);
	stmt=con.createStatement();
	stmt.executeUpdate(sql);
	String user_rolerank="";
	user_rolerank=ds.getRank(roleno);
	session.setAttribute("user_rolerank",user_rolerank);
	Vector<String> v_safemark=null;
	String powerstring=ds.getPowerString(roleno);
	v_safemark=ds.getVSafeMark(powerstring);
	session.setAttribute("safemark",v_safemark);
	String url=ds.getWelcomePage(roleno);
	log.info("连接为："+url);
	response.sendRedirect(url+"?roleno="+roleno+"&rolename="+rolename); 
	
	}catch(Exception e)
	{
		//System.out.println("Catch");	
		throw e;
	}finally{
		if(stmt!=null)stmt.close();
		if(con!=null)con.close();
	}
	return;
  }
  
  if(session.getAttribute("userid")!=null){
  	%>
<div align=center style="display:none;">	
	<div style=" background-color:#6699FF;width:450px;height:250px; margin:0 auto;border : medium solid ;border-color : buttonface; ">
	<br>
	<h3>请选择角色，系统将在30秒后进行跳转</h3><br>
	<span id="BackSecs" style="color: #F00;"></span>秒
	<FORM name="tform" METHOD=POST ACTION="log4_terminal.jsp">
		<INPUT TYPE="hidden"  NAME="rolename" id="rolename">
		<SELECT id="roleno" NAME="roleno" onChange="setRolename(this)">
		<%
			Connection con=null;
			Statement stmt=null;
			ResultSet rs = null;

			String sql="select DATA_USER_ROLE.NROLENO,DATA_USER_ROLE.NUSRNO,DATA_USER_ROLE.CDEFAULT,DATA_ROLE.CROLENAME from DATA_USER_ROLE,DATA_ROLE where DATA_ROLE.NROLENO=DATA_USER_ROLE.NROLENO and DATA_USER_ROLE.NUSRNO="+(String)session.getAttribute("userid")+" order by DATA_USER_ROLE.CDEFAULT";
			
			try{
				con=Conn_MES.getConn();
				stmt=con.createStatement();
				rs=stmt.executeQuery(sql);
				/*
					根据智能终端修正＿不支持document.getElementById相关方法
				while(rs.next()){
					out.print("<OPTION VALUE='"+rs.getObject(1)+"'>"+rs.getString(4)+"</OPTION>");
				}
				*/
				if(rs.next()){
					response.sendRedirect("log4_terminal.jsp?roleno="+rs.getObject(1)+"&rolename="+rs.getString(4));
					return;
				}else{
					session.removeAttribute("userid");
					response.sendRedirect("log4_terminal.jsp");
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					rs.close();rs=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					stmt.close();stmt=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					con.close();con=null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		
		
		
		%>
			
		</SELECT>
		<INPUT TYPE="submit" value="确认">
	</FORM>
	</div>
</div>

	<SCRIPT LANGUAGE="JavaScript">
	<!--
		function setRolename(obj){
			document.getElementById("rolename").value=obj.options[obj.selectedIndex].text;

		}
		function countDown(Secs) {
			BackSecs.innerText=Secs;
			if(Secs>0) {
			setTimeout("countDown("+Secs+"-1)",1000);
			}
			else {
				document.tform.submit();
			}
		}
		setRolename(document.getElementById("roleno"));
		countDown(0);
	//-->
	</SCRIPT>

	<%
  	
  }else{
  %>
	
	<script language="JavaScript">
		<!-- 
    	 alert("请从新登陆");window.location.href='index_terminal.jsp';
 		-->
	</script>
  
  <%
  }
  
  %>
  <%System.out.println("test23_"); %>
  </body>
</html>

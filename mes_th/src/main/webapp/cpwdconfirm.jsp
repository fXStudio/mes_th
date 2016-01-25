<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn_MES" scope="page" class="common.Conn_MES"/>
<%@page import="common.*"%>
<%
	 /* 
	 * 时间：2007-07-5
	 * 作者：吕智
	 * 业务描述：更新用户密码
	 */
	 
	String userid=(String)session.getAttribute("userid");
	if(userid==null) 
	{
		out.write("访问被拒绝！");
		return;
	}
	//获取请求参数
	String username=(String)session.getAttribute("username");
	String password=request.getParameter("txt_password");
	String newpassword=request.getParameter("txt_newpassword");
	
	newpassword=Security.clearSingleQuotationMarksFlaw(newpassword);
	//System.out.println(newpassword);
	
	Connection con=null;
	Statement stmt=null;
	
	if(newpassword==null||newpassword.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='changepassword.jsp';
 -->
</script>
<%
		return;
	}
	
	try
	{
		//获取资源
		con=Conn_MES.getConn();
		stmt = con.createStatement();
		MD5 md5=new MD5();
		String dataPassword = "";
		
		String msql = "select cpassword from data_user where cusrname='"+username+"'";
		ResultSet rs = stmt.executeQuery(msql);
		if(rs.next()){
			dataPassword = rs.getString(1);
		}
		if(!md5.getMD5ofStr(password).equals(dataPassword)){
%>
<script>
<!-- 
alert("原密码输入不正确");window.location.href='changepassword.jsp';
 -->
</script>
<%
		return;
	}	
		String sql = "update data_user set CPASSWORD='" + md5.getMD5ofStr(newpassword) + "' where NUSRNO=" + userid+"";
		stmt.executeUpdate(sql);
%>
<script>
<!-- 
alert("操作成功！");window.location.href='body.jsp';
 -->
</script>
<%
		
	}
	catch(Exception e)
	{	
%>
<script>
<!--  
alert("发生异常！<%=e.toString()%>");window.location.href='changepassword.jsp';
-->
</script>	
<%
		throw e;
	}finally{
		//释放资源
		if(stmt!=null)stmt.close();
		if(con!=null)con.close();
	}
%>
		
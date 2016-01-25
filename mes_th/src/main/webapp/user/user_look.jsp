<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<%//@page import="tree.*"%>
<%@page import="mes.framework.dao.*"%>
<%@page import="mes.framework.*"%>
<jsp:directive.page import="mes.beans.Role"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<%@ include file="security.jsp"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
<div align="center"><strong>查看用户信息</strong></div>
</head>
 
<body >
<div  align="center">
<br>
<br>
<%
	String id="";
	String intpage="";
	intpage=request.getParameter("intPage");
	String info = request.getParameter("info");
		info = info==null?"":info;
	if((request.getParameter("id")==null))
	{
%>
	<script>alert("没有此用户！");window.history.go(-1);</script>
<%
		return;
	}
	else
	{
 		id=request.getParameter("id"); 
	}
	
	String user_rolerank="";
	user_rolerank=(String)session.getAttribute("user_rolerank");
	//临时使用
	//user_rolerank="1";
	
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	try{
	conn=Conn.getConn();
	stmt=conn.createStatement();
	

	String usrno="",usrname="",employeeid="",roleno="",default_roleno="",state="",lastupdateuser="",lastupdatetime="",note="";

/*

	String sql="select usrno,usrname,employeeid,u.roleno,u.state,u.lastupdateuser,to_char(u.lastupdatetime,'yyyy-mm-dd HH24:MI:ss'),u.note ";
	sql+=" from data_user u,data_role r ";
	sql+=" where usrno='"+id+"' and u.roleno=r.roleno and to_number(r.rank)>="+user_rolerank+"";
*/



	String sql="select NUSRNO, CUSRNAME, NEMPLOYEEID, NROLENO, CSTATE, NLASTUPDATEUSER, DLASTUPDATETIME, CNOTE ";
 

	sql+=" from data_user u ";
	sql+=" where NUSRNO ='"+id+"' ";
	//IDaoUser myDao=DaoUserFactory.getInstance(2);
	IDAO_UserManager myDao = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(conn));
	//String sql=myDao.getSQL_QueryUserRoleInfoForRank(id,user_rolerank);
		
	rs=stmt.executeQuery(sql);
	if(rs.next())
	{
		usrno=rs.getString(1);
		usrname=rs.getString(2);
		employeeid=rs.getString(3);
		if(employeeid==null)
		   employeeid="";
		state=rs.getString(5);
		lastupdateuser=rs.getString(6);
		lastupdatetime=rs.getString(7);
		note=rs.getString(8);
		if(note==null)note=" 无需说明 ";
	}
	else
	{
		//if(rs!=null)rs.close();
		//if(stmt!=null)stmt.close();
		//if(conn!=null)conn.close();

%>
	<script>alert("没有此用户信息!");window.location.href='user_view.jsp';</script>
<%
		return;
	}
	String sql_DataUserRole = myDao.getSQL_selectDataUserRole(new Integer(usrno));
	List<Role> listRoleNo = new ArrayList<Role>();
	rs=stmt.executeQuery(sql_DataUserRole);
	while(rs.next()){
       Role r = new Role();
       r.setRole_no(String.valueOf(rs.getInt(2)));
       r.setCenabled(rs.getString(3));
       listRoleNo.add(r); 
	}
%>
<div align="center">
  <table class="tbnoborder" border="1">
    <tr>
        <td width="150" >用户代码<font color="#ff0000">*</font>：</td>
        <td width="300" ><%=usrno%></td>
   </tr>
   <tr>
        <td >用户名称：</td>
        <td><%=usrname%></td>   
   </tr>

   <tr>
        <td >员工号：</td>
    <td ><%=employeeid%></tr>
   <tr>
        <td >所属角色：</td>
        <td>
<%  
	String rolename="";
	//sql="select crolename from data_role where nroleno='"+roleno+"'";
	for(int i=0;i<listRoleNo.size();i++){
	Role ro = (Role)listRoleNo.get(i);
	roleno = ro.getRole_no();
	if(ro.getCenabled().equals("0")){
	default_roleno=ro.getRole_no();
	}
	sql=myDao.getSQL_QueryRoleForRoleNo(roleno);
	rs=stmt.executeQuery(sql);
	if(rs.next())
	{
		rolename=rs.getString(2);
	}
	%>
	<%=i%>、&nbsp;<%=rolename%><br/>
<%	
	}
%>
      </td>
   </tr>
   
      <tr>
        <td >默认角色：</td>
        <td>
<%  
	sql=myDao.getSQL_QueryRoleForRoleNo(default_roleno);
	rs=stmt.executeQuery(sql);
	if(rs.next())
	{
		rolename=rs.getString(2);
	}
	%>
	<%=rolename%>
      </td>
   </tr>
   
   <tr>
		<td >状态:</td>
        <td ><% if(state.equals("1")) out.write("正常");else out.write("停用");%>   </tr>
   
<%
  	 if(user_rolerank.equals("0"))
   	{
		//sql="select cusrname from data_user where nusrno="+lastupdateuser;
		sql=myDao.getSQL_QueryUserInfoForUserID(lastupdateuser);
		rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				usrname=rs.getString(2);
			}
			else
			{
				usrname="用户名不存在";
			}
%>
      <tr>
		<td >管理维护员:</td>
		
        <td ><%=usrname%>   </tr>
      <tr>
		<td >维护时间:</td>
        <td><%=lastupdatetime%>   </tr>
<%
	}
%>
      <tr>
		<td >备注:</td>
        <td><%=note%></tr>
   <tr>
      <td colspan="2" align=center>
      <!-- 
      <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onMouseOut ="this.className='btn2_mouseout'" >
         <input type="button" class="btn2" value="返 回" name="B1" onclick="window.location.href='user_view.jsp'">
      </span>
       -->
      <mes:button id="B1" reSourceURL="../JarResource/" onclick="func()" value="返 回"/>
      </td>
    </tr>
  </table>
</div>
</body>
<script>
		<!--
			function func(){
				var pageIndex = <%=intpage%>;
				var int_id=<%=id%>;		
				window.location.href = 'user_view.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';							
			}
		-->
	</script>
</html>
<%
	}catch(Exception e)
	{
		throw e;
	}finally{
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (conn != null) conn.close();
	}
%>







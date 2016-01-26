<%@ page language="java" import="java.sql.*,com.qm.mes.beans.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page import="com.qm.mes.framework.dao.*"%>
<%@page import="com.qm.mes.framework.*"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.List,java.util.ArrayList" %>
<%//@page import="tree.*"%>
<%@ include file="security.jsp" %>
<%

	ResultSet rs=null;
	ResultSet rs2=null;
	Connection con=null;
	Statement stmt=null;
	Statement stmt2=null;
	String user_rolerank="";
	user_rolerank=(String )session.getAttribute("user_rolerank");
	String intpage=request.getParameter("intPage");	
	String info = request.getParameter("info");
		info = info==null?"":info;    
	//user_rolerank="0";
	try
	{
	con=Conn.getConn();
	stmt=con.createStatement();
	stmt2=con.createStatement();

	int maxid = 0;
	
	String  sql="";
	//获得用户id的最大值；在服务中已经修改，maxid只是为了显示
	//IDaoUser sqlDao=DaoUserFactory.getInstance(2);
	IDAO_UserManager sqlDao = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
	sql=sqlDao.getSQL_QueryMaxusrno();
	//String sql="select max(to_number(usrno)) from data_user";
	rs=stmt.executeQuery(sql);
	if(rs.next())
	{
		maxid=rs.getInt(1)+1;
	}
	java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
	map.put("正常","1");
	map.put("停用","0");
	sql=sqlDao.getSQl_QueryRoleForRank(user_rolerank);
	rs=stmt.executeQuery(sql);
	java.util.HashMap<Comparable,String> map2 = new java.util.HashMap<Comparable,String>();
	List<Role> listRole = new ArrayList<Role>();
	while(rs.next())
	{
	  Role r = new Role();
	  r.setRole_no(rs.getString(1));
	  r.setRole_name(rs.getString(2));
	  listRole.add(r);
	  map2.put(rs.getString(2),rs.getString(1));
	}
%>


<html>
<head>

<title>添加新记录</title>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>
<div valign=center>
<form name=form1 method = get action="user_adding.jsp" onSubmit="return check(form1)">
  <input type="hidden" name = "usrno" value = "<%=maxid%>">
  <table class="tbnoborder" align=center border="1" width="560" cellpadding="10" cellspacing="1" height="100">
  	<caption>添加用户信息</caption>
  	<tr>
		<td >
		用户名称：
		</td>
        <td  >
		<mes:inputbox id="usrname" name="usrname" size="36"  maxlength="30" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>     
    </tr>
    <tr>
		<td >
		员工号：
		</td>
        <td  >
		<mes:inputbox id="employeeid" name="employeeid" size="36" maxlength="10" value="1" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>     
    </tr>
    <tr>
    	<td >
		密码：
		</td>
        <td  >
		<input type="password" class="box1" name="password" size="55" maxlength="15" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
		</td>     
    </tr>
	    <tr>
    	<td >
		确认密码：
		</td>
        <td  >
		<input type="password" class="box1" name="password2" size="55" maxlength="15" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
		</td>     
    </tr>
    <tr>
    	<td >
		状态：
		</td>
        <td  >
         <mes:selectbox colorstyle="box_black" id="selectbox1" name="state" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="2" value=""/>
		</td>     
    </tr>
    <tr>
    	<td >
		所属角色：
		</td>
        <td >
		 <%for(int i=0;i<listRole.size();i++){ 
		 Role role = new Role();
		 role=(Role)listRole.get(i);
		 %>
		<input id="roleno" name="roleno" type="checkbox" value="<%=role.getRole_no() %>"><%=role.getRole_name()%>
		<br/>
		<%} %>
		</td>     
    </tr>
   <tr>
    	<td >
		默认角色：
		</td>
        <td >
        <mes:selectbox colorstyle="box_black" id="selectbox2" name="default_roleno" map="<%=map2%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value=""/>
		</td>     
    </tr>
   
    <tr>
    	<td >备注：</td>
        <td >
		<mes:inputbox id="note" name="note" maxlength="100" size="36" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>     
    </tr>
    <input type = "hidden" name="intpage" value="<%=intpage%>">
    <tr>
    <td  class="tdnoborder">
      <mes:button id="button1" reSourceURL="../JarResource/"  submit="true" value="添加"/>
    </td>
    
    <td  class="tdnoborder">
      <mes:button id="button2" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重置"/>
    </td>
   
    <td  class="tdnoborder">
        <mes:button id="button3" reSourceURL="../JarResource/" value="返回" onclick = "func()" />
    </td>
    </tr>
  </table>
</form>
</div>
</body>

<script type="text/javascript">
//<!--
var re = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/; 
function check(thisform)
{ 
	if (thisform.usrname.value=='')
   	{
    	alert("请输入用户名称！");
		thisform.usrname.focus();
		return false;
   	} 
   	
    if(!re.test(document.getElementById("usrname").value))
    {
	    alert("用户名应由字母、数字和汉字组成!");
	    return false;
	}

	 if (thisform.usrname.value.indexOf(" ")>=0)
   	{
    	alert("输入的用户名称不允许有空格！");
		thisform.usrname.focus();
		return false;
   	} 
   	
	if (thisform.password.value=='')
   	{
    	alert("请输入密码！");
		thisform.password.focus();
		return false;
   	}
	if (thisform.password.value.indexOf(" ")>=0)
   	{
    	alert("输入的密码中不允许有空格！");
		thisform.password.focus();
		return false;
   	}
	if (thisform.password2.value=='')
   	{
    	alert("请输入确认密码！");
		thisform.password2.focus();
		return false;
   	}
   	if(thisform.roleno.value=='')
   	{
   		alert("请选择角色！");
		thisform.roleno.focus();
		return false;
   	}
	if(thisform.password.value!=thisform.password2.value)
	{
		alert("两次输入密码不一致!");
		thisform.password.focus();
		return false;
	}
	if(!re.test(document.getElementById("note").value))
	{
		alert("备注应由字母、数字和汉字组成!");
		return false;
	}
  	return true;
}	
//-->	
function resetPara()
{
	document.getElementsByName("usrname")[0].value="";
	document.getElementsByName("employeeid")[0].value="";
	document.getElementsByName("password")[0].value="";
	document.getElementsByName("password2")[0].value="";
	document.getElementsByName("state")[0].value="";
	document.getElementsByName("roleno")[0].value="";
	document.getElementsByName("default_roleno")[0].value="";	
	document.getElementsByName("note")[0].value="";	
	document.getElementsByName("usrname")[0].focus();

}

function func(){
		var pageIndex = <%=intpage%>;
		window.location.href = "user_view.jsp?page=" + pageIndex+ '&info=<%=info%>';
			}
</script>
</html>
<%
	}
	catch(Exception e)
	{
		throw e;
	}finally{
		if(rs!=null)rs.close();
		if(rs2!=null)rs2.close();
		if(stmt!=null)stmt.close();
		if(stmt2!=null)stmt2.close();
		if(con!=null)con.close(); 
	}
%>







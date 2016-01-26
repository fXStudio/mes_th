<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="com.qm.mes.framework.dao.*"%>
<%@page import="com.qm.mes.framework.*"%>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="com.qm.mes.beans.Role"/>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%//@page import="tree.*"%>
<%@ include file="security.jsp"  %>

<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%

	String id=request.getParameter("id");
	String user_rolerank="";
	user_rolerank=(String)session.getAttribute("user_rolerank");
	String intpage=request.getParameter("intPage");
	String info = request.getParameter("info");
		info = info==null?"":info;
	ResultSet rs=null;
	Connection 	con=null;
	Statement stmt=null;
	String haveRoleNo = "";
	
	try{
	con=Conn.getConn();
	stmt=con.createStatement();

	
	String usrno = "";
	String usrname = "";

	String employeeid = "";
	//String roleno = "";
	String default_roleno = "";
	String old_default_roleno = "";
	String note = "";
	String state = "";
	
	String sql = "";
	
	IDAO_UserManager sqlDao = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
	sql=sqlDao.getSQL_QueryUserInfoForUserID(id);	
	
	rs=stmt.executeQuery(sql);
	if(rs.next())
	{
		usrno=rs.getString(1);
		usrname=rs.getString(2);
		employeeid=rs.getString(5);
		note=rs.getString(9);
		state=rs.getString(6);
	}
	java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
	map.put("正常","1");
	map.put("停用","0");
	String str_state = "停用";
	if(state.equals("1"))
	  str_state = "正常";
	
	sql=sqlDao.getSQl_QueryRoleForRank(user_rolerank);
	java.util.HashMap<Comparable,String> map2 = new java.util.HashMap<Comparable,String>();
	rs=stmt.executeQuery(sql);
	List<Role> listAllRole = new ArrayList<Role>();
	while(rs.next())
	{
	  Role r = new Role();
	  r.setRole_no(rs.getString(1));
	  r.setRole_name(rs.getString(2));
	  listAllRole.add(r);
	  map2.put(rs.getString(2),rs.getString(1));
	}
	
	String sql_haveRole = sqlDao.getSQL_selectHaveRole(new Integer(usrno));
	List<Role> listRoleNo = new ArrayList<Role>();
	rs=stmt.executeQuery(sql_haveRole);
	while(rs.next()){
       Role haverole = new Role();
       haverole.setRole_no(String.valueOf(rs.getInt(2)));
       haverole.setRole_name(rs.getString(4));
       haverole.setCenabled(rs.getString(3));
       haveRoleNo = haveRoleNo + ":" + haverole.getRole_no();
       listRoleNo.add(haverole); 
	}
%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>
<div valign=center>
<div  align="center"><strong>更新用户信息</strong></div>
<br>
<div align="center">
<form name=form1 method=get action=" user_updating.jsp"  onSubmit="return check(form1)">
  <input type = "hidden" name="info" value="<%=info%>">
  <table width="500" class="tbnoborder" border="1">
    <tr>
        <td width="81" >
		用户代码<font color="#ff0000">*</font>：		
		</td>
        <td width="407">
        <mes:inputbox id="usrno" name="usrno" size="36" readonly="true" value="<%=usrno%>" reSourceURL="../JarResource/" colorstyle="box_black" />
        </td>
	</tr>
	
	<tr>
		<td >
		用户名称：		
		</td>
        <td  >
		<mes:inputbox name="usrname" id="usrname" size="36" maxlength="10" readonly="true" value="<%=usrname%>" reSourceURL="../JarResource/" colorstyle="box_black" />		
		</td>     
    </tr>
    
    <tr>
    	<td >
		密码：		
		</td>
        <td  >
		<input type="password" class="box1" name="password" size="55" value="********" maxlength="15" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">		</td>     
    </tr>
	
	    <tr>
    	<td >
		确认密码：		
		</td>
        <td  >
		<input type="password" class="box1" name="password2" size="55" value="********" maxlength="15" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">		</td>     
    </tr>
    
    <tr>
		<td >
		员工号：		
		</td>
        <td >
		<mes:inputbox id="employeeid" name="employeeid" size="36"  maxlength="10" value="<%=employeeid%>" reSourceURL="../JarResource/" colorstyle="box_black" />		</td>     
    </tr>
    
    <tr>
    	<td>
		所属角色：		
		</td>
        <td  >
		 <%for(int i=0;i<listAllRole.size();i++){ 
		      Role aRole=(Role)listAllRole.get(i);
		      String str_out="";
		      for(int j=0;j<listRoleNo.size();j++){
		         Role bRole = (Role)listRoleNo.get(j);
		         if(aRole.getRole_no().equals(bRole.getRole_no()))
		            str_out = "checked";
		         if(bRole.getCenabled().equals("0")){
	                default_roleno=bRole.getRole_no();
	                old_default_roleno=bRole.getRole_no();
	             }
		      }
		 %>
		<input name="roleno" type="checkbox" value="<%=aRole.getRole_no() %>" <% out.write(str_out); %>><%=aRole.getRole_name()%>
		<br/>
		<%
		}
		%>
		</td>     
    </tr>
    
     <tr>
    	<td >
		默认角色：
		</td>
        <td >
        <%  
	        String str_sql=sqlDao.getSQL_QueryRoleForRoleNo(default_roleno);
        	String str_rolename = "";
        	rs=stmt.executeQuery(str_sql);
        	if(rs.next())
        	{
        		str_rolename=rs.getString(2);
         	}
    	%>
        <mes:selectbox colorstyle="box_black" id="selectbox2" name="default_roleno" map="<%=map2%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value="<%=str_rolename %>"/>
		</td>     
    </tr>
    
    <tr>
		<td >
		状态：		
		</td>
        <td >
        <mes:selectbox colorstyle="box_black" id="selectbox1" name="state" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="2" value="<%=str_state %>"/>
		</td>     
    </tr>
    
    <tr>
		<td >
		备注：		
		</td>
        <td >
		<mes:inputbox id="note" name="note" maxlength="100" size="36" value="<%=note%>" reSourceURL="../JarResource/" colorstyle="box_black" />		
		</td>  
		   
    </tr>
    <input type = "hidden" name="intpage" value="<%=intpage%>">
       <input type = "hidden" name="eid" value="<%=id%>">
     <input type="hidden" name="haveRoleNo" value="<%=haveRoleNo%>" />
     <input type="hidden" name="old_default_roleno" value="<%=old_default_roleno%>" />
    <tr>
     <td  class="tdnoborder">
    <mes:button id="button1" reSourceURL="../JarResource/" submit="true" value="更新"/>
    </td>
    
    <td  class="tdnoborder">
    <mes:button id="button2" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重置"/>
    </td>
    
     <td  class="tdnoborder">
    <mes:button id="button3" reSourceURL="../JarResource/" value="返回" onclick = "func()"/>
    </td>
    </tr>
  </table>
</form>
</div>
</div>
</body>

<script type="text/javascript">
//<!--
function check(thisform)
{  
	var re = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;  
	if(!re.test(document.getElementById("note").value))
	{
		alert("备注应由字母、数字和汉字组成!");
		return false;
	}
	
	if (thisform.usrno.value=='')
	{
    	alert("请输入用户代码！");
		thisform.usrno.focus();
		return false;
	} 
  	if (thisform.usrname.value=='')
   	{
    	alert("请输入用户名称！");
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
    	alert("密码中不允许有空格！");
		thisform.password.focus();
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
		alert("两次输入密码不一致");
		thisform.password.focus();
		return false;
	}
  	return true;
}	
//-->	
function resetPara()
{
	document.getElementsByName("employeeid")[0].value="";
	document.getElementsByName("password")[0].value="";
	document.getElementsByName("password2")[0].value="";
	document.getElementsByName("state")[0].value="";
	document.getElementsByName("roleno")[0].value="";
	document.getElementsByName("default_roleno")[0].value="";	
	document.getElementsByName("note")[0].value="";	
	document.getElementsByName("password")[0].focus();
}
function func(){
	var pageIndex = <%=intpage%>;
	var int_id=<%=id%>;		
	window.location.href = 'user_view.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';
	}
</script>
</html>
<%
	}catch(Exception e)
	{
		throw e;
	}finally{
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(con!=null)con.close();
	}
%>





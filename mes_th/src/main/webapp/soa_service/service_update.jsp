<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page import="com.qm.mes.framework.dao.*" %>
<%@ page import="com.qm.mes.framework.DataBaseType"%>

<%    
	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); 
	String intpage=request.getParameter("intPage");
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	String strSql="";
    String strSql1="";
    String info = request.getParameter("info");
		info = info==null?"":info;
	
	String serviceid=request.getParameter("serviceid");
	String servicename="";
	String classname="";
	String description="";
	String namespace="";
	String namespaceid="";
	
	conn=Conn.getConn();
	if (conn==null)
	{
		out.println("数据库连接失败!");
	}
	else
	{
		IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));
		strSql=dao.getSQL_QueryServiceForServiceid(Integer.parseInt(serviceid));
		
		try
		{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(strSql);
			if(rs.next())
			{
				servicename=rs.getString(2);
				classname=rs.getString(3);
				description=rs.getString(4);
				namespace=rs.getString(5);
				namespaceid=rs.getString(6);
			}
			else
			{
				out.println("无此服务号: "+ serviceid);
			}
			strSql1 = dao.getSQL_QueryOtherNameSpaces(namespace);
			rs=stmt.executeQuery(strSql1);
			java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
			map.put(namespace,namespaceid);
			while(rs.next())
			{
				map.put(rs.getString(2),String.valueOf(rs.getInt(1)));
			}
%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>更新服务定义</title>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>
<div align="center"><strong>更新服务定义</strong></div>
<div align="center">
<form name="form1" method="get" action="service_updating.jsp" onSubmit="return checkinput()" >
  <table class="tbnoborder" width="468">
    <tr>
	  <td width="130">服务名：</td>
      <td >
      <mes:inputbox name="servicename"  value="<%=servicename%>" size="36" id="servicename"  reSourceURL="../JarResource/" colorstyle="box_black" />
      </td>
    </tr>
    <tr>
      <td>对应类名：</td>
      <td>
      <mes:inputbox name="classname" value="<%=classname%>" maxlength="100" size="36" id="classname"  reSourceURL="../JarResource/" colorstyle="box_black" />
      </td>
    </tr>
    <tr>
      <td>业务描述：</td>
      <td>
      <mes:inputbox name="servicedesc" value="<%=description%>" size="36" id="servicedesc"  reSourceURL="../JarResource/" colorstyle="box_black" />
      </td>
    </tr>
    <tr>
      <td>命名空间：</td>
     <td >
      <mes:selectbox colorstyle="box_black" id="selectbox1" name="namespace" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value="<%=namespace %>"/>
     </td>
    </tr>
	</table>
	<br>
	<table width="314" class="tbnoborder">
	<input type = "hidden" name="intpage" value="<%=intpage%>">
	<input type = "hidden" name="info" value="<%=info%>">
    <tr>
      <td width="111"  class="tdnoborder">
      <!-- 
      <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onMouseOut="this.className='btn2_mouseout'" >
        <input class="btn2"  type="submit" name="Submit" value="提交"/>
      </span>
       -->
       <mes:button id="button1" reSourceURL="../JarResource/" submit="true" value="提交"/>
      </td>
	  <td width="109"  class="tdnoborder">
	   <!-- 
	  <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onMouseOut="this.className='btn2_mouseout'" >
        <input class="btn2"  type="button" name="button"  onclick="resetPara()" value="重置"/>
      </span>
       -->
       <mes:button id="button2" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重置"/>
      </td>
	  <td width="107"  class="tdnoborder">
	   <!-- 
	  <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onMouseOut="this.className='btn2_mouseout'" >
	    <input class="btn2"  type="button" name="button2"   onclick="window.location.href='service_manage.jsp'" value="返回"/>
	  </span>
	   -->
	   <mes:button id="button3" reSourceURL="../JarResource/" onclick = "func()" value="返回"/>
	  </td>
	  </tr>
  </table>
  <input  type="hidden" name="serviceid" value="<%=serviceid%>"/>
 </form>
</div>
</body>

<script type="text/javascript">
function checkinput()
{
	if(form1.servicename.value==""||form1.classname.value==""||form1.servicedesc.value==""||form1.namespace.value=="")
	{
		alert("参数为空");
		return false;
	}
	else
		return true;


}
function resetPara()
{
	document.getElementsByName("servicename")[0].value="";
	document.getElementsByName("classname")[0].value="";
	document.getElementsByName("servicedesc")[0].value="";
	document.getElementsByName("namespace")[0].value="";
	document.getElementsByName("servicename")[0].focus();

}

function func(){
	var pageIndex = <%=intpage%>;
	var int_id=<%=serviceid%>;		
	window.location.href = 'service_manage.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';
	}
</script>
</html>
<%
    }catch(Exception e)
	{
		out.println("数据库出现异常");
		throw e;
	}finally{
		//释放资源
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(conn!=null)conn.close();
	}
	}
%>
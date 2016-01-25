<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:useBean id="Conn" scope="page" class="common.Conn"/>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page import="mes.framework.dao.*" %>
<%@ page import="mes.framework.DataBaseType"%>

<%    
	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); 
	
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	String strSql="";
   String intpage=request.getParameter("intPage");
	String id=request.getParameter("id");
	String namespace="";
	String desc="";
	String info = request.getParameter("info");
		info = info==null?"":info;

	conn=Conn.getConn();
	if (conn==null)
	{
		out.println("数据库连接失败!");
	}
	else
	{
		IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));
		strSql= dao.getSQL_QueryNameSpaceForNameSpace(Integer.parseInt(id));
		try
		{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(strSql);
			if(rs.next())
			{
				namespace=rs.getString(1);
				desc=rs.getString(2);
			

			}
			else
			{
				out.println("无此命名空间: "+ namespace);
			}
%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>更新命名空间</title>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>
<div align="center">
<form name="form1" method="get" action="namespace_updating.jsp" onSubmit="return checkinput()" >
  <div class="title">更新命名空间</div>
  <br/>
  <table class="tbnoborder" width="468">
    <tr>
	  <td width="130">命名空间：</td>
      <td >
      <mes:inputbox name="namespace"  value="<%=namespace%>" size="36" id="namespace"  reSourceURL="../JarResource/" colorstyle="box_black" />
      </td>
    </tr>
    <tr>
      <td>描&nbsp;&nbsp;述：</td>
      <td>
      <mes:inputbox name="desc" value="<%=desc%>" size="36" id="classname"  reSourceURL="../JarResource/" colorstyle="box_black" />
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
      <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	 onMouseOut="this.className='btn2_mouseout'" >
        <input class="btn2"  type="submit" name="Submit" value="提交"/>
      </span>
       -->
      <mes:button id="button1" reSourceURL="../JarResource/" submit="true" value="提交"/>
      </td>
	  <td width="109"  class="tdnoborder">
	  <!-- 
	  <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	 onMouseOut="this.className='btn2_mouseout'" >
        <input class="btn2"  type="button" name="button"  onclick="resetPara()" value="重置"/>
      </span>
       -->
      <mes:button id="button2" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重置"/>
      </td>
	  <td width="107"  class="tdnoborder">
	  <!-- 
	  <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	 onMouseOut="this.className='btn2_mouseout'" >
	    <input class="btn2"  type="button" name="button2"   onclick="window.location.href='namespace_manage.jsp'" value="返回"/>
	  </span>
	   -->
	  <mes:button id="button3" reSourceURL="../JarResource/"onclick = "func()"  value="返回"/>
	  </td>
	  </tr>
  </table>
  <input type="hidden" name="id" value="<%=id%>">
 </form>
</div>
</body>

<script type="text/javascript">
function checkinput()
{
	if(form1.namespace.value==""||form1.desc.value=="")
	{
		alert("参数为空");
		return false;
	}
	else
		return true;
}
function resetPara()
{
	document.getElementsByName("namespace")[0].value="";
	document.getElementsByName("desc")[0].value="";
	document.getElementsByName("namespace")[0].focus();
}

function func(){
	var pageIndex = <%=intpage%>;
	var int_id=<%=id%>;		
	window.location.href = 'namespace_manage.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';
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
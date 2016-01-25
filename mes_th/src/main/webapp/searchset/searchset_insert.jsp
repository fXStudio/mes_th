<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<html><!-- InstanceBegin template="/Templates/new_提交.dwt.jsp" codeOutsideHTMLIsLocked="false" -->
<!-- InstanceBeginEditable name="引入包" -->
<%@page import="th.tg.dao.*" %>
<%@ page import="th.tg.bean.*" %>
<jsp:directive.page import="mes.framework.*,mes.system.dao.*"/>
<%@page import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<!-- InstanceEndEditable -->
<%	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); %>
<!-- 以下为修改提交参数时用 -->
<!-- InstanceBeginEditable name="获得连接对象" -->
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<%  String intpage=request.getParameter("intPage");	
String info = request.getParameter("info");
		info = info==null?"":new String(info);   
    Connection con=null;
    Statement stmt=null;
    ResultSet rs=null;
    try{
		final  Log log = LogFactory.getLog("searchset_insert.jsp");
	
 %>
 <!-- InstanceEndEditable -->
<!-- 结束 -->
 
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<!-- InstanceBeginEditable name="标题" -->
<title>添加查询设置</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" -->
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
<!-- InstanceEndEditable -->
</head>
<div class="title">
<!-- InstanceBeginEditable name="表单标题" -->
		添加查询设置
<!-- InstanceEndEditable -->
</div>
<body bgcolor="#FFFFFF">
<div align="center">
<!-- InstanceBeginEditable name="表单" -->
<form  name="form1"  action="searchset_adding.jsp" method="get" onSubmit="return checkinput();">
    <table class="tbnoborder" border="1">
      <tr>
        <td width="120">查询设置名称：</td>
        <td width="220">
		<mes:inputbox id="cSearchname" name="cSearchname" size="27" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
      </tr>
      <tr>
        <td width="120">焊总装：</td>
        <td width="220">
		<select id="cWA" name="cWA">
			<option value="W">焊装</option>
			<option value="A">总装</option>
		</select>
		</td>
      </tr>
	  <tr>
        <td>工厂代码：</td>
        <td>
       	<mes:inputbox id="cFactory" name="cFactory" size="27" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
      </tr>
	  <tr>
        <td>工厂名称：</td>
        <td>
       	<mes:inputbox id="cDscFactory" name="cDscFactory" size="27" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
      </tr>
	  <tr>
        <td>车型代码：</td>
        <td>
       	<mes:inputbox id="cCarType" name="cCarType" size="27" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
      </tr>
	  <tr>
        <td>车型名称：</td>
        <td>
       	<mes:inputbox id="cDscCarType" name="cDscCarType" size="27" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
      </tr>
	  <tr>
        <td>备注：</td>
        <td>
       	<mes:inputbox id="cRemark" name="cRemark" size="27" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
      </tr>
    </table>
    <div id="divnamecheck"></div><br>
    <table width="384" class="tbnoborder">
    <input type = "hidden" name="intpage" value="<%=intpage%>">
    <input type = "hidden" name="info" value="<%=info%>">
      <tr>
        <td class="tdnoborder" width="123" >
		 <mes:button id="button1" reSourceURL="../JarResource/" submit="true" value="提交"/>
		</td>
        <td  class="tdnoborder" width="126" >
		 <mes:button id="button2" reSourceURL="../JarResource/" submit="false" value="重置"/>
		</td>
        <td class="tdnoborder" width="119" >
		 <mes:button id="button3" reSourceURL="../JarResource/" onclick = "func()"  value="返回"/>
		</td>
      </tr>
    </table>
	</form>
	<!-- InstanceEndEditable -->
 </div>
</body>
<!-- InstanceBeginEditable name="参数验证" -->

<script type="text/javascript">
var namecheck="true";//名称重复判断
function checkinput(){
  var re =  /^[0-9]+$/;
  return true;
}
//返回
function func(){
	window.location.href="searchset_manage.jsp";
}

</script>
<!-- InstanceEndEditable -->
<!-- InstanceEnd --></html>

<%
    //释放资源
	}catch(Exception e)
	{
		throw e;
	}finally{
		//释放资源
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(con!=null)con .close();
	}
 %>
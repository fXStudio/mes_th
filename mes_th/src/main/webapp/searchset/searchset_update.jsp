<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@page import="th.tg.factory.SearchSetFactory"%>
<html><!-- InstanceBegin template="/Templates/new_提交.dwt.jsp" codeOutsideHTMLIsLocked="false" -->
<!-- InstanceBeginEditable name="引入包" -->
<%@page import="th.tg.bean.*,java.util.*,mes.framework.dao.*,mes.system.dao.*" %>
<%@page import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<!-- InstanceEndEditable -->
<%	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); %>
<!-- 以下为修改提交参数时用 -->
<!-- InstanceBeginEditable name="获得连接对象" -->
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
 <%
 	String info = request.getParameter("info");
		info = info==null?"":new String(info);
 	String int_id = request.getParameter("int_id");
 	String intpage=request.getParameter("intPage");
 	System.out.println("searchset_update 页数："+intpage);
	if(int_id==null){
		out.println("<script>alert(\"参数为空\"); window.location.href=\"searchset_manage.jsp\";</script>");
		return;
	} 
    Connection con=null;
    try{
	    con = Conn.getConn();
		SearchSetFactory factory = new SearchSetFactory();
		SearchSet ss = factory.getSearchSetById(Integer.parseInt(int_id),con);
		final  Log log = LogFactory.getLog("searchset_update.jsp");
		
		
 %>
 <!-- InstanceEndEditable -->
 
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<!-- InstanceBeginEditable name="标题" -->
<title>修改查询设置信息</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" -->
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
<!-- InstanceEndEditable -->
</head>
<div class="title">
<!-- InstanceBeginEditable name="表单标题" -->
		修改查询设置信息
<!-- InstanceEndEditable -->
</div>
<body bgcolor="#FFFFFF">
<div align="center">
<!-- InstanceBeginEditable name="表单" -->
<form  name="form1"  action="searchset_updating.jsp" method="get"  onSubmit="return checkinput()">
    <table class="tbnoborder" border="1">
      <tr>
        <td width="100" >查询设置号：</td>
        <td width="80" colspan="1">
		<%=int_id %>
		</td>
	</tr>
	<tr>
        <td width="100">查询设置名：</td>
        <td width="80">
		<mes:inputbox id="csearchname" name="csearchname" value="<%=ss.getCsearchName() %>" size="20" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
   	</tr>
	<tr>
		<td>焊总装：</td>
        <td>
		<select id="cWA" name="cWA">
		<%if(ss.getCwa().equals("A")) {%>
			<option value="W">焊装</option>
			<option value="A" selected="selected">总装</option>
		<%}else{%>
			<option value="W" selected="selected">焊装</option>
			<option value="A">总装</option>
		<%}%>
		</select>
		</td>
	  </tr>
	  <tr>
        <td>工厂代码：</td>
        <td>
		<mes:inputbox id="cFactory" name="cFactory" value="<%=ss.getCfactory() %>" size="20" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
		</tr>
	  <tr>
        <td width="100">工厂名称：</td>
        <td width="80">
		<mes:inputbox id="cDscFactory" name="cDscFactory" value="<%=ss.getCdscFactory() %>" size="20" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
   	  </tr>
	  <tr>
        <td width="100">车型代码：</td>
        <td width="80">
		<mes:inputbox id="cCarType" name="cCarType" value="<%=ss.getCcarType() %>" size="20" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
   	  </tr>
	  <tr>
        <td width="100">车型名称：</td>
        <td width="80">
		<mes:inputbox id="cDscCarType" name="cDscCarType" value="<%=ss.getCdscCarType() %>" size="20" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
   	  </tr>
	  <tr>
        <td width="100">描述：</td>
        <td width="80">
		<mes:inputbox id="cRemark" name="cRemark" value="<%=ss.getCremark() %>" size="20" maxlength="25" reSourceURL="../JarResource/" colorstyle="box_black" />
		</td>
   	  </tr>
    </table>
		<div id="divnamecheck"></div>
		
    <br>
    <table width="384" class="tbnoborder">
    <input type = "hidden" name="intpage" value="<%=intpage%>">
      <tr>
        <td class="tdnoborder" width="123" >
		<mes:button id="button1" reSourceURL="../JarResource/" submit="true" value="提交"/>
		</td>
        <td  class="tdnoborder" width="126" >
		<mes:button id="button2" reSourceURL="../JarResource/" submit="false" value="重置"/>
		</td>
        <td class="tdnoborder" width="119" >
		<mes:button id="button3" reSourceURL="../JarResource/" onclick="func()" value="返回"/>
		</td>
      </tr>
    </table>
     <input type="hidden" name="int_id" value="<%=int_id %>"/>
	<input type = "hidden" name="info" value="<%=info%>">

	</form>
	<!-- InstanceEndEditable -->
 </div>
</body>
<!-- InstanceBeginEditable name="参数验证" -->

<script type="text/javascript">
var namecheck="true";//名称重复判断
function checkinput(){

var re =  /^[0-9]+$/;
	
}

function func(){
	window.location.href="searchset_manage.jsp?page=<%=intpage%>&info=<%=info%>";
}
</script>
<!-- InstanceEndEditable -->
<!-- InstanceEnd --></html>

<%
		//释放资源

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				con.close();
		}
	%>	

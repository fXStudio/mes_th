<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<html><!-- InstanceBegin template="/Templates/提交模版1.dwt.jsp" codeOutsideHTMLIsLocked="true" -->
<!-- InstanceBeginEditable name="引入包" -->
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page import="mes.util.tree.*"%>
<!-- InstanceEndEditable -->
<%	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); %>
<%	String userid=(String)session.getAttribute("userid");
    String intpage=request.getParameter("intPage");		%>
<!-- InstanceBeginEditable name="获得连接对象" -->
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
<!-- InstanceEndEditable -->
 
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<!-- InstanceBeginEditable name="标题" -->
		<title>添加角色</title>
<!-- InstanceEndEditable -->
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<div class="title">
	<!-- InstanceBeginEditable name="表单标题" -->
		添加角色
	<!-- InstanceEndEditable -->
</div>
<body>

<div align="center"><!-- InstanceBeginEditable name="表单" -->
<%	Connection con = null;
	BuildTree bct=null;
	String info = request.getParameter("info");
		info = info==null?"":info;    
	try{
		con = Conn.getConn();
		bct=new BuildTree( con, userid,"insert","");
		java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
		map.put("开发级","0");
		map.put("应用级","1");
%>
<form id="form1" action="role_adding.jsp" method="get" onSubmit="return checkinput();">
    <table width="468" class="tbnoborder" border="1">
      <tr>
        <td align="center" width="119">角色名：</td>
        <td width="266">
        <mes:inputbox name="rolename" size="36" id="rolename" maxlength="30" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
      </tr>
	  <%if(((String)session.getAttribute("user_rolerank")).equals("1")){%>
	  	<input type="hidden" name="rank" value="1"/>
	  <%}else{%>
	   <tr>
			<td width="119" align="center">级别：</td>
			<td width="266">
			 <mes:selectbox colorstyle="box_black" id="selectbox1" name="rank" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="2" value=""/>
			</td>
      </tr><%}%>
	   <tr>
        <td align="center" width="119">权限：</td>
        <td width="266" style="padding:20px 20px 20px 50px "><%=bct.getHtmlCode()%></td>
      </tr>
	   <tr>
        <td align="center" width="119">首页：</td>
        <td width="266">
        <input name="welcomepage" value="body.jsp" size="48" type="text"  class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'" maxlength="100"onkeyup="this.value=this.value.replace(/\b[\d]*/,'');" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/\b[\d]*/,''))"></td>
      </tr>
	   <tr>
        <td align="center" width="119">描述：</td>
        <td width="266">
        <mes:inputbox name="note" value="无备注" id="note" size="36"  maxlength="100" reSourceURL="../JarResource/" colorstyle="box_black" />
        </td>
      </tr>
    </table>
    <br>
    <table width="384" class="tbnoborder">
    <input type = "hidden" name="intpage" value="<%=intpage%>">
      <tr>
        <td class="tdnoborder" width="123" >
        <mes:button id="button1" reSourceURL="../JarResource/" submit="true" value="提交"/>
        </td>
        <td  class="tdnoborder" width="126" >
        <mes:button id="button2" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重置"/>
        </td>
        <td class="tdnoborder" width="119" >
        <mes:button id="button3" reSourceURL="../JarResource/"  onclick = "func()" value="返回"/>
        </td>
      </tr>
    </table>
</form>
<!-- InstanceEndEditable --> </div>
</body>

<!-- InstanceBeginEditable name="参数验证" -->
<script type="text/javascript">
var re = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/; 
function checkinput(){
	if(!re.test(document.getElementById("rolename").value))
    {
	    alert("角色名应由字母、数字和汉字组成!");
	    return false;
	}
	if(!re.test(document.getElementById("note").value))
	{
		alert("描述应由字母、数字和汉字组成!");
		return false;
	}
	var rolename = document.getElementsByName("rolename")[0];
	var welcomepage = document.getElementsByName("welcomepage")[0];
	var note = document.getElementsByName("note")[0];
	if(rolename.value==""||welcomepage.value==""||note.value=="")	{
		alert("缺少参数!");
		return false;
	}
	else
		return true;
}

function resetPara(){
	document.getElementsByName("rolename")[0].value="";
	document.getElementsByName("welcomepage")[0].value="";
	document.getElementsByName("note")[0].value="";
	document.getElementsByName("rolename")[0].focus();
}
function func(){
		var pageIndex = <%=intpage%>;
		window.location.href = "role_manage.jsp?page=" + pageIndex+ '&info=<%=info%>';
			}

</script>
<!-- InstanceEndEditable -->
<!-- InstanceEnd --></html>
<%
    }catch(SQLException sqle){
		System.out.println(sqle);
	}finally{
		if(con!=null)con.close();
	}
%>
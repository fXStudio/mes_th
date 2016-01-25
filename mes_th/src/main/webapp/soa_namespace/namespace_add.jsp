<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%    
	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0);
  	String intpage=request.getParameter("intPage");
  	String info = request.getParameter("info");
		info = info==null?"":info;	    
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>命名空间定义</title>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>
<div align="center">
<form name="form1" method="get" action="namespace_adding.jsp" onsubmit="return checkinput();" >
<div class="title">命名空间定义</div>
<br/>
  <table width="468" class="tbnoborder">
    <tr>
      <td width="130">命名空间：</td>
      <td>
      <mes:inputbox name="namespace" size="36" id="namespace"  reSourceURL="../JarResource/" colorstyle="box_black" />
      </td>
    </tr>
    <tr>
      <td>描&nbsp;&nbsp;述：</td>
      <td>
      <mes:inputbox name="desc"  size="36" id="desc"  reSourceURL="../JarResource/" colorstyle="box_black" />
      </td>
    </tr>
    </table>
    <input type = "hidden" name="intpage" value="<%=intpage%>">
    <br>
    <table width="384" class="tbnoborder">
	<tr>
      <td class="tdnoborder" width="123" >
      <mes:button id="button1" reSourceURL="../JarResource/" submit="true"  value="提交"/>
      </td>
	  <td  class="tdnoborder" width="126" >
      <mes:button id="button2" reSourceURL="../JarResource/" submit="false"  onclick="resetPara()" value="重置"/>
      </td>
	  <td class="tdnoborder" width="119" >
	  <mes:button id="button3" reSourceURL="../JarResource/"  onclick = "func()" value="返回"/>
	  </td>
	  </tr>
  </table>
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
	{
		return true;
	}


}
function resetPara()
{
	document.getElementsByName("namespace")[0].value="";
	document.getElementsByName("desc")[0].value="";
    document.getElementsByName("namespace")[0].focus();
}
function func(){
		var pageIndex = <%=intpage%>;
		window.location.href = "namespace_manage.jsp?page=" + pageIndex+ '&info=<%=info%>';
			}

</script>
</html>
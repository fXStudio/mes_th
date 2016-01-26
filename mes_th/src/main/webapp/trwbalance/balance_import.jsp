<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<html>
<!-- InstanceBeginEditable name="引入包" -->
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page	import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
<!-- InstanceEndEditable -->
<!-- 以下为修改提交参数时用 -->
<!-- InstanceBeginEditable name="获得连接对象" -->
<%

%>
<!-- InstanceEndEditable -->
<!-- 结束 -->
<style> 
	.cal_descr{ display:'none'}
</style>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<!-- InstanceBeginEditable name="标题" -->
<title>月末结算导入</title>
<!-- InstanceEndEditable -->
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<div class="title">
<!-- InstanceBeginEditable name="表单标题" -->
		月末结算导入
<!-- InstanceEndEditable -->
</div>
<body>
<div align="center">
<!-- InstanceBeginEditable name="表单" -->
<form  name="form1" enctype="multipart/form-data"
 action="balance_importing.jsp" method="post" onSubmit="return checkinput()">
    <table class="tbnoborder"  align="center">
      <tr>
	    <td>
			选择导入文件：<input id="inputxls" name="inputxls" type="file">
		</td>
      </tr>
      <tr>
	    <td>
			选择导入文件：<input id="inputxls" name="inputxls" type="file">
		</td>
      </tr>
      <tr>
	    <td>
			选择导入文件：<input id="inputxls" name="inputxls" type="file">
		</td>
      </tr>
      <tr>
	    <td>
			选择导入文件：<input id="inputxls" name="inputxls" type="file">
		</td>
      </tr>
      <tr>
	    <td>
			选择导入文件：<input id="inputxls" name="inputxls" type="file">
		</td>
      </tr>
	  <tr><td >&nbsp;</td></tr>
	  <tr>
	  	<td class="tdnoborder">
			<input type="submit" id="button1"  value="上传"/>&nbsp;&nbsp;
		</td>
      </tr>
   <!-- 
      <tr>
      <td> </td>
      	<td align="right">
      	  <img width="50" src="../images/plus.bmp" onclick="">
      	  <img width="50" src="../images/minus.bmp" onclick="">
      	</td>
      </tr> -->
    </table>
    <br/>
	<div align="center" style="color:ff0000;font-size:9pt">选择导入文件项的文件名不能为空。</div>
</form>
  <!-- InstanceEndEditable -->
 </div>
</body>
<!-- InstanceBeginEditable name="参数验证" -->
<script type="text/javascript">
function checkinput(){
var file = form1.inputxls;
if(file.value=='')
	{
		alert("请选择上传文件");
		file.focus();
		return false;
	}if(file.value.substring(file.value.length-4).toLowerCase()!=".txt"){
		alert("请选择txt文件");
		file.focus();
		return false;
	}else{
		return true;
	}
}
</script>
<!-- InstanceEndEditable -->
<!-- InstanceEnd --></html>
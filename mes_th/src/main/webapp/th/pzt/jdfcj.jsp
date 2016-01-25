<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% 
                    Calendar ca = Calendar.getInstance();
					Date da = ca.getTime();
					SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
					String sj = formt.format(da);
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="../../cssfile/css.css">
		<script type="text/javascript"
			src="../../JarResource/META-INF/tag/taglib_common.js"></script>
			 <base href="<%=basePath%>">
    <title>捷达/宝莱/高尔夫线配货单副车架打印</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	

  </head>
  
  <body>
  
  
      <div align="center">
      <font size="+4" >捷达/宝莱/高尔夫线配货单副车架打印</font>
      </div>
      <form id="form1" name="form1" method="post" action="">
     <table width="369" border="1" align="center">
    <tr>
      <td width="134"><label>
        <input type="radio" name="ls"  value="2" checked="checked" onclick="setls(2)"/>
      12辆份</label></td>
      <td width="111"><label>
        <input type="radio" name="ls" value="4" onclick="setls(4)"/>
      24辆份</label></td>
      <td width="102"><label>
        <input type="radio" name="ls" value="8" onclick="setls(8)" />
      48辆份</label></td>
    </tr>
    <tr>
      <td align="center">请输入日期</td>
      
      <td colspan="2"><label>
        <input type="text" name="rq" id="rq"  value="<%=sj %>"/>
      </label></td>
    </tr>
    <tr>
      <td align="center">请入车号</td>
      <td colspan="2"><label>
        <input type="text" name="ch" id="ch" />
      </label></td>
    </tr>
    <tr>
      <td align="center"><label>
        <input type="button" name="button" id="button" value="提交" onclick="openApp()"/>
      </label></td>
      <td colspan="2" align="center"><label>
        <input type="reset" name="button2" id="button2" value="重置" />
      </label></td>
    </tr>
  </table>
  
   <div id="d">
  <APPLET ID="JrPrt" name = "app" codebase="th/pzt" CODE = "JdfcjApplet"  
  	ARCHIVE = "jasperreports-3.1.4-applet.jar,jcommon-1.0.10.jar,jasperreports-2.0.5.jar"
  	 WIDTH = "0" HEIGHT = "0" MAYSCRIPT>
  <PARAM NAME = "type" VALUE="application/x-java-applet;version=1.2.2"/>
  <PARAM NAME = "scriptable" VALUE="true"/>
  <PARAM NAME = "REPORT_URL" VALUE =""/>
  </APPLET>
  </div>
</form>
</body>
</html>
<script language="javascript"> 
var ls = 2;
function setls(xx){
	ls=xx;
	//alert(ls);
}
function test(s){
    var rq=document.getElementById("rq").value;
    var ch=document.getElementById("ch").value;
    document.app.pp(rq,ch,s);
}

function openApp() 
{ 

    //var ls=document.getElementById("ls").value;
    var rq=document.getElementById("rq").value;
    var ch=document.getElementById("ch").value;
   document.app.pp(rq,ch,1,ls);
  
    
   
}

</script> 

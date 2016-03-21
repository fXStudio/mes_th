<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	// 取前一页面日期
	String jspRq = request.getParameter("rq"); 
	
	// 判断是否存在预设日期，如果存在则直接取预设值，否则取当前日期
	if(jspRq == null || jspRq.trim().equals("")) {
		jspRq = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	}
	// 设置参数到页面流上下文中
	request.setAttribute("rq", jspRq);
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">
        <title>配货单打印</title>	
        <meta http-equiv=content-type content="text/html;charset=GBK">
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">    
        <link rel="stylesheet" type="text/css" href="<%=basePath%>cssfile/css.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>cssfile/th_style.css">
	
        <!-- Mes Framework Library -->
        <script type="text/javascript" src="<%=basePath%>JarResource/META-INF/tag/taglib_common.js"></script>
    </head>

    <!-- 配置单打印，逻辑处理区 -->
    <body>
        <!-- Top Region (Title & Date Selector) -->
        <div align="center">
            <font size="6" >配货单打印</font> 
            <label> 
                <mes:calendar id="rq" name="rq" reSourceURL="JarResource/" 
      			   showDescripbe="false" haveTime="false" 
      			   value="<%=jspRq%>" onchange="getPrtDate()"/>
            </label> 
        </div>
		
        <!-- 配置单打印表单 -->
        <form id="form1" name="form1" method="post" action="">
            <table width="1000" border="1" align="center" height="97">
                <tr>	
                    <td>序号</td>
                    <td width="40%">描述</td>
                    <td width="24%">基础数据维护</td>
                    <td width="7%">当前架号</td>
                    <td width="7%"><strong class="accepted">已接收</strong></td>
                    <td width="7%">打印标准</td>
                    <td width="10%">自动打印 </td>
                    <td width="15%">提交</td>
                </tr>
            
                <!-- 导入业务逻辑处理，和数据的交互 -->
                <jsp:include page="PrintLogic.jsp"></jsp:include>
            </table>
        </form>
		
        <!-- 加载Applet对象，用户配置单的打印 -->
        <div id="d">
            <APPLET ID="JrPrt" name = "app" codebase="th/pzt" CODE = "JdApplet"  
          	               ARCHIVE = "jasperreports-3.1.4-applet.jar,jcommon-1.0.10.jar,jasperreports-2.0.5.jar"
          	               WIDTH = "0" HEIGHT = "0" MAYSCRIPT>
                <PARAM NAME = "type" VALUE="application/x-java-applet;version=1.2.2"/>
                <PARAM NAME = "scriptable" VALUE="true"/>
                <PARAM NAME = "REPORT_URL" VALUE =""/>
            </APPLET>
        </div>

        <!-- 网站的根目录 -->
        <input type="hidden" value="<%=basePath%>" id="basePath"/>

        <!-- Load Javascript -->
        <script type="text/javascript" src="<%=basePath%>/js/Print-PHD.js"></script>
        <script type="text/javascript"><%=request.getAttribute("openApp")%></script>
    </body>
</html>
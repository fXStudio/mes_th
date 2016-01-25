<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="th.tg.dao.*,th.tg.factory.*" %>
<%@page import="th.tg.bean.*,mes.ra.util.*" %>
<%@page import="java.util.*,java.text.SimpleDateFormat" %>
<%@page	import="mes.framework.*,mes.framework.dao.*,mes.system.dao.*"%>

<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html><!-- InstanceBegin template="/Templates/new_view.dwt.jsp" codeOutsideHTMLIsLocked="true" -->

<!-- InstanceBeginEditable name="获得连接" -->
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
<script language="JavaScript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="获得过滤" -->
<%@page import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%  
    final  Log log = LogFactory.getLog("updateCP5ATime.jsp");
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
    Connection con = null;
    List<AssemblySearch> list_ws = new ArrayList<AssemblySearch>();//车辆信息
    WeldingSearchFactory factory_ws = new WeldingSearchFactory();//查询工厂
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String condition_One = "n";//条件一
	String sql_temp1 = "";
	String time = "";
	try{
	    //获取连接
	    con=Conn.getConn();
		String sql=null;
		DAO_WeldingSearch dao = new DAO_WeldingSearch();
		time = request.getParameter("d11")==null?"":request.getParameter("d11");
	    	condition_One = request.getParameter("vin")==null||request.getParameter("vin")==""?"n":request.getParameter("vin");
	    	sql_temp1 = "cvincode='"+ condition_One +"' ";

		list_ws = factory_ws.getcar(sql_temp1,con);
		condition_One = condition_One.equals("n")?"":condition_One;
	%>				
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
<!-- InstanceBeginEditable name="标题" -->
		<title>总装时间修改</title>
<!-- InstanceEndEditable -->
<meta http-equiv="Content-Language" content="zh-cn">
<style>
.head2{	height:22px;color:ffff00;
}
</style>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>

<body background="../images/background.jpg">
<!-- 引用通用脚本 -->
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>

<div class="title"><strong><!-- InstanceBeginEditable name="标题2" -->总装时间修改<!-- InstanceEndEditable --></strong></div>
	<br>
	<div align="center"><!-- InstanceBeginEditable name="内容1" -->
	  <form action="updateCP5ATime.jsp">
	  <table>
  	  <tr><td>
	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;VIN：<input name="vin" size="17" maxlength="17" value="<%=condition_One%>">
	  	<mes:button id="s1" reSourceURL="../JarResource/" submit="true" value="查询" />
	    </td>
	  </tr>
	  <tr>
	    <td>
	  	修改时间：<input id="d11" name="d11" type="text" value="<%=time==null?"":time%>" />
		<img onclick="WdatePicker({el:'d11',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="../My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
		<mes:button id="s2" reSourceURL="../JarResource/" onclick="update()" value="更新" />
	  </td></tr>
  	</table>
  </form>
  	</div>
  	<div align="center">
  	<style>
  	td{border-width:1pt;border-style :solid}
  	</style>
  	<style>
  	tr{border-width:1pt;border-style :solid}
  	</style>
  	<p></p><p></p>
  <table border="0" cellspacing="0" cellpadding="0" width="1090">
  	<tr>
  	<td align="center" width="90">焊装顺序号</td>
  	<td align="center" width="90">总装顺序号</td>
  	<td align="center" width="150">VIN</td>
  	<td align="center" width="90">订单号</td>
  	<td align="center" width="150">焊装上线</td>
  	<td align="center" width="150">总装上线</td>
  	<td align="center" width="150">CP6上线</td>
  	<td align="center" width="110">焊装报文名称</td>
  	<td align="center" width="110">总装报文名称</td>
  	</tr>
  	<%for(int i=0;i<list_ws.size();i++){%>
  	  <tr>
  	    <td align="center"><%=list_ws.get(i).getSeq_W()==null?"-":list_ws.get(i).getSeq_W()%></td>
  	  	<td align="center"><%=list_ws.get(i).getSeq()==null?"-":list_ws.get(i).getSeq()%></td>
  	  	<td align="center"><%=list_ws.get(i).getVin()==null?"-":list_ws.get(i).getVin()%></td>
  	    <td align="center"><%=list_ws.get(i).getKin()==null?"-":list_ws.get(i).getKin()%></td>
  	    <td align="center"><%=list_ws.get(i).getDWBegin()==null?"-":sdf.format(sdf.parse(list_ws.get(i).getDWBegin()))%></td>
  	    <td align="center"><%=list_ws.get(i).getDABegin()==null?"-":sdf.format(sdf.parse(list_ws.get(i).getDABegin()))%></td>
  	    <td align="center"><%=list_ws.get(i).getDCp6Begin()==null?"-":sdf.format(sdf.parse(list_ws.get(i).getDCp6Begin()))%></td>
  	    <td align="center"><%=list_ws.get(i).getCfilename_w()==null?"-":list_ws.get(i).getCfilename_w()%></td>
  	    <td align="center"><%=list_ws.get(i).getCfilename_a()==null?"-":list_ws.get(i).getCfilename_a()%></td>
  	  </tr>
    <%} %>
  </table>
  </div>
</body>

<%
		//释放资源
	
		}catch(Exception e)
		{		
			e.printStackTrace();
		}finally {			
			if(con!=null)con .close();
			}
		
	%>	
<!-- InstanceBeginEditable name="script" -->
<script type="text/javascript">
function checkinput(thisform){
        var re =  /^[0-9]+$/;
		var i=0;
		var qm;
		var mm = document.getElementsByName("method");
		
}
function update(){
	var vin = document.getElementsByName("vin")[0].value;
	var time = document.getElementsByName("d11")[0].value;
	if(time==""){
		alert("请输入时间");
		return;
	}
	window.location.href="updateCP5ATimeing.jsp?vin="+vin+"&time="+time;
}
</script>
<!-- InstanceEndEditable -->
<!-- InstanceEnd --></html>
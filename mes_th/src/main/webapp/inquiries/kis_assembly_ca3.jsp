<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="th.tg.dao.*,th.tg.factory.*" %>
<%@page import="th.tg.bean.*,mes.ra.util.*" %>
<%@page import="java.util.*,java.text.SimpleDateFormat" %>
<%@page	import="mes.framework.*,mes.framework.dao.*,mes.system.dao.*"%>
<script language="JavaScript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
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
    final  Log log = LogFactory.getLog("kis_assembly.jsp");
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
    Connection con = null;
    WeldingSearchFactory factory_ws = new WeldingSearchFactory();//总装查询工厂
    List<KisAssembly> list_ka = new ArrayList<KisAssembly>();//KIS总装查询集合
    SearchSetFactory factory_ss = new SearchSetFactory();//查询设置工厂
    SearchSet ss = new SearchSet();//查询设置对象
    int searchsetid = 0;//查询设置序号
    String auto = "";
    String start_Time = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	try{
	    //获取连接
	    con=Conn.getConn();
	    if(request.getParameter("auto")==null||request.getParameter("auto").equals(""))
	    	auto = "true";
	    else
	    	auto = request.getParameter("auto");
	    start_Time = request.getParameter("d11");
	    log.debug("auto参数为："+auto);
	    searchsetid = Integer.parseInt(request.getParameter("searchsetid"));
	    ss = factory_ss.getSearchSetById(searchsetid,con);
		String sql_temp = null;
		DAO_WeldingSearch dao = new DAO_WeldingSearch();
		if(start_Time==null||start_Time.equals("null")||start_Time.equals("")){
			sql_temp = " and DateDiff(hour,dABegin,getdate())<24 ";
		}else{
			sql_temp = " and dABegin>=convert(varchar(100),'" + start_Time + "',20) ";
		}
		list_ka = factory_ws.getKISAssemblyByCartype(ss.getCcarType(),ss.getCfactory(),sql_temp,con);
		
	%>				
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<!-- InstanceBeginEditable name="标题" -->
		<title>查询设置</title>
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

<div class="title"><strong><!-- InstanceBeginEditable name="标题2" --><%=ss.getCdscCarType()+ss.getCdscFactory()%>总装FIS查询<!-- InstanceEndEditable --></strong></div>
  	<div align="center">
  	<style>
  	td{border-width:1pt;border-style :solid}
  	</style>
  	<style>
  	tr{border-width:1pt;border-style :solid}
  	</style>
  	<p></p><p></p>
  	<div align="center">
  	<%if(auto.equals("true")) {%>
  		<input type="button" name="noauto" value="切换至手动" onclick="unautoreflush()">
  	<%}else{ %>
  		<input type="button" name="auto" value="切换至自动" onclick="reflush()">
  		<input type="button" name="reflush" value="刷新" onclick="unautoreflush()">
  	<%} %>
  		<input type="hidden">
  	</div>
  	<div align="center">
  		开始时间：
  		<input id="d11" name="d11" type="text" value="<%=start_Time==null||start_Time.equals("")?"":start_Time%>"/>
	  <img onclick="WdatePicker({el:'d11',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="../My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
	  <input type="button" name="select_btn" value="查询" onclick="select_btn()">
  	</div>
  <table border="0" cellspacing="0" cellpadding="0" id="table1">
  	<tr>
  	<td align="center" width="60">顺序号</td>
  	<td align="center" width="40">车型</td>
  	<td align="center" width="150">VIN</td>
  	<td align="center" width="90">KIN</td>
  	<td align="center" width="180">	CP5A上线</td>
  	<td align="center" width="180">	CP6上线</td>
  	</tr>
  	<%for(int i=0;i<list_ka.size();i++){%>
  	  <tr>
	    <%if(list_ka.get(i).getSeq()!=null && list_ka.get(i).getSeq().substring(2).equals("1999")){%>
		<td align="center" style="background-color: red"><%=list_ka.get(i).getSeq()==null?"-":list_ka.get(i).getSeq()%></td>
	    <%}else if(list_ka.get(i).getSeq()!=null && list_ka.get(i).getSeq().substring(2).equals("0001")){%>
		<td align="center" style="background-color: #00FF00"><%=list_ka.get(i).getSeq()==null?"-":list_ka.get(i).getSeq()%></td>
	    <%}else{%>
  	  	<td align="center"><%=list_ka.get(i).getSeq()==null?"-":list_ka.get(i).getSeq()%></td>
	    <%}%>
  	  	<td align="center"><%=list_ka.get(i).getCartype()==null?"-":list_ka.get(i).getCartype()%></td>
  	  	<td align="center"><%=list_ka.get(i).getVin()==null?"-":list_ka.get(i).getVin()%></td>
  	    <td align="center"><%=list_ka.get(i).getKin()==null?"-":list_ka.get(i).getKin()%></td>
  	    <td align="center"><%=list_ka.get(i).getDABegin()==null?"-":sdf.format(sdf.parse(list_ka.get(i).getDABegin()))%></td>
  	    <td align="center"><%=list_ka.get(i).getDCp6Begin()==null?"-":sdf.format(sdf.parse(list_ka.get(i).getDCp6Begin()))%></td>
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
var auto = <%=auto%>;
function checkinput(thisform){
        var re =  /^[0-9]+$/;
		var i=0;
		var qm;
		var mm = document.getElementsByName("method");
		
}
//自动刷新
function reflush(){
	window.location.href="kis_assembly.jsp?auto=true&searchsetid=<%=searchsetid%>&d11=" + document.getElementsByName("d11")[0].value;
}
//如果request.getParameter("auto")==true，本页面自动刷新
if(auto==true) {
	setTimeout(reflush,60000);
}
//手动刷新
function unautoreflush(){
	window.location.href="kis_assembly.jsp?auto=false&searchsetid=<%=searchsetid%>&d11=" + document.getElementsByName("d11")[0].value
}
//提交按钮
function select_btn(){
	if(auto==true){
		reflush();
	}else{
		unautoreflush();
	}
}
//车型赋予颜色
function changecartypecolor(){
	var table = document.getElementById('table1');
	var rows = table.rows;
	var curname = rows[1].cells[1].innerHTML;;
	for(var i=1;i<rows.length;i++){
		if(curname==rows[i].cells[1].innerHTML){
			rows[i].cells[1].style.backgroundColor='#87CEFA';
		}else{
			rows[i].cells[1].style.backgroundColor='#AFEEEE';
		}
	}
}changecartypecolor();

</script>
<!-- InstanceEndEditable -->
<!-- InstanceEnd --></html>
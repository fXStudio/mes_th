<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="com.qm.th.tg.dao.*,com.qm.th.tg.factory.*" %>
<%@page import="com.qm.th.tg.bean.*,com.qm.mes.ra.util.*" %>
<%@page import="java.util.*,java.text.SimpleDateFormat" %>
<%@page	import="com.qm.mes.framework.*,com.qm.mes.framework.dao.*,com.qm.mes.system.dao.*"%>

<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html><!-- InstanceBegin template="/Templates/new_view.dwt.jsp" codeOutsideHTMLIsLocked="true" -->

<!-- InstanceBeginEditable name="获得连接" -->
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<script language="JavaScript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="获得过滤" -->
<%@page import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%  
    final  Log log = LogFactory.getLog("kis_welding.jsp");
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
    Connection con = null;
    WeldingSearchFactory factory_ws = new WeldingSearchFactory();//焊装查询工厂
    List<KisWelding> list_kw = new ArrayList<KisWelding>();//KIS焊装查询集合
    SearchSetFactory factory_ss = new SearchSetFactory();//查询设置工厂
    SearchSet ss = new SearchSet();//查询设置对象
    int searchsetid = 0;//查询设置序号
    String auto = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	try{
	    //获取连接
	    con=Conn.getConn();
	    if(request.getParameter("auto")==null||request.getParameter("auto").equals(""))
	    	auto = "true";
	    else
	    	auto = request.getParameter("auto");
	    log.debug("auto参数为："+auto);
	    searchsetid = Integer.parseInt(request.getParameter("searchsetid"));
	    ss = factory_ss.getSearchSetById(searchsetid,con);
		String sql=null;
		DAO_WeldingSearch dao = new DAO_WeldingSearch();
		list_kw = factory_ws.getKISWeldingByCartype(ss.getCcarType(),con);
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

<div class="title"><strong><!-- InstanceBeginEditable name="标题2" --><%=ss.getCdscCarType()+ss.getCdscFactory()%>焊装FIS查询<!-- InstanceEndEditable --></strong></div>
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
  <table border="0" cellspacing="0" cellpadding="0" >
  	<tr>
  	<td align="center" width="60">顺序号</td>
  	<td align="center" width="150">VIN</td>
  	<td align="center" width="110">KIN</td>
  	<td align="center" width="160">焊装上线</td>
  	</tr>
  	<%for(int i=0;i<list_kw.size();i++){%>
  	  <tr>
  	  	<td align="center"><%=list_kw.get(i).getSeq()==null?"-":list_kw.get(i).getSeq()%></td>
  	  	<td align="center"><%=list_kw.get(i).getVin()==null?"-":list_kw.get(i).getVin()%></td>
  	    <td align="center"><%=list_kw.get(i).getKin()==null?"-":list_kw.get(i).getKin()%></td>
  	    <td align="center"><%=list_kw.get(i).getDWBegin()==null?"-":sdf.format(sdf.parse(list_kw.get(i).getDWBegin()))%></td>
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
function reflush(){
	window.location.href="kis_welding.jsp?auto=true&searchsetid=<%=searchsetid%>";
}

var auto = <%=auto%>;
if(auto==true) {
	setTimeout(reflush,60000);
}
function unautoreflush(){
	window.location.href="kis_welding.jsp?auto=false&searchsetid=<%=searchsetid%>";
}
</script>
<!-- InstanceEndEditable -->
<!-- InstanceEnd --></html>
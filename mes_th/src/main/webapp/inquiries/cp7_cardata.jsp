<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="com.qm.th.tg.dao.*,com.qm.th.tg.factory.*" %>
<%@page import="com.qm.th.tg.bean.*,com.qm.mes.ra.util.*" %>
<%@page import="java.util.*,java.text.SimpleDateFormat" %>
<%@page	import="com.qm.mes.framework.*,com.qm.mes.framework.dao.*,com.qm.mes.system.dao.*"%>
<%@page import="java.util.Date"%>

<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<script language="JavaScript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<%@page import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<html>
<%   
    final  Log log = LogFactory.getLog("cp7_cardata.jsp");
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
    Connection con = null;
    Date date = new Date();
    String startTime = null;
    String endTime = null;
    int select = 1;
    CP7SearchFactory cp7_factory = new CP7SearchFactory();
    DAO_CP7Search dao = new DAO_CP7Search();
    List<CP7CarData> list_cp7 = new ArrayList<CP7CarData>();
    String sql = null;
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");  //年月日
    try{
    	if(request.getParameter("d11")==null)
    		startTime = simple.format(new Date());
    	else
    		startTime = request.getParameter("d11");
    	if(request.getParameter("d12")==null)
    		endTime = simple.format(new Date());
    	else
    		endTime = request.getParameter("d12");
    	con = Conn.getConn();
    		list_cp7 = cp7_factory.getCarDataStatByStartTimeEndTime(startTime,endTime,con);
    		sql = dao.getCarDataStatByStartTimeEndTime(startTime,endTime);
%>
<head>
<title>QAD车辆总成信息统计</title>
<meta http-equiv="Content-Language" content="zh-cn">
</head>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
<body>
  <div class="title" align="center"><strong>QAD车辆总成信息统计</strong></div>
  <form action="cp7_cardata.jsp">
    <div>
	  	开始时间
	 	<input id="d11" name="d11" type="text" value="<%=startTime%>"/>
	    <img onclick="WdatePicker({el:'d11',dateFmt:'yyyy-MM-dd'})" src="../My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
	    --结束时间
	  	<input id="d12" name="d12" type="text" value="<%=endTime%>"/>
	    <img onclick="WdatePicker({el:'d12',dateFmt:'yyyy-MM-dd'})" src="../My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
  	  <mes:button id="s12" reSourceURL="../JarResource/" value="总成统计" onclick="stat()"/>
  	  <mes:button id="s2" reSourceURL="../JarResource/" onclick="xls()" value="导出"/>
  	  </div>
  	<input type="hidden" name="select" value="<%=select%>">
  </form>
  <div align="center" id="div_stat">
  	  <style>
  	    td{border-width:1pt;border-style :solid}
  	  </style>
  <table border="0" cellspacing="0" cellpadding="0" width="340">
  	<tr>
   	  <td align="center" width="200">QAD总成号</td>
      <td align="center" width="50">数量</td>
  	</tr>
  	<%for(int i=0;i<list_cp7.size();i++){%>
  		<tr>
  		<td align="center"><%=list_cp7.get(i).getPart_no() %></td>
  		<td align="center"><%=(int)list_cp7.get(i).getNum()%></td>
  		</tr>
  	<%} %>
  </table>
  </div>
</body>
<%
    }catch(Exception e){
    	log.error("错误信息："+e.toString());
    	e.printStackTrace();
    }finally{
    	if(con != null){
    		con.close();
    		con = null;
    	}
    }
%>
<script type="text/javascript">
//统计
function stat(){
	window.location.href="cp7_cardata.jsp?d11="+document.getElementsByName("d11")[0].value+"&d12="+document.getElementsByName("d12")[0].value;
}
//导出
function xls(){
	window.location.href="cp7_cardata_xls.jsp?sql=<%=sql%>";
}
</script>
</html>
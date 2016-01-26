<%@ page language="java" import="java.sql.*"
	contentType="text/html;charset=gb2312"%>

<%@page import="com.qm.th.tg.factory.ChengduCarFactory"%>

<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES" />
<%@page
	import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%
	final Log log = LogFactory.getLog("chengdu_manage.jsp");
	response.setHeader("progma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	Connection con = null;
	String startDate = "";
	ChengduCarFactory factory = new ChengduCarFactory();
	try {
		//获取连接
		con = Conn.getConn();
		startDate = request.getParameter("startDate");
		factory.deleteChengduJettaByStartDate(startDate,con);
	} catch (Exception e) {
		e.printStackTrace();
		log.error("未知错误：" + e.toString());
	} finally {
		if (con != null) {
			con.close();
			con = null;
		}
	}
%>
<html>
<script type="text/javascript">
function back(){
	window.location.href="chengducar_manage.jsp?startDate=<%=startDate%>";
}back();
</script>
</html>

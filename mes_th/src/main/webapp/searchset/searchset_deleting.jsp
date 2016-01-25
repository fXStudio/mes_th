
<%@page import="th.tg.factory.SearchSetFactory"%><html><!-- InstanceBegin template="/Templates/new_处理提交.dwt.jsp" codeOutsideHTMLIsLocked="true" -->
<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="common.*"%>
<%@page import="java.util.*" %>
<!-- InstanceBeginEditable name="获得连接对象" -->
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<%@page import="mes.framework.*,mes.util.SerializeAdapter"%>

<%@page import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<!-- InstanceEndEditable -->

<%	
	SearchSetFactory factory = new SearchSetFactory(); 
    String int_id=null;
    Connection con = null;
    int intPage = 0;
    String info = null;
    final  Log log = LogFactory.getLog("searchset_deleting.jsp");
%>
<!-- InstanceBeginEditable name="配置“基础信息”和“自动获取”的参数名" -->
<%
	try{
		con = Conn.getConn();
	    int_id = request.getParameter("int_id");
	    intPage = Integer.parseInt(request.getParameter("intPage"));
	    info = request.getParameter("info");
	    log.debug("查询设置号："+request.getParameter("int_id"));
		factory.delSearchSetById(Integer.parseInt(int_id),con);
	}catch(Exception e)
	{
		e.printStackTrace();
	}finally{
			if(con!=null)con.close();
	}
%>
<script type="text/javascript">
function back(){
	window.location.href="searchset_manage.jsp?page=<%=intPage%>&info=<%=info%>";
}back();
</script>
<!-- InstanceEnd --></html>
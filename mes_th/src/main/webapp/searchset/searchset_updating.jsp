
<%@page import="com.qm.th.tg.factory.SearchSetFactory"%>
<%@page import="com.qm.th.tg.bean.SearchSet"%><html>
	<!-- InstanceBegin template="/Templates/new_处理提交.dwt.jsp" codeOutsideHTMLIsLocked="true" -->
	<%@ page language="java" import="java.sql.*"
		contentType="text/html;charset=gb2312"%>
	<%@page import="java.util.*"%>
	<%@page import="com.qm.mes.util.*"%>
<%@page import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
    <%@page import="com.qm.mes.system.dao.*,com.qm.mes.framework.*"%>
	<!-- InstanceBeginEditable name="获得连接对象" -->
	<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES" />
	<!-- InstanceEndEditable -->

	
<%	
		response.setHeader("Pragma","No-cache");  
   		response.setHeader("Cache-Control","no-cache");  
		Connection con = null;
		
		SearchSetFactory factory = new SearchSetFactory();
		SearchSet ss = new SearchSet();
 		String int_id = null;
 		int intPage = 0;
 		String info = null;
 		
		try {
			//获取资源
			con = Conn.getConn();
 			int_id = request.getParameter("int_id");
 			intPage = Integer.parseInt(request.getParameter("intpage"));
 			info = request.getParameter("info");
			ss.setCsearchName(request.getParameter("csearchname"));
			ss.setCwa(request.getParameter("cWA"));
			ss.setCfactory(request.getParameter("cFactory")==null?"":request.getParameter("cFactory"));
			ss.setCdscFactory(request.getParameter("cDscFactory")==null?"":request.getParameter("cDscFactory"));
			ss.setCcarType(request.getParameter("cCarType"));
			ss.setCdscCarType(request.getParameter("cDscCarType"));
			ss.setCremark(request.getParameter("cRemark")==null?"":request.getParameter("cRemark"));
			factory.updateSearchSetById(Integer.parseInt(int_id),ss,con);

		} catch (Exception e) {
			throw e;
		}finally{
		
			if (con != null)
				con.close();//释放资源
		}
	%>
	<!-- InstanceEnd -->
	<script type="text/javascript">
	function back(){
		window.location.href = "searchset_manage.jsp?page=<%=intPage%>&info=<%=info%>";
	}back();
	</script>
</html>
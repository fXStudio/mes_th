<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="th.tg.dao.*,th.tg.factory.*" %>
<%@page import="th.tg.bean.*,mes.ra.util.*" %>
<%@page import="java.util.*,java.text.SimpleDateFormat" %>

<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html><!-- InstanceBegin template="/Templates/new_view.dwt.jsp" codeOutsideHTMLIsLocked="true" -->

<!-- InstanceBeginEditable name="获得连接" -->
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="获得过滤" -->
<%@page import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%  
       String pageno = request.getParameter("pageno");
       List<th.tg.bean.Part> list_as = new ArrayList<th.tg.bean.Part>();
       
       if(pageno != null && !"".equals(pageno = pageno.trim())){
    	    StringBuilder sb = new StringBuilder();
    	    sb.append(" select partname, pageno,");
    	    sb.append(" dpcode + ' ' + dpdate +  ' ' + dpseqnum traceone,");
    	    sb.append(" partseq tracetwo, ");
    	    sb.append(" CONVERT(varchar(20),recorddate, 120) recorddate");
    	    sb.append(" from  v_pageno_part p");
    	    sb.append(" WHERE pageno = ?");
    	   
       	     Connection conn  = null;
       	     PreparedStatement stmt = null;
       	     ResultSet rs = null;
       	     
       	     try{
       	    	conn = Conn.getConn();
       	    	stmt = conn.prepareStatement(sb.toString());
       	    	stmt.setString(1, pageno);
       	        
       	    	rs = stmt.executeQuery();
       	    	
       	    	while(rs.next()){
       	    		th.tg.bean.Part part = new th.tg.bean.Part();
       	    		part.setPageno(rs.getString("pageno"));
       	    		part.setCode(rs.getString("partname"));
       				part.setPageno(rs.getString("pageno"));
       				part.setTraceone(rs.getString("traceone"));
       				part.setTracetwo(rs.getString("tracetwo"));
       				part.setProddate(rs.getString("recorddate"));
       				
       				list_as.add(part);
       	    	}
       	     } catch(SQLException e){
       	    	 e.printStackTrace();
       	     } finally {
       	    	 if(rs != null){
       	    		 try{
       	    			 rs.close();
       	    		 }catch(SQLException e){
       	    			 e.printStackTrace();
       	    		 } finally{
       	    			 rs = null;
       	    		 }
       	    	 }
       	    	 if(stmt != null){
       	    		 try{
       	    			stmt.close();
       	    		 }catch(SQLException e){
       	    			 e.printStackTrace();
       	    		 } finally{
       	    			stmt = null;
       	    		 }
       	    	 }
       	    	 if(conn != null){
       	    		 try{
       	    			conn.close();
       	    		 }catch(SQLException e){
       	    			 e.printStackTrace();
       	    		 } finally{
       	    			conn = null;
       	    		 }
       	    	 }
       	     }
       }
%>				
<head>
<!-- InstanceBeginEditable name="标题" -->
		<title>单车查询</title>
<!-- InstanceEndEditable -->
<meta http-equiv="Content-Language" content="zh-cn">
<style>
.head2{	height:22px;color:ffff00;
}
</style>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>

<body background="../images/background.jpg" style="overflow:scroll;">
<!-- 引用通用脚本 -->
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>

<div class="title"><strong><!-- InstanceBeginEditable name="标题2" -->配置单查询<!-- InstanceEndEditable --></strong></div>
	<br>
	<div align="center"><!-- InstanceBeginEditable name="内容1" -->
	
	  <form action="pagepart.jsp">
	  <table>
	  <tr><td>
	  	配置单号
	 	<input id="pageno" name="pageno" size="20" maxlength="12" type="text" value="<%=pageno == null ? "" : pageno%>"/>
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
  <table border="0" cellspacing="0" cellpadding="0" width="1090" style="margin-top:30px;">
  	<tr>
	  	<td width="50px" align="center">序号</td>
	  	<td width="100px" align="center">配货单号</td>
	  	<td width="160px" align="center">总成号</td>
	  	<td width="180px" align="center">追溯条码1</td>
	  	<td width="200px"   align="center">追溯条码2</td>
	  	<td width="160px" align="center">扫描时间</td>
  	</tr>
  	<%for(int i=0; i < list_as.size();i++){%>
  	  <tr>
  	  	<td align="center"><%=i+1%></td>
  	  	<td align="center"><%=list_as.get(i).getPageno()==null?"-":list_as.get(i).getPageno()%></td>
  	  	<td align="center"><%=list_as.get(i).getCode()==null?"-":list_as.get(i).getCode()%></td>
  	  	<td align="center"><%=list_as.get(i).getTraceone()==null?"-":list_as.get(i).getTraceone()%></td>
  	  	<td align="center"><%=list_as.get(i).getTracetwo()==null?"-":list_as.get(i).getTracetwo()%></td>
  	  	<td align="center"><%=list_as.get(i).getProddate()==null?"-":list_as.get(i).getProddate()%></td>
  	  </tr>
    <%} %>
  </table>
  </div>
</body>
</html>
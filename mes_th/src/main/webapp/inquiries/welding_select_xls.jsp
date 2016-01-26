<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@ page import="java.sql.*" %>
<%@page import="com.qm.mes.tg.dao.*,com.qm.mes.tg.factory.*,com.qm.mes.tg.bean.*" %>
<%@ page import ="com.qm.mes.framework.DataBaseType"%>
<%@ page import ="com.qm.th.tg.util.*"%>
<%@ page import = "com.qm.mes.system.dao.DAOFactoryAdapter"%>
<%@page	import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
	
<%	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); %>
<%
	
	response.reset();   
	response.setContentType("application/x-msdownload");
	final  Log log = LogFactory.getLog("welding_select_xls.jsp");
	request.setCharacterEncoding("gb2312");	
	//以下两句是可以生成想要的文件名.xls
	//excel文件的名
	String str = request.getParameter("downloadname");
	String type = "1";
	if(str.equals("焊装查询")){
		type = "1";
	}else{
		type = "2";
	}
	//sql语句
	String sql=request.getParameter("sql");
	String sql_part=request.getParameter("sql_part"); 
	String partnum = request.getParameter("partnum");
	log.debug(str + "sql：" + sql);
	log.debug(str + "零件sql：" + sql_part);
	log.debug(str + "partnum：" + partnum);
  
	response.setHeader("Content-Disposition",   "attachment;   filename="+new String((str+"_数据表.xls").getBytes(),"iso8859-1"));			
	Connection con = null;
	try {
	    con = Conn.getConn();
		ResultSet rs = null;
		ResultSet rs_part = null;
		if(sql!=null){
			rs=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(sql);
			rs_part=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(sql_part);
		}
		ServletOutputStream os=response.getOutputStream();
        WeldingExport.createExcelFile(rs,rs_part,os,partnum,type,con);
	} catch (Exception e){
		e.printStackTrace();
	}finally{
	    if(con!=null)
		con.close();
		out.clear();
        out =  pageContext.pushBody();
	}
%>

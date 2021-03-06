<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@ page import="java.sql.*" %>
<%@page import="mes.tg.dao.*,mes.tg.factory.*,mes.tg.bean.*" %>
<%@ page import ="mes.framework.DataBaseType"%>
<%@ page import ="th.tg.util.*"%>
<%@ page import = "mes.system.dao.DAOFactoryAdapter"%>
<%@page	import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
<%	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); %>
<%	
	response.reset();   
	response.setContentType("application/x-msdownload");
	final  Log log = LogFactory.getLog("assembly_select_xls.jsp");
	
	//以下两句是可以生成想要的文件名.xls
	//excel文件的名
	String str = request.getParameter("downloadname");
	String type = "1";
	if(str.equals("总装查询")){
		type = "1";
	}else{
		type = "2";
	}
	//sql语句
	String sql=request.getParameter("sql"); 
	log.debug(str + "sql：" + sql);
  
	response.setHeader("Content-Disposition",   "attachment;   filename="+new String((str+"_数据表.xls").getBytes(),"iso8859-1"));			
	Connection con = null;
	try {
	    con = Conn.getConn();
		ResultSet rs=null;
		
		if(sql!=null){
			rs=con.createStatement().executeQuery(sql);
		}
		ServletOutputStream os=response.getOutputStream();
        AssemblyExport.createExcelFile(rs,os,type,con);
	} catch (Exception e){
		e.printStackTrace();
	}finally{
	    if(con!=null)
		con.close();
		out.clear();
        out =  pageContext.pushBody();
	}
%>

<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="mes.util.*,java.io.*" %>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
<%	
	response.reset();   
	response.setContentType("application/x-msdownload");
	
	//下面这句是生成名为数据表.xls的文件
	//response.setHeader("Content-Disposition",   "attachment;   filename="+new String("数据表.xls".getBytes(),"iso8859-1"));
	
	//以下两句是可以生成想要的文件名.xls
	String str = request.getParameter("downloadname");
	response.setHeader("Content-Disposition",   "attachment;   filename="+new String((str+"_数据表.xls").getBytes(),"iso8859-1"));			
	Connection con = Conn.getConn();
	try {
		String cursql = (String)session.getAttribute("cursql");
		ResultSet rs = null;
		//System.out.println("read cursql :"+cursql);
		if(cursql!=null){
			rs = con.createStatement().executeQuery(cursql);
		}
		ServletOutputStream   os   =   response.getOutputStream();   
		ResultSetToExcel.createExcelFile(rs,os);
	} catch (Exception e){
		e.printStackTrace();
	}finally{
		con.close();
	}
%>

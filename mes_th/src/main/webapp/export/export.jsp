<%@ page language="java" pageEncoding="GBK" trimDirectiveWhitespaces="true"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.ResultSetMetaData"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="jxl.write.WritableWorkbook"%>
<%@ page import="jxl.write.WritableSheet"%>
<%@ page import="jxl.Workbook"%>
<%@ page import="jxl.write.Label"%>

<% 
    StringBuilder sql = new StringBuilder();
    sql.append(" SELECT SUBSTRING(iCarId, 3, 7) + '-' + SUBSTRING(iCarId, 10, 1) kin,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 0 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo0,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 1 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo1,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 2 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo2,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 3 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo3,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 4 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo4,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 5 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo5,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 6 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo6,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 7 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo7,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 8 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo8,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 9 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo9,");
    sql.append(" MAX(CASE WHEN ITFASSNameId = 10 THEN ISNULL(cQADNo, '') ELSE '' END) AS cQADNo10");
    sql.append(" FROM cardata_d");
    sql.append(" WHERE lastUnit='R100'");
    sql.append(" GROUP BY iCarId");
    sql.append(" ORDER BY kin");

%>

<% 
     response.setContentType("application/vnd.ms-excel");     
     response.setHeader("Content-disposition", "inline;filename=FATH.xls");
     
     Connection conn = null;
     PreparedStatement stmt = null;
     ResultSet rs = null;
     
     WritableWorkbook wb = Workbook.createWorkbook(response.getOutputStream());
     
     try{
    	 conn = new com.qm.th.helpers.Conn_MES().getConn();
    	 stmt = conn.prepareStatement(sql.toString());
    	 rs = stmt.executeQuery();
    	 
    	 ResultSetMetaData rsmd = rs.getMetaData();
    	    
    	 WritableSheet ws = wb.createSheet("FATH", 1);
    	 int rowNum = 0;
    	 while(rs.next()){
	   		   for(int i = 1; i <= rsmd.getColumnCount(); i++){
	   			   ws.addCell(new Label(i - 1, rowNum, rs.getString(rsmd.getColumnName(i))));
	   		   }
	   		   rowNum++;
    	 }
    	 wb.write();
    	 wb.close();
     } catch(SQLException e){
    	 e.printStackTrace();
     } finally{
    	 if(rs != null){
    		 try{
    			 rs.close();
    		 }catch(Exception e){
    			 e.printStackTrace();
    		 }finally{
    			 rs = null;
    		 }
    	 }
    	 if(stmt != null){
    		 try{
    			 stmt.close();
    		 }catch(Exception e){
    			 e.printStackTrace();
    		 }finally{
    			 stmt = null;
    		 }
    	 }
    	 if(conn != null){
   		     try{
                 conn.close();
             }catch(Exception e){
                 e.printStackTrace();
             }finally{
            	 conn = null;
             }
    	 }
     }
     response.flushBuffer();
%>
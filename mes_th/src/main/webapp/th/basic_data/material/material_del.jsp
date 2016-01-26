<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="com.qm.th.helpers.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>

<% 
     /** Ö÷¼üID */
     String id = request.getParameter("intId");
     /** Ò³Âë */
     String page_num = request.getParameter("page");

     Connection conn = null;
     PreparedStatement stmt = null;
     
     try{
         conn = new Conn_MES().getConn();
         stmt = conn.prepareStatement("DELETE FROM PARTLIST WHERE ID = ?");
         stmt.setString(1, id);
         
         stmt.execute();
     }catch(Exception e){
         e.printStackTrace();
     }finally{
         if(stmt != null){
             try{
                 stmt.close();
             }catch(SQLException e){
                 e.printStackTrace();
             }finally{
                 stmt = null;
             }
         }
         if(conn != null){
             try{
                 conn.close();
             }catch(SQLException e){
                 e.printStackTrace();
             }finally{
                 conn = null;
             }
         }
     }
     response.sendRedirect("material_manage.jsp?page=" + page_num);
%>
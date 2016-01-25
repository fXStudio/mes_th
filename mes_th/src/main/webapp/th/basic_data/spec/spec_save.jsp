<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>

<% 
     /** 零件 */
     String part = request.getParameter("part");
     /** 转换零件 */
     String convertPart = request.getParameter("convertPart");
     /** 备注 */
     String remark = request.getParameter("remark");

     String strSql = "INSERT INTO SPECPART (cPart, cConvertPart, cRemark) VALUES (?, ?, ?)";
     
     Connection conn = null;
     PreparedStatement stmt = null;
 
     try{
         conn = new Conn_MES().getConn();
         stmt = conn.prepareStatement(strSql);
         stmt.setString(1, part);
         stmt.setString(2, convertPart);
         stmt.setString(3, remark);
         
         stmt.execute();
     }catch(Exception e){
         e.printStackTrace();   
     }finally{
         // resource release
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
     response.sendRedirect("spec_manage.jsp");
%>
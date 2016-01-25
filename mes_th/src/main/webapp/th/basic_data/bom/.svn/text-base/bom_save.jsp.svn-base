<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>

<% 
     /** 天合零件号 */
     String tfass = request.getParameter("partdata_ctfass");
     /** 大众主零件号 */
     String vwmainpart = request.getParameter("primary");
     /** 大众子零件种类数量 */
     String vwsubparttype = request.getParameter("partdata_nvwsubparttype");
     /** 大众子零件号 */
     String vwsubpart = request.getParameter("sub");
     /** 大众子零件数量 */
     String vwsubpartquan = request.getParameter("partdata_nvwsubpartquan");
     /** 方案号 */
     String qadno = request.getParameter("plan_no");
     /** 是否过度车型 */
     String istempcar = request.getParameter("tempcar");
     /** 备注 */
     String remark = request.getParameter("remark");
     
     /** 更新一条BOM记录 */
     StringBuilder strSql = new StringBuilder();
     strSql.append(" INSERT INTO PARTDATA( ");
     strSql.append("    cTFASS, nTfassNum, cVWMainPart, cVWMainPartQuan, cVWMainPartType, nVWSubPartType, ");
     strSql.append("    cVWSubPart, nVWSubPartQuan, cQADNo, cIsTempCar, cRemark");
     strSql.append(" ) VALUES (?, 1, ?, 1, 1, ?, ?, ?, ?, ?, ?)");
     
     Connection conn = null;
     PreparedStatement stmt = null;
     ResultSet rs = null;

     try{
         conn = new Conn_MES().getConn();
         stmt = conn.prepareStatement(strSql.toString());
         stmt.setString(1, tfass);
         stmt.setString(2, vwmainpart);
         stmt.setString(3, vwsubparttype);
         stmt.setString(4, vwsubpart);
         stmt.setString(5, vwsubpartquan);
         stmt.setString(6, qadno);
         stmt.setString(7, istempcar == null ? "F" : "T");
         stmt.setString(8, remark);
         
         stmt.execute();
     }catch(Exception e){
         e.printStackTrace();
     }finally{
         // resources release
         if(rs != null){
             try{
                 rs.close();
             }catch(SQLException e){
                 e.printStackTrace();
             }finally{
                 rs = null;
             }
         }
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
     response.sendRedirect("bom_manage.jsp");
%>
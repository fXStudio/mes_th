<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.text.SimpleDateFormat"%>

<% 
       /** 页码 */
       String page_num = request.getParameter("page");
       /** KIN号 */
       String ccarno = request.getParameter("ccarno");
       /** 焊装上线时间 */
       String dwbegin = request.getParameter("dwbegin");
       /** 焊装顺序号 */
       String remark = request.getParameter("remark");
       
       Connection conn = null;
       PreparedStatement stmt = null;

       try{
           // 插入一条新数据
           String strSql = "INSERT INTO SPECIALKIN (CCARNO, DTODATE, CREMARK) VALUES (?, ?, ?)";
           // 数据库连接
           conn = new Conn_MES().getConn();
           stmt = conn.prepareStatement(strSql);
           stmt.setString(1, ccarno);
           stmt.setTimestamp(2, new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dwbegin).getTime()));
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
       response.sendRedirect("spec_kincode_search.jsp?page=" + page_num);
%>
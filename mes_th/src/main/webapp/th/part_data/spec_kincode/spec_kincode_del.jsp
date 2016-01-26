<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>

<% 
    /** KIN号 */
    String kinCode = request.getParameter("kinCode");
	/** 页码 */
	String page_num = request.getParameter("page");
    
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try{
        // 删除语句
        String strSql = "DELETE FROM SPECIALKIN WHERE CCARNO = ?";
        // 建立数据库连接
        conn = new Conn_MES().getConn();
        stmt = conn.prepareStatement(strSql);
        stmt.setString(1, kinCode);
        
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
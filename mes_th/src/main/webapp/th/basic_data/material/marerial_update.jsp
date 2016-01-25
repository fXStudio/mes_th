<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>

<% 
	/** 主键ID */
	String id = request.getParameter("intId");
	/** 页码 */
	String page_num = request.getParameter("page");
	/** 零件编码 */     
    String partno = request.getParameter("partcode");
    /** 零件类别 */
    String parttype = request.getParameter("parttype");
    /** 天合总成名称 */
    String fassname = request.getParameter("fassname");
    /** 备注 */
    String remark = request.getParameter("remark");
    
	/** 更新BOM表 */
	StringBuilder strSql = new StringBuilder();
	strSql.append(" UPDATE PARTLIST SET cPartNo=?, ");
	strSql.append(" cPartType=?, iTFASSNameId=?, ");
	strSql.append(" cRemark=? ");
	strSql.append(" WHERE ID = ?");
	
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try{
        conn = new Conn_MES().getConn();
        stmt = conn.prepareStatement(strSql.toString());
        stmt.setString(1, partno);
        stmt.setString(2, parttype);
        stmt.setString(3, fassname);
        stmt.setString(4, remark);
        stmt.setString(5, id);
        
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
    response.sendRedirect("material_manage.jsp?eid=" + id + "&page=" + page_num);
%>
<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>

<% 
	/** 页码 */
    String page_num = request.getParameter("page");
	/** 主键ID */
	String id = request.getParameter("intId");
	/** KIN号 */
    String ccarno = request.getParameter("ccarno");
    /** 焊装上线时间 */
    String dwbegin = request.getParameter("dwbegin");
    /** 焊装顺序号 */
    String remark = request.getParameter("remark");
	
	Connection conn = null;
	PreparedStatement stmt = null;
 
	try{
		conn = new Conn_MES().getConn();
		stmt = conn.prepareStatement("UPDATE SPECIALKIN SET DTODATE=?, CREMARK=? WHERE CCARNO=?");
		stmt.setString(1, dwbegin);
		stmt.setString(2, remark);
		stmt.setString(3, ccarno);
		
		stmt.executeUpdate();
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
	response.sendRedirect("spec_kincode_search.jsp?page=" + page_num + "&eid=" + id);
%>
<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>

<% 
	/** 主键ID */
	String id = request.getParameter("intId");
	/** 页码 */
	String page_num = request.getParameter("page");
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

	/** 更新BOM表 */
	StringBuilder strSql = new StringBuilder();
	strSql.append(" UPDATE PARTDATA SET cTFASS=?, ");
	strSql.append(" cVWMainPart=?, nVWSubPartType=?,");
	strSql.append(" cVWSubPart=?, nVWSubPartQuan=?, ");
	strSql.append(" cQADNo=?, cIsTempCar=?, cRemark=?");
	strSql.append(" WHERE ID = ?");
	
    Connection conn = null;
    PreparedStatement stmt = null;
    
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
        stmt.setString(9, id);
        
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
    response.sendRedirect("bom_manage.jsp?eid=" + id + "&page=" + page_num);
%>

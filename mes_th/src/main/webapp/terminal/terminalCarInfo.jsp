<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="common.Conn_MES"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
 
<% 
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	try{
		con = new Conn_MES().getConn();
		String sql="select * from car_info";
		stmt = con.prepareStatement(sql);
		rs = stmt.executeQuery();
		StringBuffer jsoncar = new StringBuffer();
		jsoncar.append("[");
		while(rs.next()){
				jsoncar.append("{'carname':'"
						+ rs.getString("car") + "','carvalue':"
						+ rs.getInt("id") + "}");
				jsoncar.append(",");
		}
		String json = jsoncar.toString().substring(0,
					jsoncar.toString().length() - 1)
					+ "]";
		response.getWriter().print(json);
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				stmt = null;
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
		if(con != null){
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				con = null;
			}
		}
	}
%>
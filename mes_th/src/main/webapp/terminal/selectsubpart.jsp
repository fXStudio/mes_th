<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="db.Terminal"%>
<%@ page import="common.Conn_MES"%>

<%
	String output = "";
	String start = request.getParameter("start");
	String limit = request.getParameter("limit");

	int index = Integer.parseInt(start);
	int pageSize = Integer.parseInt(limit);
	String condition = request.getParameter("tcontent");
	String begindate = request.getParameter("begindate");
	String enddate = request.getParameter("enddate");
	String t_condition = "";
	if (begindate != null && enddate != null) {
		t_condition = " where copertime between '" + begindate
				+ "' and '" + enddate + "'";
	}
	int rows = new Terminal().getSubPartCount(condition, t_condition);
	int total = rows;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		con = new Conn_MES().getConn();
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String sql = "select * from dbo.F_Unused_Subpart('" + condition
				+ "')" + t_condition;
		rs = stmt.executeQuery(sql);
		output = "{total:" + total + ",data:[";
		int aa = ((pageSize + index > total) ? total
				: (pageSize + index));
		if (index > 0) {
			rs.absolute(index);
		}
		for (int i = index; i < aa; i++) {
			if (rs.next()) {
				output += "{ccarno:'" + rs.getString("ccarno")
						+ "',cpartno:'" + rs.getString("cpartno")
						+ "',npartnum:'" + rs.getString("npartnum")
						+ "',ilastpartnum:'"
						+ rs.getString("ilastpartnum")
						+ "',cfilename:'" + rs.getString("cfilename")
						+ "',ctfasstype:'" + rs.getString("ctfasstype")
						+ "'}";

				if (i != aa - 1) {
					output += ",";
				}
			}
		}
		output += "]}";
		System.out.println(output);
		response.getWriter().write(output);
	} catch (Exception e) {
		out.print(e);
	} finally {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				stmt = null;
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				con = null;
			}
		}
	}
%>
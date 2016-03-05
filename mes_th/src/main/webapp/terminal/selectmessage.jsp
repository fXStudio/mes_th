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
	String cfilename = request.getParameter("tcfilename");
	int rows = new Terminal().getMessageCount(condition, cfilename);
	int total = rows;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		con = new Conn_MES().getConn();
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		String sql = "select * from dbo.F_Message_View('" + condition
				+ "','" + cfilename + "')";
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
						+ "',cparttype:'" + rs.getString("cparttype")
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
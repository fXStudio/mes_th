<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>

<%
	Connection con = null;
	try {
		con = new Conn_MES().getConn();
		String cPageNo = request.getParameter("cPageNo");
		String partname = request.getParameter("partname");
		String partno = request.getParameter("partno");
		String vin = request.getParameter("vin");
		String flag = request.getParameter("flag");
		if (flag == null)
			flag = "";
		Terminal ter = new Terminal();
		String emp = (String) session.getAttribute("username");
		if (flag.equals("1")) {
			int cou = ter.getTSeqRows(con, partno);
			if (cou > 0) {
				out.println("{success:false}");
				return;
			}
		}
		boolean isSucess = ter.insertPageNo_Part(con, partname, partno,
				cPageNo, emp, vin);

		out.println(!isSucess ? "{}" : "{partName:\"" + partname
				+ "\"}");
	} finally {

		if (con != null) {
			try {
				con.close();
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			} finally {
				con = null;
			}
		}
	}
%>

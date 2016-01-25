<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="db.Terminal"%>

<%
	Connection con = null;
	try {
		con = new Conn_MES().getConn();
		System.out.println("terminalSaveCarPage.jsp --- Open Connection");
		String car = request.getParameter("carinfo");

		String doorno = request.getParameter("doorname");

		Terminal ter = new Terminal();
		String emp = (String) session.getAttribute("username");

		String[] infos = (String[]) session.getAttribute("pagenos");
		boolean flag = ter.insertCarInfo(con, car, infos, doorno, emp);
		int existnum = ter.getCarSearchExist(con, car);
		if (existnum > 0) {
			ter.deleteCar_State(con, car);
			ter.insertCar_State(con, car, emp);
		} else {
			ter.insertCar_State(con, car, emp);
		}
		String json = "";
		if (flag == false)
			json = "{success:true,info:'数据添加失败'}";
		else
			json = "{success:true,msg:\'ok\'}";
		response.getWriter().print(json);
	} finally {
		if (con != null) {
			try {
				con.close();
				System.out.println("terminalSaveCarPage.jsp --- Close Connection");
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			} finally {
				con = null;
			}
		}
	}
%>
<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ page import = "java.sql.*" %>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES" />
<%
		int groupid = 1;
	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {// 当前最大组号
			conn = Conn.getConn();
			stmt = conn.prepareStatement("select max(iPrintGroupId) + 1 as printgroupid from printset");
			rs = stmt.executeQuery();
			
			if(rs.next()){
				groupid = rs.getInt("printgroupid");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					conn = null;
				}
			}
		}
%>

<html>
	<head>
  		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
	</head>
	<body>
		<% 
			try {
				conn = Conn.getConn();
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement("insert into printset (ccode, cDescrip, iPrintGroupId, cFactory, cCarType, cCarTypeDesc, cTFASSName, nPage, nPerTimeCount, cPrintMD, cVinRule, cseqno_a, ibigno, icarno,clastvin) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 1, 0,'')");
				
				for(int i = 0; i < request.getParameterValues("cCode").length; i++) {
					stmt.setString(1, request.getParameterValues("cCode")[i]);
					stmt.setString(2, request.getParameter("cDescrip"));
					stmt.setInt(3, groupid);
					stmt.setString(4, request.getParameter("cFactory"));
					stmt.setString(5, request.getParameter("cCarType"));
					stmt.setString(6, request.getParameterValues("cCarTypeDesc")[i]);
					stmt.setString(7, request.getParameterValues("cTFASSName")[i]);
					stmt.setString(8, request.getParameter("nPage"));
					stmt.setString(9, request.getParameter("nPerTimeCount"));
					stmt.setString(10, request.getParameterValues("cPrintMD")[i]);
					stmt.setString(11, request.getParameter("cVinRule"));
					
					stmt.addBatch();
				}
				stmt.executeBatch();
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						stmt = null;
					}
				}
				if (conn != null) {
					try {
						conn.setAutoCommit(false);
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						conn = null;
					}
				}
			}
		    out.write("<script type='text/javascript'>window.location='printSetView.jsp'</script>");
		%>
	</body>
</html>
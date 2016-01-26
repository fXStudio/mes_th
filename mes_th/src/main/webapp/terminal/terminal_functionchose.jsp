<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.qm.mes.util.tree.DataServer_UserManage"%>
<%@page import="com.qm.th.helper.Conn_MES"%>
<%@page import="com.qm.mes.util.tree.BuildTree"%>
<%@page import="com.qm.mes.util.tree.Function"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"+request.getServerName() + ":"+request.getServerPort() + path + "/";
		
			String []temp_code;
			String roleno = request.getParameter("roleno");
			Connection con = null;
			DataServer_UserManage ds = null;
			
			try{
				Conn_MES connmes = new Conn_MES();
				//获得连接
				con = connmes.getConn();
				ds = new DataServer_UserManage(con);
				String powerstring = ds.getPowerString(roleno);
				
				//BuildTree功能树的类名
				BuildTree bft = new BuildTree(con,powerstring);
				
				Function root = ds.getFuncitonInfo("1");
				String code = bft.expand_terminal(root);
				code = code.substring(0,code.length()-1);
				
				temp_code = code.split(",");
			} catch(Exception e){
				throw e;
			}
			finally {
				if(con!=null){
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
		<title>终端扫描</title>
	</head>
	<body>
		<script type="text/javascript">
				var obj = "<%=java.util.Arrays.toString(temp_code)%>";
				var bathpath = "<%=basePath%>";
				var arr = eval("{" + obj + "}");
				for(var i = 0; i < arr.length; i++){
					var subarr = arr[i].split(":");
					if(subarr.length == 2){
						var btn = document.createElement("button");
						btn.style.cssText = "width:100px;position:relative;left:90px;padding:5px 5px 5px 5px;background-color:#8EA8C6;border: 1px solid #4A637B;font-family: Tahoma, Arial, Helvetica, sans-serif;";
						btn.value = subarr[0];
						btn.link = bathpath + subarr[1];
						btn.onclick = function(){
							window.location.href = this.link;
						}
						document.body.appendChild(btn);
						document.body.appendChild(document.createElement("br"));
						document.body.appendChild(document.createElement("br"));
					}
				}
		</script>
	</body>
</html>

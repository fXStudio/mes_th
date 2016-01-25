<%@ page language="java" pageEncoding="UTF-8"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<% 
			response.setHeader("Pragma", "No-cache");  
			response.setDateHeader("Expires", 0);  
			response.setHeader("Cache-Control", "no-cache"); 
			String pageno=request.getParameter("pageno"); 
	
		%>
		<title>终端扫描</title>
		<script type="text/javascript" src="../ext-base.js"></script>
		<script type="text/javascript" src="../ext-all.js"></script>
		<script type="text/javascript">
			function abc(){
				document.getElementById("indtxt").focus();
			}
			function indKeyUp(){
					if(document.getElementById("indtxt").value == ""){
			 			return;
			 		}
			 		var entrycode = 13;
				 	if(entrycode == event.keyCode){
				 		var pageno = <%=pageno%>;
				 		var ind = document.getElementById("indtxt").value;
				 		if(ind != pageno){
				 			alert('您扫描的配货单条码不正确');
				 			return false;
				 		}else{
				 			var requestConfig = {
								url:"terminalPageState.jsp",//请求的服务器地址
								params:{
									//请求参数
									cPageNo:document.getElementById("indtxt").value
								}
							}
							Ext.Ajax.request(requestConfig);//发送请求
				 			location.href = "terminal.jsp";
				 		}
				 	}
			}
		</script>
	</head>
	<body onload="abc();">
	<div style="position:relative;float:left;">确认配货单条码:</div><div><input type="text" id="indtxt" onkeyup="indKeyUp();" size=30></div>
	</body>
</html>

<%@ page language="java" contentType="text/html;charset=gb2312"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<style>
			.alert_msg {
				overflow:hidden;
				font-style:italic;
				color:#da5a1b;
				font-weight:bolder;
				float:right;
				margin-right: -120px;
				background: URL('images/footbar.gif') no-repeat;
				width:120px;
				padding-left:10px;
			}
		</style>
		<script language="javascript" src="<%=basePath%>js/jquery-1.11.0.min.js"></script>
	</head>
	<body bottommargin="0" leftmargin="0" rightmargin="0" topmargin="0">
		<div>
			<a id="msg" class="alert_msg" href="<%=basePath%>th/stat/newCarList.jsp" target="_blank" onfocus="this.blur()">发现新车型</a>
		</div>
	</body>
	<script>
		$(document).ready(function(){
			 /**
			  * 以轮询的方式请求后台数据，知道后台数据被处理完为止
			  */
			(function(){
				var selobj = arguments.callee;
				/**
				 * 拉取最新的数据
				 */
				$.ajax({
				   	type: "POST",
				   	cache: false,
				   	url: "<%=basePath%>NewCarDataServlet?t=" + new Date(),
				   	timeout: 60 * 1000,
				   	success: function(msg){
				   	    // 提示条对象
						var obj = $('.alert_msg');
						// 显示提示条的动画事件
						obj.animate({marginRight: '0px'}, 2 * 1000);
						
						/**
						 * 绑定提示条事件
						 */
						obj.bind('click', function(){
						    // 提交一个请求，通知服务端已经有用户确认过了，可以删除文件了
							$.ajax({
								type: "GET", 
								url: "<%=basePath%>NewCarDataServlet?t=" + new Date(),
								success: function(msg){
									setTimeout(function(){selobj()}, 5 * 1000);
								}
							});
							// 将提示条一处页面
							obj.animate({marginRight: '-120px'}, 2 * 1000);
							// 取消绑定的Click事件
							obj.unbind('click');
						});
					},
					// 如果放生错误，则重新提交一次请求
					error: function(msg){
						selobj();
					}
				});
			})();
		});
	</script>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>终端扫描</title>
		<link rel="stylesheet" type="text/css" href="../resources/css/ext-all.css" />
		<script type="text/javascript" src="../ext-base.js"></script>
		<script type="text/javascript" src="../ext-all.js"></script>
		<script type="text/javascript" defer="defer">
			Ext.onReady(function() {
				Ext.QuickTips.init();
				Ext.BLANK_IMAGE_URL = "../resources/images/default/s.gif";
				Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	
				var query_form = new Ext.FormPanel({
					id : "query-form",
					labelWidth : 80,
					renderTo:Ext.getBody(),
					labelAlign : 'right',
					frame:true,
					width:305,
					height:228,
					bodyStyle : 'padding:5px 5px 0',
					defaults : {
						width : 150,
						msgTarget : 'side' // 验证信息显示右边
					},
					items : [{
							xtype : 'textfield',
							fieldLabel : '待发货车辆',
							name : "ind",
							id:"ind"
						},{
							xtype : 'textfield',
							fieldLabel : '计划外收货',
							name : "inp",
							//开启事件,查看API，确定是否需要开启,默认非自动开启
							enableKeyEvents:true,
							id:"inp",
							listeners:{
							 	'keyup':function(e){
							 		if(Ext.getCmp("inp").getValue() == ""){
							 			return;
							 		}
							 		var entrycode = 13;
								 	if(entrycode == event.keyCode){
								 		var requestConfig = {
												url:"terminalOSIOperator.jsp",//请求的服务器地址
												params:{
													//请求参数
													osin:Ext.getCmp("inp").getValue(),
													car:Ext.getCmp("ind").getValue()
												}
											}
										Ext.Ajax.request(requestConfig);//发送请求
										document.getElementById("inp").select();
								 	}
							 	}
							 }
						}],
						buttonAlign : 'center',
							minButtonWidth : 60,
							buttons:[
								{
									text : '装载完毕发出',
									id:'aa',
									handler:function(){
										if(Ext.getCmp("ind").getValue() == ""){
							 				return;
							 			}
										var requestConfig = {
														url:"terminalOSInState.jsp",//请求的服务器地址
														params:{
														    //请求参数
															car:Ext.getCmp("ind").getValue()
														}}
										Ext.Ajax.request(requestConfig);//发送请求
										alert('装载完毕已发出');
									}
								}
							],
						listeners:{
							 	'render':function(e){
							 		Ext.getCmp("ind").focus(true,true);
							 	}
							 }
						});
						var aa = document.getElementById("aa");
						aa.style.position = "relative";
						aa.style.bottom = "90px";
				});
			</script>
	</head>
	<body>
	</body>
</html>

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
					labelWidth : 70,
					renderTo:Ext.getBody(),
					labelAlign : 'right',
					frame:true,
					bodyStyle : 'padding:5px 5px 0',
					width:305,
					height:253,
					defaults : {
						width : 170,
						msgTarget : 'side' // 验证信息显示右边
					},
					items : [{
							xtype : 'textfield',
							fieldLabel : '待发货车辆',
							name : "ind",
							id:"ind"
						},{
							xtype : 'textfield',
							fieldLabel : '配货单条码',
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
												url:"terminalGetPNoState.jsp",//请求的服务器地址
												params:{
													//请求参数
													cPageNo:Ext.getCmp("inp").getValue(),
													car:Ext.getCmp("ind").getValue()
												},
												callback:function(options, success, response){
													var jsonTxt = response.responseText;
													
													var str=jsonTxt.split(",");
													//var jsonObj = eval(jsonTxt);
													//if(jsonObj == 0){
													if(str[1]>0){
														alert('此配货单已存在');
														return;
													}
													if(str[0] == 0){
														var _btn = confirm('未通过总成校验,是否装载?');
														if(!_btn){
															Ext.getCmp("inp").setValue("");
															Ext.getCmp("inp").focus(true,true);
														}else{
															
															var requestConfig = {
															url:"terminalCarPNoOperator.jsp",//请求的服务器地址
															params:{
															    //请求参数
																car:Ext.getCmp("ind").getValue(),
																pageno:Ext.getCmp("inp").getValue()
															},
															callback:function(options, success, response){
																 var jsonTxt= response.responseText;
																 var num = eval(jsonTxt);
																 Ext.getCmp('num').setValue('共'+ num +'个......');
																}
															}
															Ext.Ajax.request(requestConfig);//发送请求
															document.getElementById("inp").select();
															
															alert('此配货单准备装载');
														}
													}else{
														
														var requestConfig = {
														url:"terminalCarPNoOperator.jsp",//请求的服务器地址
														params:{
														    //请求参数
															car:Ext.getCmp("ind").getValue(),
															pageno:Ext.getCmp("inp").getValue()
														},
														callback:function(options, success, response){
															 var jsonTxt= response.responseText;
															 var num = eval(jsonTxt);
															 Ext.getCmp('num').setValue('共'+num+'个......');
															}
														}
														Ext.Ajax.request(requestConfig);//发送请求
														document.getElementById("inp").select();
														
														alert('此配货单准备装载');
													}												
												}
											}
											Ext.Ajax.request(requestConfig);//发送请求
								 	}
							 	}
							 }
						},{
							xtype : 'textfield',
							fieldLabel : '所属门号',
							name : "doornum",
							//开启事件,查看API，确定是否需要开启,默认非自动开启
							enableKeyEvents:true,
							id:"doornum",
							listeners:{
							 	'keyup':function(e){
							 		if(Ext.getCmp("doornum").getValue() == ""){
							 			return;
							 		}
							 		var entrycode = 13;
								 	if(entrycode == event.keyCode){
								 		var requestConfig = {
												url:"terminalDoorNo.jsp",//请求的服务器地址
												params:{
													//请求参数
													doornum:Ext.getCmp("doornum").getValue(),
													car:Ext.getCmp("ind").getValue()
												},
												callback:function(options, success, response){
													 Ext.getCmp("inp").focus(true,true);
												}
											}
											Ext.Ajax.request(requestConfig);//发送请求
											
								 	}
							 	}
							 }
						},{
							xtype : 'textfield',
							fieldLabel : '已装载数量',
							name : "num",
							id:"num",
							readOnly:true
						}],
							buttonAlign : 'center',
							minButtonWidth : 60,
							buttons:[
								{
									text : '开始进行装载',
									id:'aa',
									handler:function(){
										if(Ext.getCmp("ind").getValue() == ""){
							 				return;
							 			}
							 			var requestConfig = {
														url:"terminalCarExist.jsp",//请求的服务器地址
														params:{
														    //请求参数
															car:Ext.getCmp("ind").getValue()
														},
														callback:function(options,success,response){
															var jsonTxt = response.responseText;
															var jsonObj = eval(jsonTxt);
															if(jsonObj>0){
																alert('此车已确认进行装载');
																return;
															}else{
																var requestConfig = {
																	url:"terminalCarState.jsp",//请求的服务器地址
																	params:{
																		    //请求参数
																			car:Ext.getCmp("ind").getValue()
																		}}
																Ext.Ajax.request(requestConfig);//发送请求
																
																alert('确认进行装载');
															}
														}}
														Ext.Ajax.request(requestConfig);//发送请求
										
									}
								},{
									text : '取消前一个装载',
									id:'cannelOne',
									handler:function(){
										if(Ext.getCmp("ind").getValue() == ""){
											alert('请输入待发货车辆');
											Ext.getCmp("ind").focus(true,true);
							 				return;
							 			}
										var requestConfig = {
														url:"terminalCancelOne.jsp",//请求的服务器地址
														params:{
														    //请求参数
															car:Ext.getCmp("ind").getValue()
														},
														callback:function(options,success,response){
															var jsonTxt = response.responseText;
															//var jsonObj = eval(jsonTxt);
															var str=jsonTxt.split(",");
															if(str[0]==1){
																Ext.getCmp('num').setValue('共'+str[1].trim()+'个......');
																alert('已取消前一个装载');
															}
														}}
										Ext.Ajax.request(requestConfig);//发送请求
									}
								},{
									text : '取消全部装载',
									id:'cannelFull',
									handler:function(){
										if(Ext.getCmp("ind").getValue() == ""){
											alert('请输入待发货车辆');
											Ext.getCmp("ind").focus(true,true);
							 				return;
							 			}
										var requestConfig = {
														url:"terminalCancelFull.jsp",//请求的服务器地址
														params:{
														    //请求参数
															car:Ext.getCmp("ind").getValue()
														},
														callback:function(options,success,response){
															var jsonTxt = response.responseText;
															//var jsonObj = eval(jsonTxt);
															var str=jsonTxt.split(",");
															if(str[0]==1){
																Ext.getCmp('num').setValue('共'+str[1].trim()+'个......');
																alert('已取消全部装载');
															}
														}}
										Ext.Ajax.request(requestConfig);//发送请求
									}
								},{
									text : '查看装载数量',
									id:'viewnum',
									handler:function(){
										if(Ext.getCmp("ind").getValue() == ""){
											alert('请输入待发货车辆');
											Ext.getCmp("ind").focus(true,true);
							 				return;
							 			}
										var requestConfig = {
														url:"terminalViewNum.jsp",//请求的服务器地址
														params:{
														    //请求参数
															car:Ext.getCmp("ind").getValue()
														},
														callback:function(options,success,response){
															var jsonTxt = response.responseText;
															var jsonObj = eval(jsonTxt);
															
															Ext.getCmp('num').setValue('共'+jsonObj+'个......');
														}}
										Ext.Ajax.request(requestConfig);//发送请求
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
						aa.style.position = "absolute";
						aa.style.bottom = "80px";
						aa.style.left = "10px";
						var cannelOne = document.getElementById("cannelOne");
						cannelOne.style.position = "absolute";
						cannelOne.style.bottom = "80px";
						cannelOne.style.left = "105px";
						var cannelFull = document.getElementById("cannelFull");
						cannelFull.style.position = "absolute";
						cannelFull.style.bottom = "80px";
						cannelFull.style.left = "210px";
						var viewnum = document.getElementById("viewnum");
						viewnum.style.position = "absolute";
						// viewnum.style.bottom = "90px";
						viewnum.style.top = "170px";
						viewnum.style.left = "110px";
				});
			</script>
	</head>
	<body>
	</body>
</html>

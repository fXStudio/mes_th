<%@ page language="java" pageEncoding="UTF-8"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html> 
	<head>
		<title>终端扫描</title>
		<link rel="stylesheet" type="text/css" href="../resources/css/ext-all.css" />
		
		<script type="text/javascript" src="../ext-base.js"></script>
		<script type="text/javascript" src="../ext-all.js"></script>
		<script type="text/javascript" src="js/ValidationStatus.js"></script>
		<script type="text/javascript" defer="defer">
			Ext.onReady(function() {
			
				Ext.QuickTips.init();
			
				Ext.BLANK_IMAGE_URL = "../resources/images/default/s.gif";
			
				Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
				var query_form = new Ext.FormPanel({
					id : "query-form",
					labelWidth : 70,
					labelAlign : 'right',
					url : 'terminalPartNameCode.jsp',
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 5px 0',
					anchor : '100%',
					defaults : {
						width : 233,
						allowBlank : false,
						msgTarget : 'side' // 验证信息显示右边
					},
					defaultType : 'numberfield',
					items : [{
								xtype : 'textfield',
								id : 'partname',
								fieldLabel : '零件号',
								name : "partname",
								allowed:false,
								blankText : '零件号不能为空',
								emptyText : '请输入零件号'
							},{
								xtype : 'textfield',
								id : 'code',
								fieldLabel : '条码简码',
								name : "code",
								blankText : '条码简码不能为空',
								emptyText : '请输入条码简码'
							}],
					buttonAlign : 'right',
					minButtonWidth : 60,
					buttons : [{
						text : '确认',
						handler : function(btn) {
			
							var frm = this.ownerCt.form;
			
							if (frm.isValid()) {
			
								frm.submit({
									waitTitle : '请稍候',
									waitMsg : '正在添加数据,请稍候...',
									success : function(form, action) {
										if (action.result.msg == 'ok') {
											Ext.Msg.show(
													{
															title : '信息提示',
															msg : '数据添加成功',
															buttons : Ext.Msg.OK,
															icon : Ext.Msg.INFO
											});
											
										} else {
											Ext.Msg.show({
														title : '错误提示',
														msg : action.result.info,
														buttons : Ext.Msg.OK,
														icon : Ext.Msg.ERROR,
														width : 220
													});
										}
									},
									failure : function() {
										Ext.Msg.show({
													title : '错误提示',
													msg :'网络连接错误',
													buttons : Ext.Msg.OK,
													icon : Ext.Msg.ERROR
												});
									}
								});
							}
						}
					}, {
						text : '重置',
						handler : function() {
							this.ownerCt.form.reset();
						}
					}]
			
				});
			
				var window_query = new Ext.Window({
							title : '条码简码对照',
							width : 350,
							plain : true,
							resizable : false,
							autoHeight : true,
							modal : true,
							collapsible : true,
							closeAction : 'hide',
							items : [query_form]
						});
				
				window_query.show();
			});
</script>	
	</head>
	<body>
	</body>
</html>

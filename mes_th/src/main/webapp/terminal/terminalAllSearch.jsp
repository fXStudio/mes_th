<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>终端扫描</title>
		<link rel="stylesheet" type="text/css"
			href="../resources/css/ext-all.css" />

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
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 5px 0',
					anchor : '100%',
					defaults : {
						width : 233,

						msgTarget : 'side' // 验证信息显示右边
					},
					defaultType : 'numberfield',
					items : [{
								xtype : 'textfield',
								id : 'unlinecode',
								fieldLabel : '底盘号',
								name : "unlinecode"
							},{
								xtype : 'textfield',
								id : 'kincode',
								fieldLabel : 'KIN号',
								name : "kincode"
							},{
								xtype : 'textfield',
								id : 'partcode',
								fieldLabel : '总成零件号',
								name : "partcode"
							},{
								
								xtype : 'fieldset',
								id : 'check_one',
								checkboxToggle : true,
								labelWidth : 50,
								title : '特殊序列号',
								autoHeight : true,
								anchor : '95%',
								collapsed : true,
								items : [{
											xtype : 'textfield',
											width : 200,
											fieldLabel : '序列号',
											id:'seqnum',
											name : 'seqnum'
										}]
							
							},{
								
								xtype : 'fieldset',
								id : 'check_two',
								checkboxToggle : true,
								labelWidth : 50,
								title : '总成条码序列号',
								autoHeight : true,
								anchor : '95%',
								collapsed : true,
								items : [{
											xtype : 'textfield',
											width : 200,
											fieldLabel : '批次',
											name : 'dpcode',
											id:'dpcode'
										},{
											xtype : 'textfield',
											width : 200,
											fieldLabel : '日期',
											name : 'dpdate',
											id:'dpdate'
										},{
											xtype : 'textfield',
											width : 200,
											fieldLabel : '流水号',
											name : 'dpseqnum',
											id:'dpseqnum'
										}]
							
							},{
								xtype : "combo",
								fieldLabel : '查询状态',
			
								// 使用本地模式
								mode : "local",
								displayField : "statename",
								valueField : "statevalue",
			
								// 可改为只读
								readOnly : true,
			
								// 设置该属性，可让信息全部显示，而不只是query显示
								triggerAction : "all",
			
								allowBlank : false,
								emptyText : '请选择查询状态',
								blankText : '查询状态不能为空',
								id : "stateid",
			
								// 使用数据存储器
								store : new Ext.data.SimpleStore({
											fields : ["statename", "statevalue"],
											data : [['当前查询', "1"],
													['历史查询', "0"]]
										})				
					}],
					buttonAlign : 'right',
					minButtonWidth : 60,
					buttons : [{
						text : '确认',
						handler : function(btn) {
			
							var frm = this.ownerCt.form;
			
							if (frm.isValid()) {
								var unlinecode=Ext.getCmp("unlinecode").getValue();
								var kincode=Ext.getCmp("kincode").getValue();
								var partcode=Ext.getCmp("partcode").getValue();
								var seqnum=Ext.getCmp("seqnum").getValue();
								var dpcode=Ext.getCmp("dpcode").getValue();
								var dpdate=Ext.getCmp("dpdate").getValue();
								var dpseqnum=Ext.getCmp("dpseqnum").getValue();
								var stateid=Ext.getCmp("stateid").getValue();
						
								location.href = "/mes_th/terminal/terminalAllSeaPage.jsp?unlinecode="+unlinecode+"&kincode="+kincode+"&partcode="+partcode+"&seqnum="+seqnum+"&dpcode="+dpcode+"&dpdate="+dpdate+"&dpseqnum="+dpseqnum+"&stateid="+stateid;
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
							title : '追溯查询',
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

Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.menu.RangeMenu.prototype.icons = {
		gt : 'images/img/greater_then.png',
		lt : 'images/img/less_then.png',
		eq : 'images/img/equals.png'
	};
	Ext.grid.filter.StringFilter.prototype.icon = 'images/img/find.png';

	Ext.BLANK_IMAGE_URL = "../resources/images/default/s.gif";

	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

	var terminal_search = Ext.data.Record.create([{
				name : 'cpageno',
				type : 'string'
			}, {
				name : 'code',
				type : 'string'
			}, {
				name : 'cdescrip',
				type : 'string'
			}, {
				name : 'car',
				type : 'string'
			}, {
				name : 'printdate',
				type : 'date',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'partdate',
				type : 'date',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'cardate',
				type : 'date',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'outdate',
				type : 'date',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'name',
				type : 'string'
			}, {
				name : 'pagenostate',
				type : 'string'
			}]);

	var ds = new Ext.data.GroupingStore({

				proxy : new Ext.data.HttpProxy({
							url : 'terminalCPageNoSeaOperator.jsp'
						}),

				reader : new Ext.data.JsonReader({
							id : 'cpageno',
							totalProperty : 'total',
							root : 'data'
						}, terminal_search),
				sortInfo : {
					field : 'cpageno',
					direction : "DESC"
				},
				groupField : 'cdescrip'

			});

	var summary = new Ext.grid.GroupSummary();

	var filters = new Ext.grid.GridFilters({

				filters : [{
							type : 'string',
							dataIndex : 'cpageno'
						}]
			});
	var sm = new Ext.grid.CheckboxSelectionModel({
				header : "<div style=\"background-color:#eff0f2\">&#160;</div>"
			});
	sm.handleMouseDown = Ext.emptyFn;// 不响应MouseDown事件
	sm.on('rowselect', function(sm_, rowIndex, record) {// 行选中的时候\
				if (record.get("car") != "") {
					Ext.Msg.show({
								title : '系统提示',
								msg : '该配货单已装载',
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});
					grid.getSelectionModel().deselectRow(rowIndex);
					return false;
				}
				
			if (record.get("cdescrip") != "奥迪B8/Q5方向盘、安全气囊")
			{
				  if (record.get("pagenostate") != 1) {
					Ext.Msg.confirm('系统提示', '该配货单没有总成匹配，确认装载吗?', function(_btn) {

								if (_btn == "yes") {
									grid.getSelectionModel()
											.selectRow(rowIndex);
								} else {
									grid.getSelectionModel()
											.deselectRow(rowIndex);
								}
							});
				}
			}else {
									grid.getSelectionModel()
											.selectRow(rowIndex);
					   }
			}, this);
	var cm = new Ext.grid.ColumnModel([sm,
			// 加入序号列
			new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}),

			{
				dataIndex : 'cpageno',
				header : '配货单条码',
				id : 'cpageno',
				// 此列总显示,不可选
				hideable : false,
				sortable : true

			}, {
				dataIndex : 'code',
				header : '配货单号',
				id : 'code',
				// 此列总显示,不可选
				hideable : false,
				sortable : true

			}, {
				dataIndex : 'cdescrip',
				id : 'cdescrip',
				header : '配货单状态'

			}, {
				dataIndex : 'car',
				header : '车号',
				id : 'car',
				// 此列总显示,不可选
				hideable : false,
				sortable : true

			}, {

				id : 'printdate',
				dataIndex : 'printdate',
				header : '打印时间',
				sortable : true,
				width : 150,
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')

			}, {
				id : 'partdate',
				dataIndex : 'partdate',
				header : '总成校验完成时间',
				sortable : true,
				width : 150,
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
			}, {

				id : 'cardate',
				dataIndex : 'cardate',
				header : '装载完成时间',
				sortable : true,
				width : 150,
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')

			}]);

	var grid = new Ext.grid.EditorGridPanel({
		iconCls : "icon-grid",
		ds : ds,
		cm : cm,
		sm : sm,// 设置行选择模式
		region : 'center',
		loadMask : true,
		plugins : [filters, summary],
		view : new Ext.grid.GroupingView({

			groupByText : '按列分组',
			showGroupsText : '采用分组模式',
			hideGroupedColumn : true
				// 隐藏分组列

			}),

		el : 'searchManager',
		tbar : ["->", {
			text : '准备装载',
			iconCls : "x-tbar-put",
			handler : function() {
				var _sm = grid.getSelectionModel();
				var record = _sm.getSelected();
				try {
					if (_sm.getCount() == 0) {
						throw Error('请选定配货单进行操作');
					}

					var msg = '';
					var pagenos = '';
					var cell = grid.getSelectionModel().each(function(rec) {

						msg = msg + rec.get('code') + " : " + rec.get('name')
								+ ',';
						pagenos = pagenos + rec.get('cpageno') + ',';

					});
					
					msg = msg.substring(0, msg.length - 1);
					pagenos = pagenos.substring(0, pagenos.length - 1);
					var bpanel = new Ext.Panel({
								height : 150,// 设置面板的高度
								width : 330,// 设置面板的宽度
								autoScroll : true,// 自动显示滚动条
								collapsible : true,// 允许展开和收缩
								bodyStyle : 'background-color:#FFFFFF',// 设置面板体的背景色
								border : false
							});
					var query_form = new Ext.FormPanel({
						labelWidth : 70,
						labelAlign : 'right',
						url : 'terminalSaveCarPage.jsp',
						baseCls : 'x-plain',
						bodyStyle : 'padding:5px 5px 0',
						anchor : 350,
						defaults : {
							width : 233,
							allowBlank : false,
							msgTarget : 'side' // 验证信息显示右边
						},
						defaultType : 'numberfield',
						items : [{
							xtype : "combo",
							id : "car",
							fieldLabel : '车号',

							// 使用本地模式
							mode : "local",
							displayField : "carname",
							valueField : "carname",

							// 设置该属性，可让信息全部显示，而不只是query显示
							triggerAction : "all",
							allowBlank : false,
							emptyText : '请选择车号',
							blankText : '车号不能为空',

							hiddenName : "carinfo",

							// 使用数据存储器
							store : new Ext.data.Store({
										autoLoad : true,
										proxy : new Ext.data.HttpProxy({
													url : 'terminalCarInfo.jsp'
												}),
										reader : new Ext.data.JsonReader({
													fields : ['carname',
															'carvalue']

												})
									})
						}, {
							xtype : "combo",
							id : 'doorno',
							fieldLabel : '所属门号',

							// 使用本地模式
							mode : "local",
							displayField : "doorname",
							valueField : "doorvalue",

							// 可改为只读
							readOnly : true,

							// 设置该属性，可让信息全部显示，而不只是query显示
							triggerAction : "all",

							allowBlank : false,
							emptyText : '请选择所属门号',
							blankText : '所属门号不能为空',
							hiddenName : "doorname",

							// 使用数据存储器
							store : new Ext.data.SimpleStore({
										fields : ["doorname", "doorvalue"],
										data : [['1号门', "1"], ['2号门', "2"],
												['3号门', "3"]]
									})

						}],
						buttonAlign : 'right',
						minButtonWidth : 60,
						buttons : [{
							text : '确认',
							handler : function(btn) {

								var frm = this.ownerCt.form;
								var car = frm.findField('car').getValue();

								if (frm.isValid()) {

									frm.submit({
										waitTitle : '请稍候',
										waitMsg : '正在添加数据,请稍候...',
										success : function(form, action) {
											if (action.result.msg == 'ok') {
												Ext.Msg.show({
															title : '信息提示',
															msg : '数据添加成功',
															buttons : Ext.Msg.OK,
															icon : Ext.Msg.INFO
														});

												grid.getStore().commitChanges();
												grid.getSelectionModel()
														.clearSelections();
												ds.reload();
												window_bpanel.close();
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
														msg : '网络连接错误',
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
					var window_bpanel = new Ext.Window({
								title : '整车装载确认',
								width : 350,
								plain : true,
								resizable : false,
								autoHeight : true,
								modal : true,
								collapsible : true,
								closeAction : 'close',
								listeners : {
									"close" : function() {
										window.close();
									}
								},
								items : [bpanel, query_form]
							});

					window_bpanel.show();
					bpanel.load({
								url : 'terminalCPageNoInfo.jsp',
								params : {
									pagenolist : msg,
									pagenos : pagenos
								}
							});
				} catch (_err) {

					Ext.Msg.show({
								title : '系统提示',
								msg : _err.description,
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});
				}
			}
		}],
		bbar : new Ext.PagingToolbar({
					store : ds,
					pageSize : 500,
					plugins : filters,
					emptyMsg : '没有任何记录'
				})
	});

	var viewport = new Ext.Viewport({
				region : 'center',
				border : 'layout',
				frame : true,
				layout : 'border',
				defaults : {
					collapsible : true,
					split : true
				},
				items : [{
							title : '配货单状态查询',
							region : 'center',
							layout : 'border',
							items : [grid]
						}]

			});

	ds.load({
				params : {
					start : 0,
					limit : 500
				}
			});

});
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
				name : 'pageno',
				type : 'string'
			}, {
				name : 'partdate',
				type : 'date',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'partemp',
				type : 'string'
			}, {
				name : 'carpagenodate',
				type : 'date',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'carpagenoemp',
				type : 'string'
			}, {
				name : 'car',
				type : 'string'
			}, {
				name : 'cardate',
				type : 'date',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'caremp',
				type : 'string'
			}, {
				name : 'outrecorddate',
				type : 'date',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'outemp',
				type : 'string'
			}]);

	var ds = new Ext.data.Store({

				proxy : new Ext.data.HttpProxy({
							url : 'terminalSeaOperator.jsp'
						}),

				reader : new Ext.data.JsonReader({
							id : 'pageno',
							totalProperty : 'total',
							root : 'data'
						}, terminal_search)
			});

	var filters = new Ext.grid.GridFilters({

				filters : [{
							type : 'string',
							dataIndex : 'pageno'
						}]
			});

	var cm = new Ext.grid.ColumnModel([

	{
				dataIndex : 'pageno',
				header : '配货单号',
				id : 'pageno',
				// 此列总显示,不可选
				hideable : false,
				sortable : true

			}, {

				id : 'partdate',
				dataIndex : 'partdate',
				header : '总成匹配完成时间',
				sortable : true,
				width : 150,
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')

			}, {
				dataIndex : 'partemp',
				header : '总成匹配完成操作员',
				id : 'partemp',
				width : 130,
				sortable : true
			},{

				id : 'car',
				dataIndex : 'car',
				header : '车号',
				sortable : true

			}, {

				id : 'cardate',
				dataIndex : 'cardate',
				header : '车辆装载完成时间',
				width : 150,
				sortable : true,
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')

			}, {
				dataIndex : 'caremp',
				header : '车辆装载完成操作员',
				id : 'caremp',
				width : 150,
				sortable : true
			}, {

				dataIndex : 'outemp',
				header : '车辆发货操作员',
				id : 'outemp',
				sortable : true
			}, {

				id : 'outrecorddate',
				dataIndex : 'outrecorddate',
				header : '车辆发货时间',
				width : 150,
				sortable : true,
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')

			}]);

	var grid = new Ext.grid.EditorGridPanel({
		iconCls : "icon-grid",
		ds : ds,
		cm : cm,
		region : 'center',
		loadMask : true,
		plugins : [filters],

		sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),

		el : 'searchManager',

		bbar : new Ext.PagingToolbar({
					store : ds,
					pageSize : 10,
					plugins : filters,
					emptyMsg : '没有任何记录'
				}),

		listeners : {
			'rowclick' : function(grid, rowIndex) {
				ds_dail.baseParams.uid = grid.getStore().getAt(rowIndex).data.pageno;
				var requestConfig = {
					url : "terminalPNoPrintDate.jsp",// 请求的服务器地址
					params : {
						// 请求参数

						pageno : ds_dail.baseParams.uid
					},
					callback : function(options, success, response) {
						var jsonTxt = response.responseText;

						Ext.getCmp("dail").setTitle("整车发货完成-总成匹配扫描详细信息[打印时间："
								+ jsonTxt + "]");
					}
				};
				Ext.Ajax.request(requestConfig);// 发送请求
				ds_dail.load({
							params : {
								start : 0,
								limit : 5
							}
						});

			}
		}

	});

	var ds_dail = new Ext.data.Store({
				url : 'terminalDailOperator.jsp',
				/*
				 * sortInfo : { field : 'functionName', direction : 'ASC' },
				 */
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'data'
						}, [{
									name : 'partname',
									type : 'string'
								}, {
									name : 'partseq',
									type : 'string'
								}, {
									name : 'dpcode',
									type : 'string'
								}, {
									name : 'dpdate',
									type : 'string'
								}, {
									name : 'dpseqnum',
									type : 'string'
								}, {
									name : 'recorddate',
									type : 'date',
									dateFormat : 'Y-m-d H:i:s'
								}, {
									name : 'emp',
									type : 'string'
								}, {
									name : 'vin',
									type : 'string'
								}])
			});
	var dail_filters = new Ext.grid.GridFilters({

				filters : [{
							type : 'string',
							dataIndex : 'partname'
						}, {
							type : 'string',
							dataIndex : 'dpcode'
						}, {
							type : 'string',
							dataIndex : 'dpdate'
						}, {
							type : 'string',
							dataIndex : 'dpseqnum'
						}]
			});
	terminalDailPanel = Ext.extend(Ext.grid.GridPanel, {
				constructor : function() {
					terminalDailPanel.superclass.constructor.call(this, {
								loadMask : {
									msg : '数据加载中...'
								},
								plugins : [dail_filters],
								cm : new Ext.grid.ColumnModel([{
											header : '零件号',
											id : 'partname',
											sortable : true,
											dataIndex : 'partname'
										}, {
											header : '底盘号',
											id : 'vin',
											sortable : true,
											dataIndex : 'vin',
											width : 200
										}, {
											header : '方向盘/气囊序列号',
											id : 'partseq',
											sortable : true,
											dataIndex : 'partseq',
											width : 200
										}, {
											header : '批次',
											id : 'dpcode',
											sortable : true,
											dataIndex : 'dpcode'
										}, {
											header : '日期',
											id : 'dpdate',
											sortable : true,
											dataIndex : 'dpdate'
										}, {
											header : '流水号',
											id : 'dpseqnum',
											sortable : true,
											dataIndex : 'dpseqnum'
										}, {
											header : '扫描零件号操作员',
											id : 'emp',
											sortable : true,
											dataIndex : 'emp',
											width : 130
										}, {
											id : 'recorddate',
											dataIndex : 'recorddate',
											header : '扫描零件号时间',
											sortable : true,
											renderer : Ext.util.Format
													.dateRenderer('Y-m-d H:i:s'),
											width : 200
										}]),
								ds : ds_dail,
								sm : new Ext.grid.RowSelectionModel({
											singleSelect : true
										}),
								bbar : new Ext.PagingToolbar({
											pageSize : 5,
											store : ds_dail,
											displayInfo : true,
											displayMsg : '第 {0} - {1} 条 共 {2} 条',
											emptyMsg : '没有记录'
										})
							});
				}
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
							title : '整车发货完成-配货单追踪查询',
							region : 'center',
							layout : 'border',
							items : [grid]
						}, {
							id : 'dail',
							region : 'south',
							layout : 'fit',
							title : '整车发货完成-总成匹配扫描详细信息',
							height : 200,
							items : [new terminalDailPanel()]
						}]

			});

	ds.load({
				params : {
					start : 0,
					limit : 10
				}
			});

});
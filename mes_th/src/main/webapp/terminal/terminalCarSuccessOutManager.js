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
						name : 'car',
						type : 'string'
					},  {
						name : 'recorddate',
						type : 'date',
						dateFormat : 'Y-m-d H:i:s'
					}, {
						name : 'emp',
						type : 'string'
					}]);

			var ds = new Ext.data.Store({

						proxy : new Ext.data.HttpProxy({
									url : 'terminalCarStateOperator.jsp'
								}),

						reader : new Ext.data.JsonReader({
									id : 'car',
									totalProperty : 'total',
									root : 'data'
								}, terminal_search)
					});

			var filters = new Ext.grid.GridFilters({

						filters : [{
									type : 'string',
									dataIndex : 'car'
								}]
					});

			var cm = new Ext.grid.ColumnModel([

			{
						dataIndex : 'car',
						header : '车号',
						id : 'car',
						// 此列总显示,不可选
						hideable : false,
						sortable : true

					},  {

						id : 'recorddate',
						dataIndex : 'recorddate',
						header : '装载完成时间',
						sortable : true,
						width : 150,
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')

					}, {
						dataIndex : 'emp',
						header : '装载完成操作员',
						id : 'emp',
						width : 130,
						sortable : true
					}]);

			var grid = new Ext.grid.EditorGridPanel({
						iconCls : "icon-grid",
						ds : ds,
						cm : cm,
						tbar : ["->",{
						
							text:'确认发货',
							tooltip : '确认发货',
							iconCls : "x-tbar-keygo",
							handler:function(){
								
								
								var _sm = grid.getSelectionModel();
		
								try {
									if (_sm.getCount() == 0) {
										throw Error('请选定一辆车进行发货');
									}
									var car = _sm.getSelected().data.car;
									
									var requestConfig = {
												url:"terminalCarSuccessOut.jsp",//请求的服务器地址
												params:{
													
													//请求参数
													recorddate:_sm.getSelected().data.recorddate.format('Y-m-d H:i:s')
												},
												callback:function(options, success, response){
															Ext.Msg.show({
																title : '系统提示',
																msg : '车辆已发货',
																buttons : Ext.Msg.OK,
																icon : Ext.Msg.WARNING,
																shadow:false
															});
															ds.reload();
															ds_dail.removeAll();
															ds_dail.baseParams.uid="";									
												}
									}
									Ext.Ajax.request(requestConfig);//发送请求
									
								} catch (_err) {
		
									Ext.Msg.show({
												title : '系统提示',
												msg : _err.description,
												buttons : Ext.Msg.OK,
												icon : Ext.Msg.WARNING,
												shadow:false
											});
								}

							}
						},"-",{
						
							text:'撤销装载',
							tooltip : '撤销装载',
							iconCls : "x-tbar-keydelete",
							handler:function(){
								
								
								var _sm = grid.getSelectionModel();
		
								try {
									if (_sm.getCount() == 0) {
										throw Error('请选定一辆车进行撤销');
									}
									var car = _sm.getSelected().data.car;
									var requestConfig = {
												url:"terminalCarDestrop.jsp",//请求的服务器地址
												params:{
													//请求参数
													car:car
												},
												callback:function(options, success, response){
															Ext.Msg.show({
																title : '系统提示',
																msg : '车辆已撤销',
																buttons : Ext.Msg.OK,
																icon : Ext.Msg.WARNING,
																shadow:false
															});
															ds.reload();
															ds_dail.removeAll();
															ds_dail.baseParams.uid="";									
												}
									}
									Ext.Ajax.request(requestConfig);//发送请求
									
								} catch (_err) {
		
									Ext.Msg.show({
												title : '系统提示',
												msg : _err.description,
												buttons : Ext.Msg.OK,
												icon : Ext.Msg.WARNING,
												shadow:false
											});
								}

							}
						}],
						region : 'center',
						loadMask : true,
						plugins : [filters],
						autoExpandColumn : 'emp',
						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),

						el : 'carSuccessOutManager',

						bbar : new Ext.PagingToolbar({
									store : ds,
									pageSize : 10,
									plugins : filters,
									emptyMsg : '没有任何记录'
								}),

						listeners : {
							'rowclick' : function(grid, rowIndex) {
								ds_dail.baseParams.uid = grid.getStore()
										.getAt(rowIndex).data.car;
							
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
				url : 'terminalCarDailOperator.jsp',
				/*
				 * sortInfo : { field : 'functionName', direction : 'ASC' },
				 */
				reader : new Ext.data.JsonReader({
							id:'pageno',
							totalProperty : 'total',
							root : 'data'
						}, [{
									name : 'pageno',
									type:'string'
								},{
									name:'doorno',
									type:'string'
								},{
									name : 'recorddate',
									type : 'date',
									dateFormat : 'Y-m-d H:i:s'
								},{
									name:'emp',
									type:'string'
								}])
			});
			var dail_filters = new Ext.grid.GridFilters({
						filters : [{
									type : 'string',
									dataIndex : 'pageno'
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
											header : '配货单号',
											id : 'pageno',
											sortable : true,
											dataIndex : 'pageno'
										},{
											header : '所属门号',
											id : 'doorno',
											sortable : true,
											dataIndex : 'doorno'
										},{
											header : '装载中扫描操作员',
											id : 'emp',
											sortable : true,
											dataIndex : 'emp',
											width:130
										},{
											id : 'recorddate',
											dataIndex : 'recorddate',
											header : '装载中扫描时间',
											sortable : true,
											renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
										}]),
								autoExpandColumn : 'recorddate',
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
			var gridobj = new terminalDailPanel();
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
									title : '整车发货',
									region : 'center',
									layout : 'border',
									items : [grid]
								}, {
									id:'dail',
									region : 'south',
									layout : 'fit',
									title : '装载配货单详细信息',
									height : 200,
									items : [gridobj]
								}]

					});

			ds.load({
						params : {
							start : 0,
							limit : 10
						}
					});

		});
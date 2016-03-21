Ext.onReady(function() {

			Ext.QuickTips.init();

			Ext.menu.RangeMenu.prototype.icons = {
				gt : 'images/img/greater_then.png',
				lt : 'images/img/less_then.png',
				eq : 'images/img/equals.png'
			};
			Ext.grid.filter.StringFilter.prototype.icon = 'images/img/find.png';

			Ext.BLANK_IMAGE_URL = "../resources/images/default/s.gif";

			var mainpart = Ext.data.Record.create([{
						name : 'cfilename',
						type : 'string'
					}]);
			var ds = new Ext.data.Store({

						proxy : new Ext.data.HttpProxy({
									url : 'selectmainpart.jsp'
								}),

						reader : new Ext.data.JsonReader({
									totalProperty : 'total',
									root : 'data'
								}, mainpart)
					});
			var cm = new Ext.grid.ColumnModel([// 加入序号列
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 35
							}), {
						dataIndex : 'cfilename',
						header : '报文名称',
						// 此列总显示,不可选
						hideable : false,
						width : 400,
						sortable : true

					}]);
			var grid = new Ext.grid.EditorGridPanel({
						ds : ds,
						cm : cm,
						region : 'center',
						loadMask : true,
						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),
						bbar : new Ext.PagingToolbar({
									store : ds,
									pageSize : 500,
									emptyMsg : '没有任何数据',
									displayInfo : true
								}),
						tbar : ['文件类型：', new Ext.form.TextField({
											id : 'content'
										}), {}, new Ext.Button({
											text : '开始查询',
											pressed : true,
											handler : function() {
												var content = Ext
														.getCmp("content")
														.getValue();
												ds.baseParams.tcontent = content;
												ds.reload();
											}
										})],
						listeners : {
							'rowclick' : function(grid, rowIndex) {
								var content = Ext.getCmp("content").getValue();
								ds_message.baseParams.tcontent = content;
								ds_message.baseParams.tcfilename = grid
										.getStore().getAt(rowIndex).data.cfilename;
								ds_message.load({
											params : {
												start : 0,
												limit : 500
											}
										});

							}
						}

					});
			var ds_message = new Ext.data.Store({
						url : 'selectmessage.jsp',
						reader : new Ext.data.JsonReader({
									totalProperty : 'total',
									root : 'data'
								}, [{
											name : 'ccarno'

										}, {
											name : 'cpartno'
										}, {
											name : 'npartnum'
										}, {
											name : 'ilastpartnum'
										}, {
											name : 'cparttype'
										}, {
											name : 'ctfasstype'
										}])
					});

			messagePanel = Ext.extend(Ext.grid.GridPanel, {
						constructor : function() {
							messagePanel.superclass.constructor.call(this, {
										loadMask : {
											msg : '正在进行报文展开'
										},
										cm : new Ext.grid.ColumnModel([{
													header : 'KIN号',
													dataIndex : 'ccarno'
												}, {
													header : '零件号',
													dataIndex : 'cpartno'
												}, {
													header : '零件数量',
													dataIndex : 'npartnum'
												}, {
													header : '剩余数量',
													dataIndex : 'ilastpartnum'
												}, {
													header : '零件类型',
													dataIndex : 'cparttype'
												}, {
													header : '零件描述',
													dataIndex : 'ctfasstype'
												}]),
										ds : ds_message,
										sm : new Ext.grid.RowSelectionModel({
													singleSelect : true
												}),
										bbar : new Ext.PagingToolbar({
													pageSize : 500,
													store : ds_message,
													displayInfo : true,
													emptyMsg : '没有任何数据'
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
									title : '未被消耗主零件',
									region : 'center',
									layout : 'border',
									items : [grid]
								}, {
									region : 'south',
									layout : 'fit',
									title : '报文展开',
									height : 300,
									items : [new messagePanel()]
								}]

					});
			ds.load({
						params : {
							start : 0,
							limit : 500
						}
					});
		});
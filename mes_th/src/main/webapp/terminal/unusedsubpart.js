Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.menu.RangeMenu.prototype.icons = {
		gt : 'images/img/greater_then.png',
		lt : 'images/img/less_then.png',
		eq : 'images/img/equals.png'
	};
	Ext.grid.filter.StringFilter.prototype.icon = 'images/img/find.png';

	Ext.BLANK_IMAGE_URL = "../resources/images/default/s.gif";
	var summary = new Ext.grid.GroupSummary();
	var subpart = Ext.data.Record.create([{
				name : 'ccarno'

			}, {
				name : 'cpartno'
			}, {
				name : 'npartnum'
			}, {
				name : 'ilastpartnum',
				type : 'int'
			}, {
				name : 'cfilename'
			}, {
				name : 'ctfasstype'
			}]);
	var ds = new Ext.data.GroupingStore({

				proxy : new Ext.data.HttpProxy({
							url : 'selectsubpart.jsp'
						}),

				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'data'
						}, subpart),
				sortInfo : {
					field : 'cpartno',
					direction : "ASC"
				},
				groupField : 'cpartno'
			});
	var cm = new Ext.grid.ColumnModel([// 加入序号列
			new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}), {
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
				dataIndex : 'ilastpartnum',
				summaryType : 'sum',
				summaryRenderer : function(v, params, data) {
					return '<b>合计：' + v + '个</b>';
				},
				width : 200
			}, {
				header : '报文名称',
				dataIndex : 'cfilename',
				width : 400
			}, {
				header : '零件描述',
				dataIndex : 'ctfasstype'
			}]);
	var grid = new Ext.grid.EditorGridPanel({
		ds : ds,
		cm : cm,
		region : 'center',
		loadMask : true,
		sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
		view : new Ext.grid.GroupingView({
			groupByText : '按列分组',
			showGroupsText : '采用分组模式',
			hideGroupedColumn : true
				// 隐藏分组列
			}),
		plugins : [summary],
		bbar : new Ext.PagingToolbar({
					store : ds,
					pageSize : 500,
					emptyMsg : '没有任何数据',
					displayInfo : true
				}),
		tbar : ['文件类型：', new Ext.form.TextField({
							id : '_content'
						}), {}, '开始日期：', new Ext.form.DateField({
							id : 'beginrq',
							format : "Y-m-d",
							width : 150,
							invalidText : '日期格式不正确,如:2009-01-13'
						}), {}, '结束日期：', new Ext.form.DateField({
							id : 'endrq',
							format : "Y-m-d",
							width : 150,
							invalidText : '日期格式不正确,如:2009-01-13'
						}), {}, new Ext.Button({
							text : '开始查询',
							pressed : true,
							handler : function() {
								ds.baseParams.begindate = null;
								ds.baseParams.enddate = null;
								if (Ext.getCmp("beginrq").isValid()
										&& Ext.getCmp("endrq").isValid())
									if (Ext.getCmp("beginrq").getValue()
											&& Ext.getCmp("endrq").getValue()) {
										var beginDate = Ext.getCmp("beginrq")
												.getValue().dateFormat('Y-m-d');
										var endDate = Ext.getCmp("endrq")
												.getValue().dateFormat('Y-m-d');

										if (beginDate > endDate) {
											Ext.Msg.show({
														title : '错误提示',
														msg : '开始日期不能大于结束日期',
														buttons : Ext.Msg.OK,
														icon : Ext.Msg.ERROR
													});
											return false;
										}
										ds.baseParams.begindate = beginDate;
										ds.baseParams.enddate = endDate;
									} else if (!Ext.getCmp("beginrq")
											.getValue()
											&& Ext.getCmp("endrq").getValue()) {
										Ext.Msg.show({
													title : '错误提示',
													msg : '请输入区间段开始日期',
													buttons : Ext.Msg.OK,
													icon : Ext.Msg.ERROR
												});
										return false;
									} else if (Ext.getCmp("beginrq").getValue()
											&& !Ext.getCmp("endrq").getValue()) {
										Ext.Msg.show({
													title : '错误提示',
													msg : '请输入区间段结束日期',
													buttons : Ext.Msg.OK,
													icon : Ext.Msg.ERROR
												});
										return false;
									}
								var content = Ext.getCmp("_content").getValue();
								ds.baseParams.tcontent = content;
								ds.reload();
							}
						})]
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
							title : '未被消耗子零件',
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
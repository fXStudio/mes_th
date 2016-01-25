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
						name : 'unlinecode',
						type : 'string'
					}, {
						name : 'kincode',
						type : 'string'
					}, {
						name : 'partcode',
						type : 'string'
					}, {
						name : 'seqcode',
						type : 'string'
					}, {
						name : 'pageno',
						type : 'string'
					}, {
						name : 'printdate',
						type : 'date',
						dateFormat : 'Y-m-d H:i:s'
					}, {
						name : 'psrecorddate',
						type : 'date',
						dateFormat : 'Y-m-d H:i:s'
					}, {
						name : 'psemp',
						type : 'string'
					}, {
						name : 'csrecorddate',
						type : 'date',
						dateFormat : 'Y-m-d H:i:s'
					}, {
						name : 'car',
						type : 'string'
					}, {
						name : 'doorno',
						type : 'string'
					}, {
						name : 'outrecorddate',
						type : 'date',
						dateFormat : 'Y-m-d H:i:s'
					}]);

			var ds = new Ext.data.Store({

						proxy : new Ext.data.HttpProxy({
									url : 'terminalAllSeaOperator.jsp'
								}),

						reader : new Ext.data.JsonReader({
									totalProperty : 'total',
									root : 'data'
								}, terminal_search)
					});

			var cm = new Ext.grid.ColumnModel([{
						name : 'unlinecode',
						dataIndex : 'unlinecode',
						header : '底盘号',
						sortable : true
					},

					{

						id : 'kincode',
						dataIndex : 'kincode',
						header : 'KIN号',
						sortable : true

					}, {
						dataIndex : 'partcode',
						header : '总成零件号',
						id : 'partcode',
						sortable : true
					}, {

						id : 'seqcode',
						dataIndex : 'seqcode',
						header : '总成条码序列号',
						sortable : true

					}, {

						id : 'pageno',
						dataIndex : 'pageno',
						header : '配货单号',
						sortable : true

					}, {
						dataIndex : 'printdate',
						header : '配货单打印时间',
						id : 'printdate',
						width : 150,
						sortable : true,
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
					}, {

						id : 'psrecorddate',
						dataIndex : 'psrecorddate',
						header : '配货单扫描时间',
						width : 150,
						sortable : true,
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')

					}, {

						dataIndex : 'psemp',
						header : '扫描操作员',
						id : 'psemp',
						sortable : true
					}, {

						id : 'csrecorddate',
						dataIndex : 'csrecorddate',
						header : '配货单装载时间',
						width : 150,
						sortable : true,
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')

					}, {

						dataIndex : 'car',
						header : '装载车辆',
						id : 'car',
						sortable : true
					}, {

						dataIndex : 'doorno',
						header : '装载门号',
						id : 'doorno',
						sortable : true
					}, {

						id : 'outrecorddate',
						dataIndex : 'outrecorddate',
						header : '车辆发出时间',
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

						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),

						el : 'searchManager',

						bbar : new Ext.PagingToolbar({
									store : ds,
									pageSize : 30,
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
									title : '追溯综合查询',
									region : 'center',
									layout : 'border',
									items : [grid]
								}]

					});

			ds.load({
						params : {
							start : 0,
							limit : 30
						}
					});

		});
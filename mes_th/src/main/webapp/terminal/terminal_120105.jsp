<%@ page language="java" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>

		<title>终端扫描</title>
		<style>
.scan {
	color: green;
	font: bold;
}

.nscan {
	color: red;
	font: bold;
}
</style>
		<script type="text/javascript" src="../ext-base.js"></script>
		<script type="text/javascript" src="../ext-all.js"></script>
		<script type="text/javascript" language="javascript"
			src="soundmanager2.js"></script>
		<script type="text/javascript" defer="defer">
	/**
	 * 初始化
	 */
	
	var initial = function() {
		document.getElementById("indtxt").focus();
		var sh = document.getElementById("showtxt");
		sh.value = "没有待扫数据";
	}();

	//初始化声音类soundManager 
	soundManager = new SoundManager();
	//soundManager.waitForWindowLoad = true;
	//是否打开调试模式，打开话对viewport有一定影响
	soundManager.debugMode = false;
	//这个是soundManager提供的swf文件所在的文件夹
	soundManager.url = 'swf';
	soundManager.beginDelayedInit();

	soundManager.onload = function() {
		//这里面放入你要播放的声音
		//系统声音
		soundManager.createSound({
			id : 'success',
			url : 'media/event.mp3'
		//autoLoad: true,//自动加载
		//multiShot: false,//true 在同一时刻只能有一个频段的声音
		//autoPlay: true //自动播放 这个是系统的背景音
		//volume: 100
		});
		//信息音
		soundManager.createSound({
			id : 'failed',
			url : 'media/alarm.mp3'
		//volume: 100
		});
	}
	function indKeyUp() {

		if (document.getElementById("indtxt").value == "") {
			return;
		}
		var entrycode = 13;
		if (entrycode == event.keyCode) {

			var intxt = document.getElementById("inptxt");
			var sh = document.getElementById("showtxt");
			var requestConfig = {
				url : "terminalOperator.jsp",//请求的服务器地址
				params : {
					//请求参数
					cPageNo : document.getElementById("indtxt").value
				},
				callback : function(options, success, response) {
					var jsonTxt = response.responseText;
					var jsonObj = eval(jsonTxt);
					var index = eval(jsonObj["index"]);
					arr = jsonObj["items"];

					//获得VINS
					vins = jsonObj["vins"];

					if (arr.length > 0) {
						intxt["index"] = index;
						intxt["preindex"] = index;
						sh.value = index < arr.length ? arr[index] : "数据已扫完";
						var show = document.getElementById("show");

						sh.className = "";
						show.innerHTML = index < arr.length ? "待扫零件号:"
								: "零件号扫完:";
						intxt.readOnly = index >= arr.length;
						if (intxt.readOnly)
							document.getElementById('showordertxt').value = '';
						else
							document.getElementById('showordertxt').value = '第'
									+ (index + 1) + '个零件号......';

					}
					if (intxt.readOnly) {
						if (sh.value == "数据已扫完") {
							var requestConfig = {
								url : "terminal_pagenostate.jsp",//请求的服务器地址
								params : {
									//请求参数
									cPageNo : document.getElementById("indtxt").value
								},
								callback : function(options, success, response) {
									var jsonTxt = response.responseText;
									var jsonObj = eval(jsonTxt);
									if (jsonObj == 0) {
										var pageno = document
												.getElementById("indtxt").value;
										location.href = "terminal_pageno.jsp?pageno="
												+ pageno;
									}
								}
							}
							Ext.Ajax.request(requestConfig);//发送请求
						}
						document.getElementById("indtxt").select();
					} else {
						intxt.focus();
					}
				}
			}
			intxt.readOnly = true;
			intxt.value = "";
			intxt.focus();
			intxt.blur();
			arr.length = 0;

			sh.value = "没有待扫数据";
			sh.className = "";
			document.getElementById('showordertxt').value = '';
			var show = document.getElementById("show");
			show.innerHTML = "配货零件号:";

			Ext.Ajax.request(requestConfig);//发送请求
		}
	}
	function inrKeyUp() {
		if (document.getElementById("indtxt").value == "") {
			return;
		}
		var inrtxt_temp = document.getElementById("inrtxt");
		var regexp = /1[\w\s]{14,}/;
		
		var entrycode = 13;
		if (entrycode == event.keyCode) {
			document.getElementById("inrtxt").blur();	
					if (!regexp.test(inrtxt_temp.value)) {
						alert('序列号错误');
document.getElementById("inrtxt").focus();
						inrtxt_temp.value = "";

						return;
					}
		
			var requestConfig = {
				url : "terminalPartNo.jsp",//请求的服务器地址
				params : {
					//请求参数
					partseq : document.getElementById("inrtxt").value,
					cPageNo : document.getElementById("indtxt").value
				},
				callback : function(options, success, response) {
					var jsonTxt = response.responseText;
					var jsonObj = eval(jsonTxt);
					if (jsonObj) {
						document.getElementById("inrtxt").value = "";
					}else{
						alert('序列号重复');
						document.getElementById("inrtxt").value = "";
						document.getElementById("inrtxt").focus();
						return;
					}
					var intxt = document.getElementById("inptxt");
					intxt.readOnly = false;
					document.getElementById("inptxt").focus();
					
					var sh = document.getElementById("showtxt");
					if (sh.value == "数据已扫完") {
						var pageno = document.getElementById("indtxt").value;
						location.href = "terminal_pageno.jsp?pageno=" + pageno;
					}
				}
			}
			Ext.Ajax.request(requestConfig);//发送请求

		}
	}
	function cancleone() {
		if (document.getElementById("indtxt").value == "") {
			alert('请扫描配货单条码');
			document.getElementById("indtxt").focus();
			return;
		}
		var requestConfig = {
			url : "terminalDeleteOne.jsp",//请求的服务器地址
			params : {
				//请求参数
				pageno : document.getElementById("indtxt").value
			},
			callback : function(options, success, response) {
				var jsonTxt = response.responseText;
				var jsonObj = eval(jsonTxt);

				if (jsonObj == 1) {
					alert('已撤销上一个');
					var intxt = document.getElementById("inptxt");
					var sh = document.getElementById("showtxt");
					var requestConfig = {
						url : "terminalOperator.jsp",//请求的服务器地址
						params : {
							//请求参数
							cPageNo : document.getElementById("indtxt").value
						},
						callback : function(options, success, response) {
							var jsonTxt = response.responseText;
							var jsonObj = eval(jsonTxt);
							var index = eval(jsonObj["index"]);
							arr = jsonObj["items"];

							if (arr.length > 0) {
								intxt["index"] = index;
								intxt["preindex"] = index;
								sh.value = index < arr.length ? arr[index]
										: "数据已扫完";
								var show = document.getElementById("show");
								sh.className = "";
								show.innerHTML = index < arr.length ? "待扫零件号:"
										: "零件号扫完:";
								intxt.readOnly = index >= arr.length;
								if (intxt.readOnly)
									document.getElementById('showordertxt').value = '';
								else
									document.getElementById('showordertxt').value = '第'
											+ (index + 1) + '个零件号......';
							}

						}
					}

					Ext.Ajax.request(requestConfig);//发送请求
				} else {
					alert('已通过校验，不能撤销');

				}
			}
		}
		Ext.Ajax.request(requestConfig);//发送请求
	}

	function cancleall() {
		if (document.getElementById("indtxt").value == "") {
			alert('请扫描配货单条码');
			document.getElementById("indtxt").focus();
			return;
		}
		var requestConfig = {
			url : "terminalDeleteAll.jsp",//请求的服务器地址
			params : {
				//请求参数
				pageno : document.getElementById("indtxt").value
			},
			callback : function(options, success, response) {
				var jsonTxt = response.responseText;
				var jsonObj = eval(jsonTxt);

				if (jsonObj == 1) {
					alert('已全部撤销');
					var intxt = document.getElementById("inptxt");
					var sh = document.getElementById("showtxt");
					var requestConfig = {
						url : "terminalOperator.jsp",//请求的服务器地址
						params : {
							//请求参数
							cPageNo : document.getElementById("indtxt").value
						},
						callback : function(options, success, response) {
							var jsonTxt = response.responseText;
							var jsonObj = eval(jsonTxt);
							var index = eval(jsonObj["index"]);
							arr = jsonObj["items"];

							if (arr.length > 0) {
								intxt["index"] = index;
								intxt["preindex"] = index;
								sh.value = index < arr.length ? arr[index]
										: "数据已扫完";
								var show = document.getElementById("show");
								sh.className = "";
								show.innerHTML = index < arr.length ? "待扫零件号:"
										: "零件号扫完:";
								intxt.readOnly = index >= arr.length;
								if (intxt.readOnly)
									document.getElementById('showordertxt').value = '';
								else
									document.getElementById('showordertxt').value = '第'
											+ (index + 1) + '个零件号......';
							}

						}
					}

					Ext.Ajax.request(requestConfig);//发送请求
				} else {
					alert('已通过校验，不能撤销');

				}
			}
		}
		Ext.Ajax.request(requestConfig);//发送请求
	}
</script>
		<script>
<!--
	/** 数据队列 */
	var arr = [];
	var vins = [];

	function func(e) {
		/** 回车键 */
		var enterkey = 13;
		/** 输入文本 */
		var intxt = document.getElementById("inptxt");
		var inrtxt = document.getElementById("inrtxt");

		if (enterkey == e.keyCode) {
document.getElementById("inptxt").blur();
			intxt["sel"] = coreFunc();
			inrtxt.readOnly=false;
		}
		return intxt["sel"] == true;
	}

	/**
	 *
	 */
	function coreFunc() {
		var intxt = document.getElementById("inptxt");
		var sh = document.getElementById("showtxt");
		if (!intxt["index"]) {
			intxt["index"] = 0
		}
		;
		var destination = arr[intxt["index"]];

		//得到VIN
		var vin = vins[intxt["index"]];

		var curscan = intxt["value"];
		var isLetter = /^.{7}[a-zA-Z0-9]{2}.*$/g;
		var val = "";
		if (isLetter.test(curscan)) {
			val = curscan.substring(7, 9)
			if ((destination.trim() == "") && (curscan.trim() != "")) {
				soundManager.play('failed');
				alert('零件号错误');
document.getElementById("inptxt").focus();
				return false;
			}
			var temp = curscan;
			var count = curscan.length;
			var partno = temp.substring(9, count);

			var requestConfig = {
				url : "terminalCodeParamter.jsp",//请求的服务器地址
				params : {
					//请求参数
					code : val
				},
				callback : function(options, success, response) {
					var jsonTxt = response.responseText;
					curscan = jsonTxt;

					if (destination.trim() == curscan.trim()) {

						var requestConfig = {
							url : "terminalPageNoPart.jsp",//请求的服务器地址
							params : {
								//请求参数
								cPageNo : document.getElementById("indtxt").value,
								partname : destination.trim(),
								partno : partno,
								vin : vin.trim(),
								flag:1
							},
							callback : function(options, success, response) {
								var jsonObj = eval( "(" + response.responseText + ")");
								if(jsonObj["success"]==false){
									alert('序列号重复');
									document.getElementById("inptxt").value = "";
									document.getElementById("inptxt").focus();
									return;
								}
								var partName = jsonObj["partName"];

								if(partName){
									soundManager.play('success');
									document.getElementById("inptxt").focus();
									intxt["index"] = intxt["index"] + 1;
									if (intxt["index"] <= arr.length - 1) {
										sh.value = arr[intxt["index"]];
										turn();
									} else {
										sh.value = "数据已扫完";
										var show = document.getElementById("show");
										show.innerHTML = "零件号扫完:";
										intxt.readOnly = !intxt.readOnly;
										var showOrder = document
												.getElementById("showordertxt");
										showOrder.value = '';
									}
									if (intxt["index"] == arr.length) {
	
										var pageno = document
												.getElementById("indtxt").value;
	
										window.location.href = "terminal_pageno.jsp?pageno="
												+ pageno;
										
	
									}
									
								}
							}
						}
						Ext.Ajax.request(requestConfig);//发送请求
						intxt.value = "";
						return true;
					}

					sh.className = "";
					sh.value = arr[intxt["index"]];
					soundManager.play('failed');
					alert('零件号错误');
					document.getElementById("inptxt").focus();
					intxt.value = "";
					turn();
					return false;
				}
			}
			Ext.Ajax.request(requestConfig);//发送请求
		} else {
			if ((destination.trim() == "") && (curscan.trim() != "")) {
				soundManager.play('failed');
				alert('零件号错误');
document.getElementById("inptxt").focus();
				return false;
			}
			var count = curscan.length;
			curscan = curscan.substring(4, count);
			if (destination.trim() == curscan.trim()) {
				var requestConfig = {
					url : "terminalPageNoPart.jsp",//请求的服务器地址
					params : {
						//请求参数
						cPageNo : document.getElementById("indtxt").value,
						partname : destination.trim(),
						vin : vin.trim()
					},
					callback : function(options, success, response) {
						var jsonObj = eval( "(" + response.responseText + ")");
						var partName = jsonObj["partName"];

						if(partName){
							soundManager.play('success');
							document.getElementById("inrtxt").focus();

							intxt["index"] = intxt["index"] + 1;
							if (intxt["index"] <= arr.length - 1) {
								sh.value = arr[intxt["index"]];
								turn();
							} else {
								sh.value = "数据已扫完";
								var show = document.getElementById("show");
								show.innerHTML = "零件号扫完:";
								intxt.readOnly = true;
								var showOrder = document.getElementById("showordertxt");
								showOrder.value = '';
							}
						}
					}
				}
				Ext.Ajax.request(requestConfig);//发送请求
				intxt.value = "";

				return true;
			}
			sh.className = "";
			sh.value = arr[intxt["index"]];
			soundManager.play('failed');
			alert('零件号错误');
document.getElementById("inptxt").focus();
			intxt.value = "";
			turn();
			return false;
		}

	}

	/**
	 *  反转
	 */
	function turn(srcObj) {
		if (arr.length == 0) {
			return;
		}
		var showOrder = document.getElementById("showordertxt");
		var name = srcObj ? srcObj["value"] : "";
		var intxt = document.getElementById("inptxt");
		var preindex = intxt["preindex"] ? intxt["preindex"] : 0;
		var sh = document.getElementById("showtxt");
		var index = arr.length;

		for ( var i = (name == "" ? 0 + intxt["index"] : preindex); i < arr.length; i++) {
			var val = arr[i];
			if (sh.value == val) {
				index = i;
				break;
			}
		}
		if ("上一个" == name) {
			if (index > 0) {
				index = index - 1;
				sh.value = arr[index];
			}
		} else if ("下一个" == name) {
			if (index < arr.length - 1) {
				index = index + 1;
				sh.value = arr[index];
			}
			var show = document.getElementById("show");
			if (show.innerHTML == "零件号扫完:") {
				return;
			}
		}
		if (index < intxt["index"]) {
			sh.className = "scan";
			var show = document.getElementById("show");
			show.innerHTML = "已扫零件号:";
		} else if (index > intxt["index"]) {
			sh.className = "nscan";
			var show = document.getElementById("show");
			show.innerHTML = "未扫零件号:";
		} else {
			sh.className = "";
			if (sh.value != "没有待扫数据") {
				var show = document.getElementById("show");
				show.innerHTML = index < arr.length ? "待扫零件号:" : "零件号扫完:";
			}
		}
		intxt["preindex"] = index;

		showOrder.value = '第' + (index < arr.length ? index + 1 : index)
				+ '个零件号......';
	}
//-->
</script>
	</head>
	<body>
		<div style="position: relative; float: left;">
			配货单条码:
		</div>
		<div>
			<input type="text" id="indtxt" onkeyup="indKeyUp();" size="28">
		</div>
		<div style="position: relative; float: left;">
			扫描零件号:
		</div>
		<div>
			<input type="text" id="inptxt" onkeyup="func(event);" size="28">
		</div>

		<div style="position: relative; float: left;">
			扫描序列号:
		</div>
		<div>
			<input type="text" id="inrtxt" onkeyup="inrKeyUp();" size="28">
		</div>

		<div id="show" style="position: relative; float: left;">
			配货零件号:
		</div>
		<div>
			<input type="text" id="showtxt" size="28">
		</div>
		<div style="position: relative; float: left;">
			零件号顺序:
		</div>
		<div>
			<input type="text" id="showordertxt" size="28">
		</div>
		<div style="position: relative; float: left;">
			<input type="button" id="aatxt" value="上一个"
				onclick="turn(document.getElementById('aatxt'));" />
		</div>
		<div>
			<input type="button" id="bbtxt" value="下一个"
				onclick="turn(document.getElementById('bbtxt'));" />
		</div>
		<br />
		<br />
		<input type="button" value="撤销上一个" onclick="cancleone();" />
		<input type="button" value="撤销全部" onclick="cancleall();" />
	</body>

</html>


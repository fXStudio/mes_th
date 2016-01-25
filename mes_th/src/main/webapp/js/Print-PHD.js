/**
 *按日期查询数据
 */
function getPrtDate() {
	window.location = "printPHD.jsp?rq=" + document.getElementById("rq").value;
}

/** 
 * 重复打印，按架号，日期 打印print_data中数据
 */
function reprint(groupId) { 
 	innerPrint("document.app.ppr", groupId);
}

/**
 * 数据补打
 */
function printOld(groupId) {
 	innerPrint("document.app.ppHistory", groupId);
}

/**
 * 内部打印方法
 */
function innerPrint(methodExp, groupId) {
	var rq = document.getElementById("rq").value;
	var jsch = "1";
	var jsOldJh = "oldjh" + groupId;
	var jsjh = document.getElementById(jsOldJh).value;
	var printPath = document.getElementById("basepath").value + "servlets";
	
	if(jsjh == 0) {
		alert("请输入架号");
		return false;
	}

	// 调用方法
	eval(methodExp + "('" + rq + "','" + jsch + "','" + jsjh + "','" + printPath + "','" + groupId + "')");
}

/**
 * 配置单打印
 *
 * @param groupid        打印配置组Id
 * @param jPrintRadio    打印份数
 * @param ch             废弃的变量，为了兼容性而保留
 * @param pages          打印页数
 * @param minPartCount   最小批次数量
 * @param PerTimeRow     每行的数据量
 * @param IsContinu      Vin是否连续
 */
function openApp(groupid, jPrintRadio, ch, pages, minPartCount, PerTimeRow, IsContinu) {
    var rq = document.getElementById("rq").value;// 当前的查询时期
    var printPath;// 打印文件路径
    var ls = 1;// 打印辆份的倍数

    // 打印服务的请求路径
    printPath = document.getElementById("basepath").value + "servlets";

    // 设置打印次数
    switch(parseInt(jPrintRadio)) {
    	case 2: ls = 2; break;
    	case 3: ls = 4; break;
    }

	// 如果Vin不连续，则需要让用户确认打印操作
	if(IsContinu === "0" && !window.confirm("vin不连续，是否打印")) { return; }

	//提示数量不足是否打印
	if(minPartCount < PerTimeRow && !window.confirm("辆份不足是否打印？")) { return; }

   	// 打印配置单 (只要能够打印，就认为Vin是连续的，所有最后一个参数为true)
	document.app.pp(rq, ch, ls, printPath, groupid, pages);

	// 页面重载
	window.location.reload();
}

// 设置页面的自动刷新计时器(每三分钟刷新一次)
setTimeout(function(){
	window.location.href = "printPHD.jsp";
}, 3 * 60 * 1000);
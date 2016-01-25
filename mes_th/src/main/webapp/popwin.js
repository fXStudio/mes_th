/**
 * 警告对话框
 */
var Dialog = function(win, date){
		// Document对象
		var doc = win.document;
		// 视图
		var view = doc.createElement("div");
		
		// Window对象
		view.win = win;
		// 唯一标识
		view.id = "divMsg";
		// 样式
		view.style.cssText = "BORDER-RIGHT:#455690 1px solid;BORDER-TOP:#a6b4cf 1px solid;Z-INDEX:99999;LEFT:0px;VISIBILITY:hidden;BORDER-LEFT:#a6b4cf 1px solid;WIDTH:180px;BORDER-BOTTOM:#455690 1px solid;POSITION:absolute;TOP:0px;HEIGHT:116px;BACKGROUND-COLOR:#c9d3f3";
		// 内容
		view.innerHTML = "<TABLE style=\"BORDER-TOP:#ffffff 1px solid;BORDER-LEFT:#ffffff 1px solid\" cellSpacing=0 cellPadding=0 width=\"100%\" bgColor=#cfdef4 border=0>"
	                   + "<TBODY>"
	                   + "<TR>"
	                   + "<TD style=\"FONT-SIZE:12px;COLOR:#0f2c8c\" width=30 height=24></TD>"
	                   + "<TD style=\"FONT-WEIGHT:normal;FONT-SIZE:12px;COLOR:#1f336b;PADDING-TOP:4px;PADDING-left:4px\" vAlign=center width=\"100%\"> 短消息提示：</TD>"
	                   + "<TD style=\"PADDING-TOP:2px;PADDING-right:2px\" vAlign=center align=right width=19>"
                       + "<span id=\"closeButton\" title=关闭 style=\"CURSOR:hand;color:red;font-size:12px;font-weight:bold;margin-right:4px;\">×</span>"
                       + "</TD>"
                       + "</TR>"
                       + "<TR>"
                       + "<TD style=\"PADDING-RIGHT: 1px;PADDING-BOTTOM: 1px\" colSpan=3 height=90>"
                       + "<DIV style=\"BORDER-RIGHT:#b9c9ef 1px solid;PADDING-RIGHT:13px;BORDER-TOP:#728eb8 1px solid;PADDING-LEFT:13px;FONT-SIZE:12px;PADDING-BOTTOM:13px;BORDER-LEFT:#728eb8 1px solid;WIDTH:100%;COLOR:#1f336b;PADDING-TOP:18px;BORDER-BOTTOM:#b9c9ef 1px solid;HEIGHT:100%\">"
                       + "<FONT COLOR=#000000>"
                       +  (date ? date : showDate())
                       +"</FONT><BR><BR>"
                       + "<DIV align=center style=\"word-break:break-all\">"
                       + "<FONT COLOR=\"#FF0000\" size=\"3pt\">发现特殊KIN号</FONT><BR><BR>"
                       + "<DIV STYLE=\"WIDTH:100%;TEXT-ALIGN:RIGHT;OVERFLOW:HIDDEN;\"><A HREF=\"/mes_th/th/productdata/container.jsp\" onclick=\"javascript:top.confirmed=true;\">查看详情</A></DIV>"
                       + "</DIV>"
                       + "</DIV>"
                       + "</TD>"
                       + "</TR>"
                       + "</TBODY>"
                       + "</TABLE>";
        // 绑定事件
        attachEvent(view);
        
        // 返回对象
		return view;
};

/**
 * 绑定事件
 */
function attachEvent(obj){
	/** 关于位置的相关变量 */
	var divTop, divLeft, divWidth, divHeight, docHeight, docWidth, objTimer, i = 0;
	/** Document对象 */
	var docu = obj.win.document;
	/** 计时器 */
	var objTimer;
	
	/**
	 * 显示信息
	 */
	obj.viewMsg = function(){
	   try{
	     divTop = parseInt(docu.getElementById("divMsg").style.top, 10);// div的x坐标
	     divLeft = parseInt(docu.getElementById("divMsg").style.left, 10);// div的y坐标
	     divHeight = parseInt(docu.getElementById("divMsg").offsetHeight, 10);// div的高度
	     divWidth = parseInt(docu.getElementById("divMsg").offsetWidth, 10);// div的宽度
	     docWidth = docu.body.clientWidth;// 获取窗体宽度
	     docHeight = docu.body.clientHeight;// 设置窗体高度
	     docu.getElementById("divMsg").style.top = parseInt(document.body.scrollTop, 10) + docHeight + 10;// 设置div的Y坐标
	     docu.getElementById("divMsg").style.left = parseInt(docu.body.scrollLeft, 10) + docWidth - divWidth;// 设置div的X坐标
	     docu.getElementById("divMsg").style.visibility = "visible";// 设置div显示
	     
	     // 设置定时器
	     objTimer = this.win.setInterval(function(){
	     	obj.moveDiv();
	     }, 10);
	   }catch(e){}
	}
	/**
	 * 重新设置div尺寸
	 */
	obj.resizeDiv = function(){
	   try{
	     divHeight = parseInt(docu.getElementById("divMsg").offsetHeight, 10);// 设置div高度
	     divWidth = parseInt(docu.getElementById("divMsg").offsetWidth, 10);// 设置div宽度
	     docWidth = docu.body.clientWidth;// 获取窗体宽度
	     docHeight = docu.body.clientHeight;// 设置窗体高度
	     docu.getElementById("divMsg").style.top = docHeight - divHeight + parseInt(docu.body.scrollTop, 10);// 设置div的y坐标
	     docu.getElementById("divMsg").style.left = docWidth - divWidth + parseInt(docu.body.scrollLeft, 10);// 设置div的x坐标
	   }catch(e){}
	}
	
	/**
	 * 移动窗体
	 */
	obj.moveDiv = function(){
      try{
	     if (parseInt(docu.getElementById("divMsg").style.top,10) <= (docHeight - divHeight + parseInt(docu.body.scrollTop, 10))){
	       this.win.clearInterval(objTimer);
	       objTimer = this.win.setInterval(function(){
	       		obj.resizeDiv();
	       }, 1);// 调整div的位置和大小
	     }
	     divTop = parseInt(docu.getElementById("divMsg").style.top, 10);// 获取y坐标
	     docu.getElementById("divMsg").style.top = divTop - 1;// 调整div的Y坐标
	   }catch(e){}
	}
	/**
	 * 关闭视窗
	 * */
	obj.close = function(){
		// 清除定时器
		if(objTimer) obj.win.clearInterval(objTimer);
		var nodes = docu.body.childNodes;
		for(var i = 0; i < nodes.length; i++){
			var node = nodes[i];
			if(node == obj){
				// 移除移除节点
				docu.body.removeChild(obj);
			}
		}
	}
	
	/**
	 * 关闭窗体
	 */
	var closeDialog = function(){
		// 关闭按钮
		var closeButton = obj.getElementsByTagName("span")[0];
		// 已确认
		top.recognize = true;
	   	// 关闭事件
	   	closeButton.onclick = obj.close;
	}();
	// 根据窗体高度和宽度，改变短消息提示框的高度和宽度
	obj.win.onresize = obj.resizeDiv;
	// 出现错误时，不做任何处理
	obj.win.onerror = function(){};
}

/**
 * 显示日期
 */
function showDate(){
	var digit = 10;
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute= date.getMinutes();
	var second = date.getSeconds();
	
	return  year 
	       + "-"  
	       + (month > digit ? month : "0" + month)
	       + "-" 
	       + (day > digit ? day : "0" + day)
	       + " " 
	       + (hour > digit ? hour : "0" + hour)
	       + ":" 
	       + (minute > digit ? minute : "0" + minute)
	       + ":" 
	       + (second > digit ? second : "0" + second);
}

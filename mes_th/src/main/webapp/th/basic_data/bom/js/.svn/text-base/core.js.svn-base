/** 当前显示信息类型 */
var curbtn;
/** 弹出窗口名称  */
var dialogname = "fore";
/** AJAX对象 */
var XHR;
 
/**
 * XMLHttpRequest构造函数
 */
function createXMLHttpRequest( ) {
        var request = false;
        if (window.XMLHttpRequest) {
                if (typeof XMLHttpRequest != 'undefined'){
                        try {
                                request = new XMLHttpRequest( );
                        } catch (e) {
                                request = false;
                        }
                }
        } else if (window.ActiveXObject) {
                try {
                        request = new ActiveXObject('Msxml2.XMLHTTP');
                } catch(e) {
                        try {
                                request = new ActiveXObject('Microsoft.XMLHTTP');
                        } catch (e) {
                                request = false;
                        }
                }
        }
        return request;
}
// 创建XMLHttpRequest对象
XHR = createXMLHttpRequest( );

/**
 * 关闭对话框
 */
function closeDialog(){
	// 前置窗口
    var d = document.getElementById(dialogname);
    // 隐藏窗口
    d.style.visibility = "hidden";
}

/**
 * 显示对话框
 */
function showDialog(obj,  page, info){
	// 页号
    var pageNum = page ? page : 1;
    // 搜索关键字
    var searchItem = info ? info : "";
	// 鼠标焦从按钮移开
    obj.blur();
    
    if (XHR) {
	      // 采用GET方式提交请求，路径为bom_dialog.jsp
	      XHR.open("GET", "bom_dialog.jsp?parttype=" + obj["alt"] + "&page=" + pageNum + "&info=" + searchItem, true);
	       
	      /**
	       * 处理请求结果
	       */
	      XHR.onreadystatechange = function(){
	            // 前置窗口对象
	            var d = document.getElementById(dialogname);
	            // 页面体
	            var b = document.body;
	            // 窗口高度
	            d.style.height = b.clientHeight;
	            // 窗口宽度
	            d.style.width = b.clientWidth;
	            // 显示窗口
	            d.style.visibility = "visible";
	            // 窗口坐标
	            d.style.paddingTop = b.clientHeight / 5;
	            
	            if(XHR.readyState == 4){
	                   if(XHR.status == 200){
	                   	     // 当前点击按钮
	                   	     curbtn = obj;
	                   	     // 弹出窗体显示区域
	                         var container = document.getElementById("subwin");
	                         // 填充显示区域
	                         container.innerHTML = XHR.responseText;
	                         
	                         return true;
	                   }
	                   closeDialog();
	           }
	      };
	      XHR.send(null);
   }
}

/**
 * 窗口重置大小
 */
function resizeDialog(){
	// 前置窗体对象
    var d = document.getElementById(dialogname);
    // 页面体
    var b = document.body;
    // 当前窗口是否可见
    if(d.style.visibility == "visible"){
    	// 窗体高度
        d.style.height = b.clientHeight;
        // 窗体宽度
        d.style.width = b.clientWidth;
        // 窗体坐标
        d.style.paddingTop = b.clientHeight / 5;
    }
}

/**
 *  鼠标悬停
 */
function mousehover(trobj){
	if(document.body["clickedObj"] != trobj){
	   trobj.className = "mousehover";
	}
}
   
/**
 *  鼠标移开
 */
function mouseout(trobj){
	if(document.body["clickedObj"] != trobj){
       trobj.className = "";
    }
}
   
/**
 *  鼠标单击
 */
function mouseclick(trobj){
	   //  之前被选中的对象
       var presel = document.body["clickedObj"];
       if(presel){
       	    presel.className = "";
       }
       // 更新选中对象引用
       document.body["clickedObj"] = trobj;
       // 设置选中状态
       trobj.className = "selected";
}
   
/**
 *  鼠标双击
 */
function mousedblclick(trobj){
	   // 鼠标单击
	   mouseclick(trobj);
	   // 提交;
	   apply(trobj);
}

/**
 * 提交
 */
function apply(trobj){
	   var trobj = trobj ? trobj : document.body["clickedObj"];
       // 文本框名称
       var inpname = curbtn["alt"] == '00001' ? "primary" : "sub";
	   // 要填充的文本框
	   var userinp = document.getElementById(inpname);
	   
	   if(trobj && trobj.cells.length > 1){
	        // 赋值选中数据到文本框
	        userinp.value = trobj.cells[1].innerHTML;
	        // 关闭对话框
	        closeDialog();
	   }
}

/**
 * 表单校验
 * 
 * @param thisform 表单对象
 */
function checkinput(thisform){
	// 允许输入格式
    var regexp =  /^[0-9]+$/;
    // 目标页码
    var value = thisform["page"].value;
    
    // 判断输入格式
    if(!regexp.test(value)){
    	window.alert("输入的页码有误,请重新输入!");
	    thisform["page"].value = "1";
	    return false;       
	}
	// 翻页
	turnPage(value);
	// 不对表单进行提交
	return false;
}

/**
 * 页面跳转
 * 
 * @param page 页码
 */
function chagePage(page){
	// 页码
    var npage = page ? page : 1;
    // 翻页
    turnPage(npage);
}

/**
 * 翻页
 * 
 * @param npage 页码
 */
function turnPage(npage){
	var searchItem = document.getElementById("info").value;
	// 显示窗口
	showDialog(curbtn, npage, searchItem);
} 

function search(formObj){
	  // 显示窗口
	  showDialog(curbtn, 1, formObj["info"].value);
	  // 刷新表格
	  return false;
}
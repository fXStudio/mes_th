<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK" language="java"%>
<%session.setAttribute("maxIndentify", "");%>
<html>
	<head>
		<meta http-equiv=content-type content="text/html;charset=GBK">
		<script type="text/javascript" language="javascript" src="ajax.js"></script>
		<link rel="stylesheet" type="text/css" href="../cssfile/th_style.css">
	</head>
	<body bgColor="#000000" scroll="no"> 
		<table border="0" cellpadding="0" cellspacing="0" class="th_table" id="th_thead">
			<thead>
				<tr>
					<td width="180" alt="name" align="center">配货单状态</td>
					<td width="180" alt="code" align="center">状态单号</td>
					<td width="180" alt="id" align="center">配货单号</td>
					<td width="180" alt="car"  align="center">车号</td>
					<td width="180" alt="doorno" align="center">所属门号</td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<div id="mydiv">
			<table border="0" cellpadding="0" cellspacing="0" class="th_table" id="th_tbody">
				<tbody>
				</tbody>
			</table>
		</div>
	</body>
	<script>
		<!--
			/**
			 * 滚屏信息
			 *
			 * @param requestID  请求ID
			 */
			var callServer = function(requestID){
				/** 函数本身 */
				var oneself = arguments.callee;
				/** 头信息 */
				var tbl_head = document.getElementById("th_thead");
				/** 表头 */
				var th = tbl_head.getElementsByTagName("thead")[0];
				/** 内容信息 */
				var tbl_body = document.getElementById("th_tbody");
				/** 表格体 */
				var tbody = tbl_body.getElementsByTagName("tbody")[0];
				/** 头单元格集合 */
				var headers = th.rows[0].cells;
				/** 时间戳 */
				var timestamp = new Date().getTime();
				/** 请求地址 */
				var url = "scrolling.jsp?date=" + timestamp + "&id=" + requestID;
				
				// 请求方式
    			XHR.open("GET", url, true);
    			
    			// 回调函数处理
                XHR.onreadystatechange = function(){
                	if(XHR.readystate == 4){
                        if(XHR.status == 200){
                        	 /** 反馈数据 */
                		     var feedback = XHR.responseText;
                		     /** 实例化回调对象 */
                		     var arr = eval(feedback);
                		     for(var i = 0; i < arr.length; i++){
                		         var jsonobj = arr[i];
                		         var trobj = document.createElement("tr");
                		         
                		         for(var j = 0; j < headers.length; j++){
                		         	 var tdobj = document.createElement("td");
                		         	 var tobj = headers[j];
                		         	 var pro = tobj.alt;
                		         	 var val = jsonobj[pro];
									
								     tdobj.align = "center";
								     tdobj.width = tobj.width;
								     tdobj.height = 50;
                		     	 	 tdobj.innerHTML = val ? val : "-----";
 	               		     	 	 trobj.appendChild(tdobj);
 	               		     	 	 
 	               		     	 	 /** 迭代数据 */
 	               		     	 	 if(pro == "id"){requestID = Math.max(requestID, val);}
                		         }
                		      
                		     	 tbody.appendChild(trobj);
                		     }
                		     while(tbody.hasChildNodes()){
								var node = tbody.firstChild;
								var height = node.offsetHeight;
								if(scrollElem.scrollTop <= 2 * height){break;}
								tbody.removeChild(node);
								scrollElem.scrollTop -= height;
							 }
                		     /**
                		      * 定时循环触发
                		      */
                		     setTimeout(function(){
                		     	oneself(arr.length ? requestID : 0);
                		     // 请求间隔时间	
                		     }, 5 * 1000);
                		     
                		      /**
                		      * 定时刷新
                		      */
                		     setTimeout(function(){
                		     	location.reload();
                		     // 请求间隔时间	
                		     }, 150 * 1000);
                        }
                    }
                }
                // 发送请求
	            XHR.send(null);
			}(0);
			
			// 大屏尺寸
			marque(1004, 700, "icefable1", "box1left")
		    var scrollElem;
		    var stopscroll;
		    var marqueesHeight;
		    
			//为表格添加事件
			function marque(width, height, marqueName, marqueCName){
				try{
				    // 大屏幕的高度
			    	marqueesHeight = height;
			    	// 停止自动翻滚标识
			  		stopscroll = false;
					// 可滚动元素
			  		scrollElem = document.getElementById("mydiv");
			  		// 设置滚动元素样式
			  		with(scrollElem){
						style.width = width;
						style.height = marqueesHeight;
						style.overflow = "hidden";
						noWrap = true;
			  		}
			  		// 鼠标悬浮事件 --- 当属表悬浮在单元格上时，停止自动滚屏
			  		scrollElem.onmouseover = new Function('stopscroll = true');
			  		// 鼠标移开事件 --- 鼠标移开后开启自动滚屏功能
			  		scrollElem.onmouseout  = new Function('stopscroll = false');
			  		// 初始化滚屏元素
			  		init_srolltext();
				}catch(e) {}
			}
			
			/**
			 * 表格滚动的初始化
			 */
			function init_srolltext(){
			    scrollElem.scrollTop = 0;
			    // 滚屏速率
			    setInterval("scrollUp()", 18);
			}
			
			/**
			 * 向上滚动的方法
			 */
			function scrollUp(){
		  		if(stopscroll) {return;}
		  		scrollElem.scrollTop += 1;
			}
			
			/**
			 * 重置表格大小
			 window.onresize = function(){
				var screenHeight = document.body.clientHeight;
				var scrollHeight = document.body.scrollHeight;
				scrollElem.style.height = screenHeight - scrollHeight;
			}
			 */
			
		-->
	</script>
</html>
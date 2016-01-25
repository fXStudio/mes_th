<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>

<% 
	/** 本页面不缓存 */
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	String path = request.getContextPath();
%>

<html>
    <head>
        <title>已处理报文查询</title>
        <meta http-equiv=content-type content="text/html;charset=GBK">
        <link rel="stylesheet" type="text/css" href="../../cssfile/style.css">
        <link rel="stylesheet" type="text/css" href="../../cssfile/th_style.css">
        <script type="text/javascript" src="../../JarResource/META-INF/tag/taglib_common.js"></script>
    </head>
    <body>
    	 <div align="center">
            <div class="title"><strong><!-- InstanceBeginEditable name="标题" -->已处理报文查询<!-- InstanceEndEditable --></strong></div>
            <br/>
            <table cellspacing=0 cellpadding=0 border=1 id="tbl" class="th_table" width="300px">
            	<thead>
            		<tr>
            			<td colspan="2" align="center" style="font-size:25px;">最新处理报文</td>
            		</tr>
	            	<tr>
	            		<td width="150" align="center" style="font-size:16px;">
		            		文件名
		            	</td>
		            	<td width="150" align="center" style="font-size:16px;">
		            		文件处理时间
		            	</td>
	            	</tr>
            	</thead>
            	<tbody>
            	</tbody>
            </table>
            <div style="border:solid 1px red;position:relative;top:30px;">
	         	<span>数据库连接状态:</span>
	         	<span id="conn_state">已连接</span>
         	</div>
         </div>
    </body>
    <script>
    	<!--	
    			/** ajax请求 */
   				var HttpXmlReq = false;
   				// 创建ajax对象
                var createHTTPXMLReq = function(){
	                // FireFox浏览器
	                if(window.XMLHttpRequest){
	                      HttpXmlReq = new  XMLHttpRequest();
	                }
	                // IE浏览器
	                else if(window.ActiveXObject){
	                      try{
	                          HttpXmlReq = new ActiveXObject("Msxml2.XMLHTTP");
	                      }catch(e){
	                          try{
	                              HttpXmlReq = new ActiveXObject("Microsoft.XMLHTTP");
	                          }catch(e){
	                               HttpXmlReq=false;
	                          }
	                      }
	                 }
                }();
                /**
                 *  刷新数据结果集
                 */
    			var func = function(){
    				// 表格对象  
    				var tbl = document.getElementById("tbl");
    				// 表格内容
    				var tbody = tbl.getElementsByTagName("tbody")[0];
    				// 内容行集合
    				var rows = tbody.rows;
    				
    				var innerFunc = function(){
    					// 函数本身
    					var oneself = arguments.callee;
    					// 指纹表示(防止重复提交)
    					var fingerprint = new Date();
    					// 数据连接状态显示区域
    					var connText = document.getElementById("conn_state");
    					// 请求方式、路径
    					HttpXmlReq.open("GET", "<%=path%>/ProcessedServlet?date" + fingerprint, true);
    					// 回调函数处理
	                    HttpXmlReq.onreadystatechange = function(){
	                    	if(HttpXmlReq.readystate == 4){
	                            if(HttpXmlReq.status == 200){
	                            	// 解析服务端数据集合
	                                var arr = eval("{" + HttpXmlReq.responseText + "}");
	                                // 数据库连接状态
		                            connText.innerHTML = arr ? "已连接" : "已断开";
		                            // 数据库连接异常再刷新表格
		                            if(!arr) return;
	                                // 更新表格
	                                for(var i = arr.length - 1; i > -1; i--){
	                                	// 表格长度应该控制在10行
	                                	if(rows.length == 5){
	                                		// 挤走第一行
	                                		tbody.removeChild(rows[arr.length - 1]);
	                                	}
	                                	var subarr = arr[i];
	                                	// 创建一个新行
		                                var tr = document.createElement("tr");
	                                	for(var j = 0; j < subarr.length; j++){
		                                	// 创建一个新列
		                                	var td = document.createElement("td");
		                                	// 对齐方式
		                                	td.align = "center";
							td.style.cssText = "word-break:break-all";
		                                	// 单元格内容
		                                	td.innerHTML = subarr[j];
		                                	// 添加单元格
		                                	tr.appendChild(td);
	                                	}
	                                	// 更新行
		                                tbody.insertBefore(tr, tbody.firstChild);
	                                }
	                            }
		                    }
	                    }
	                    // 发送请求
	                    HttpXmlReq.send(null);
	                    // 延时3秒在进行刷新
	                    setTimeout(function(){
	                    	oneself();
	                    }, 30 * 1000);
    				}();
    			}();
    	-->
    </script>
</html>
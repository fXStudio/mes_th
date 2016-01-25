<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>

<% 
	/** 本页面不缓存 */
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>异常报文信息</title>
        <meta http-equiv=content-type content="text/html;charset=GBK">
        <link rel="stylesheet" type="text/css" href="../../cssfile/style.css">
        <link rel="stylesheet" type="text/css" href="../../cssfile/th_style.css">
        <script type="text/javascript" src="../../JarResource/META-INF/tag/taglib_common.js"></script>
	</head>
	<body>
		<div align="center">
           <div class="title"><font color=red>异常报文查询</font></div>
           (下表列出了最近的几个异常文件)
           <br/>
			<table id="tbl" border=1 width="300px" class="th_table">
			    <thead>
		                <tr>
				    <td align=center style="font-size:16px;" width="150px">文件名</td>
				    <td align=center style="font-size:16px;" width="150px">文件修改时间</td>
				</tr>
			    </thead>
			    <tbody>		
			    </tbody>
			</table>
		</div>
	</body>
	<script>
		<!--
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
			 * 初始化
			 *
			 * @param frame Frame对象
			 */
			function initial(){
			    // 表格对象 
				var tbl = document.getElementById("tbl");
				// 表格体
				var tbody = tbl.getElementsByTagName("tbody")[0];
				
				if (XHR) {
			        var sendRequest = function(){
			        	  // 函数本身
			        	  var fun = arguments.callee;
			        	  // 时间戳 
			        	  var timestamp = new Date().getTime();
			        	  // 采用GET方式提交请求，路径为bom_dialog.jsp
					      XHR.open("GET", "<%=path%>/HintProductDataError?timestamp=" + timestamp + "&reqType=1", true);
					      
					      /**
					       * 处理请求结果
					       */
					      XHR.onreadystatechange = function(){
				              if(XHR.readyState == 4){
			                      if(XHR.status == 200){
				                      var result = XHR.responseText;
				                      if("" != result && result != null){ 
				                      	  // 创建json对象
				                          var jsonObj = eval("(" + result + ")");
				                          while(tbody.hasChildNodes()){
				                          	tbody.removeChild(tbody.lastChild);
				                          }
				                          
				                          // 遍历json信息
				                          for(var property in jsonObj){
				                              // 创建一个行对象
				                              var tr = document.createElement("tr");
				                              // 创建第一个列对象 (文件名)
				                              var col_name = document.createElement("td");
				                              // 创建第二个列对象 (文件创建时间)
				                          	  var col_date = document.createElement("td");
				                          	
				                          	  // 填充文件名称列
				                          	  col_name.innerHTML = property;
				                          	  // 填充文件创建日期列
				                          	  col_date.innerHTML = jsonObj[property];
								  col_name.style.cssText = "word-break:break-all";
				                          	  
				                          	  // 将新创建的单元格添加到行对象上
				                          	  tr.appendChild(col_name);
				                          	  tr.appendChild(col_date);
				                          	  
				                          	  // 插入新行
				                              tbody.appendChild(tr);
				                              // 循环执行
				                          }
				                      }
			                      }
				                  setTimeout(function(){fun()}, 3000);
				              }
					      };
					      XHR.send(null);
			        }();
				}
			}
			initial();
		-->
	</script>
</html>
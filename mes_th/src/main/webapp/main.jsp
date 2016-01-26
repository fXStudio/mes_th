<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.qm.mes.util.tree.DataServer_UserManage"%>
<%@ page import="com.qm.mes.util.tree.BussinessProcess_UserManage"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.SQLException"%>

<jsp:useBean id="Conn_MES" scope="page" class="com.qm.th.helper.Conn_MES"/>
<% 
	String userid = (String)session.getAttribute("userid");
	if(userid == null){
		out.write("访问被拒绝！");
		return;
	}
	
	String cssFile = (String)session.getAttribute("file");
 	if(cssFile == null || cssFile.trim().equals("")){ 
 		cssFile="blue";
 	}
	
	Connection con = null;
	DataServer_UserManage ds = null;
	BussinessProcess_UserManage bp = null;
	
	try{
		con = Conn_MES.getConn();
		ds = new DataServer_UserManage(con);
		String color = request.getParameter("color");
		if(color != null){
			session.setAttribute("file",color);
			cssFile = color;
			bp = new BussinessProcess_UserManage(con);
			bp.updateUserInterface(userid,color);
		}
		String roleno = request.getParameter("roleno");
		String rolename = request.getParameter("rolename");
		String url = ds.getWelcomePage(roleno);
%>

<html>
<head>
	<title>mes-web框架</title>
	<link rel="stylesheet" type="text/css" href="cssfile/<%=cssFile%>.css">
	<script language="javascript" type="text/javascript" src="popwin.js"></script>
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
			function initial(frame){
				// 窗体对象
				var win = frame.contentWindow;
				// Document对象
			    var doc = win.document;
				// 警告对话框
				var dialog;
				// 前一个返回值
			    var prers = "";
				
				// 隐藏滚动条
	   	 		if(doc.body.clientWidth == doc.body.offsetWidth){ doc.body.scroll = "no"; }
			
				if (XHR) {
			        var sendRequest = function(){
			        	  // 函数本身
			        	  var fun = arguments.callee;
			        	  // 时间戳 
			        	  var timestamp = new Date().getTime();
			        	  // 采用GET方式提交请求，路径为bom_dialog.jsp
					      XHR.open("GET", "SpecialKinWarning?timestamp=" + timestamp + "&reqType=0", true);
					      
					      /**
					       * 处理请求结果
					       */
					      XHR.onreadystatechange = function(){
				              if(XHR.readyState == 4){
			                      if(XHR.status == 200){
				                      try{
			                      	  	  var rs = XHR.responseText;
				                   	      var nodes = doc.body.childNodes;
			                   		      if("" != rs && rs != null){
			                   		          var preid = eval( "(" + rs + ")")["id"];
			                   		          
			                   		          // 校验是否为新文件,如果是新文件，则取消确认状态
			                   		          if(top.recent != preid){top.confirmed = false;}
			                   		          if(prers < preid){
			                   		          	  // 文件尚未确认，提示消息
				                   		      	  if(!top.confirmed){
					                   		      	  /** 关闭旧对话框 */
					                   		      	  if(dialog) { dialog.close(); }
						                   		      // 创建一个对话框
							                   		  dialog = new Dialog(win);
													  // 添加窗体到页面
													  doc.body.appendChild(dialog);
													  // 显示窗体
													  dialog.viewMsg();
													  
													  // 时间戳
					                   		      	  top.recent = preid;
												  }
					                   		 	  // 判断页面是否为标准页面
			                   	      			  if(doc.getElementsByTagName("body").length > 0){
						                   		 	  // 重置标识
					                   		      	  prers = preid;
				                   		      	  }
											  }
										  }
										  // 延迟重发
										  setTimeout(function(){fun();}, 5 * 60 * 1000);
								      }catch(e){}
			                      }
				              }
					      };
					      XHR.send(null);
			        }();
				}
			}
		-->
	</script>
</head>
<frameset rows="0,*,20" cols="*" id="main" border="0" >
	<frame name="title" scrolling="no" noresize  src="head.jsp?roleno=<%=roleno%>&rolename=<%=rolename%>"  marginwidth="0" marginheight="0" > 
  	<frameset cols="230,10,*" id="left" border="0" >
		<frame NAME="ProductionMenu" ID="ProductionMenu" src="menu.jsp?roleno=<%=roleno%>" marginwidth="0" marginheight="0"  class="frameborder">
		<frame NAME="middle" ID="middle"  scrolling="no" noresize="noresize" src="middle.jsp" marginwidth="0" marginheight="0">
    	<frame NAME="ProductionShow" ID="ProductionShow" src=<%=url%> class="frameborder" onload="initial(this)">
	</frameset>
	<frame name="foot" id="foot" scrolling="no" noresize src="foot.jsp" marginwidth="0" marginheight="0">
</frameset>
<% 
	}catch(Exception e){
		throw e;
	}finally{
		if(con != null){
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				con = null;
			}
		}
	}
%>
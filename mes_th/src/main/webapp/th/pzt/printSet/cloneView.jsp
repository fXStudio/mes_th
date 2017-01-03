<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ page import = "java.sql.*" %>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv=content-type content="text/html;charset=gb2312">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>cssfile/css.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>cssfile/th_style.css">
	<script type="text/javascript" src="<%=basePath%>JarResource/META-INF/tag/taglib_common.js"></script>		 
    <title>复制配置单</title>	
        <style>
    	.spanrow {
    		width:100%;height:100%;padding-top:20px;font-size:12pt;
    	}
    	.singlerow {
    		width:100%;height:20px;
    	}
    </style>

  </head>
   <body>
       <div align="center"><font size="+1" >配货单打印设置</font></div> 
       <form id="form1" name="form1"  action="cloneSave.jsp"  method="get" onsubmit="return validate()">
	     <table width="950" border="1" align="center" height="97">
	     <tr>	
			<td width="150">描述</td>
	    	<td  width="50">工厂编码</td>
	    	<td  width="40">车型</td>
	     	<td width="100">打印标题</td>
	     	<td width="100">总成</td>
	     	<td width="50">打印页数</td>
	     	<td width="50">打印辆份</td>
	     	<td width="120">VIN特殊规则</td>
	     </tr>
	    <%
		    String groupId = request.getParameter("groupId");
		
		    Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			try {
				conn = Conn.getConn();
				stmt = conn.prepareStatement("SELECT * FROM printset where iprintgroupid = ?");
				stmt.setString(1, groupId);
				
				rs = stmt.executeQuery();
				
				for(int i = 0; rs.next(); i++){
					out.write("<tr>");	
						if(i == 0) {
							out.write("<td rowspan=99><input name='cDescrip' class='spanrow' value='" + rs.getString("cDescrip") + "' /></td>");
							out.write("<td rowspan=99><input name='cFactory' class='spanrow' value='" + rs.getString("cFactory") + "' /></td>");
							out.write("<td rowspan=99><input name='cCarType' class='spanrow' value='" + rs.getString("cCarType") + "' /></td>");
						}
						out.write("<td><input name='cCarTypeDesc' class='singlerow' value='" + rs.getString("cCarTypeDesc") + "' /></td>");
						out.write("<td><input name='cTFASSName' class='singlerow'  value='" + rs.getString("cTFASSName") + "' /></td>");

						if(i == 0) {
							out.write("<td rowspan=99><input name='nPage' class='spanrow' value='" + rs.getString("nPage") + "' /></td>");
							out.write("<td rowspan=99><input name='nPerTimeCount' class='spanrow' value='" + rs.getString("nPerTimeCount") + "' /></td>");
							out.write("<td rowspan=99><input name='cVinRule' class='spanrow' value='" + (rs.getString("cVinRule") == null || rs.getString("cVinRule").trim().length() == 0 ? null : rs.getString("cVinRule")) + "' /></td>");
						}
						out.write("<td style='display:none;'><input  name='cCode' value='" + rs.getString("ccode") + "'/></td>");
						out.write("<td style='display:none;'><input name='cPrintMD' value='" + rs.getString("cprintmd") + "'/></td>");
					out.write("<tr>");	
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(rs != null) {
					try{
						rs.close();
					} catch (Exception e){
						e.printStackTrace();
					} finally {
						rs = null;
					}
				}
				if(stmt != null) {
					try{
						stmt.close();
					} catch (Exception e){
						e.printStackTrace();
					} finally {
						stmt = null;
					}
				}
				if(conn != null) {
					try{
						conn.close();
					} catch (Exception e){
						e.printStackTrace();
					} finally {
						conn = null;
					}
				}
			}
		%>
		    </table>
		    <table>
			  <tr>
			  	<td  align="center" width="200px">
			  		<input type="submit" value="提交" style="width:100%;"/>
			  	</td>
			  	<td align="center" width="200px">
			  		<input type="button" value="返回" onclick="javascript:window.location='printSetView.jsp'" style="width:100%;background-color:lightBlue;border:blue 1px solid;"/>
			  	</td>
			  </tr>
		   </table>
		</form>
        <script src="<%=basePath%>js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript">
        	  function validate() {
        		  var res = false;
        		  var obj = $(':text[name=nPage],:text[name=nPerTimeCount]').each(function(){
        			  if(!(res = /^[0-9]+$/.test($(this).val()))){
        					alert("打印页数及打印两份只允许输入数字");  
        			  }
        			  return res;
        		  });
        		  return res;
        	  }
        </script>
    </body>
</html>
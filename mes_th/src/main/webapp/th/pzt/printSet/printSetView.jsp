<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.sql.*"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
                    Calendar ca = Calendar.getInstance();
					java.util.Date da = ca.getTime();
					SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
					String sj = formt.format(da);
				
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
    <title>配货单打印</title>	
  </head>
  
  <%
  int bigNo=0;
  String carCount="0";
     Connection con = null;
     Statement stmtInIn=null;
     Statement stmt = null;
     ResultSet rs = null;
     ResultSet rsInIn=null;
     Statement stmtIn=null;
     ResultSet rsIn=null;
     
     String sql = "";
     String sqlInIn="";
     String strSql="";
     String groupId="0";
     String carTypeDesc="";
     String carType="";
     String descript="";
     String tFassName="";
     String printSetId="";
     String ch="";
     String js="";
     String sqlWhere="";
     String factoryNo="";
     String seq_A="";
     String auto="";
     String seqno_A=""; 
	 String printPage="0";
	 String lastVin="";
	 String printJh="0";
	
     int maxPage=0;
     int perTimeCount=0;
     int tFassCount=0;
     int partCount=0;
     int minPartCount=9999;

     
     int temp =0;
     
   %>
  
  <body>
       <div align="center">
      <font size="+1" >配货单打印设置</font>
      <label><mes:calendar id="rq" name="rq" reSourceURL="../../../JarResource/" showDescripbe="false" haveTime="false" value="<%=sj%>"/>    
      </label>
      
      </div> 
      <form id="form1" name="form1"  action="printSetUpdate.jsp"  method="get">
	     <table width="950" border="1" align="center" height="97">
	     <tr>	
	    	<td  width="25">组号</td>
			<td width="150">描述</td>
	     	<td width="100">打印标题</td>
	     	<td width="100">总成</td>
	     	<td width="50">打印辆份</td>
	     	<td width="25">自动</td>
	     	<td width="110">底盘号</td>
	     	<td width="25">份数</td>
			<td width="30">架号</td>
	     	<td width="35">删除</td>
	     	<td width="35">复制</td>
	     </tr>
		 <%
		 	try {
		 		con = Conn.getConn();
		     	stmt = con.createStatement();
		     	rs = stmt.executeQuery("select distinct iPrintGroupId from printSet");
		     	
				while (rs.next()) {
					groupId=rs.getString("iPrintGroupId");
				
					try {
						strSql = "select cDescrip,cCarTypeDesc,ctfassname,nPerTimeCount,nTFASSCount,id,cfactory,cCarType,cPrintRadio,cAuto,cseqno_a,npage,clastvin,icarno from printset where iPrintGroupid="+groupId+" order by id";
						stmtIn = con.createStatement();
						rsIn = stmtIn.executeQuery(strSql);
						if(rsIn.next()) {
							descript=rsIn.getString(1);
							carTypeDesc=rsIn.getString(2);
							tFassName=rsIn.getString(3);
							perTimeCount=rsIn.getInt(4);
							tFassCount=rsIn.getInt(5);	
							printSetId=	rsIn.getString(6);
							factoryNo=rsIn.getString(7);
							carType=rsIn.getString(8);
							auto=rsIn.getString(10);
							if(auto==null||auto.trim().equals(""))
							 auto="0";
							seqno_A=rsIn.getString(11).trim();
							if(seqno_A==null||seqno_A.equals(""))
								seqno_A="0";
							printPage=rsIn.getString(12);
							lastVin=rsIn.getString(13);
							lastVin=lastVin.trim();
							printJh=rsIn.getString(14);
							printJh=printJh.trim();
						}
					}
					catch(Exception eGetCarCount) {
						System.out.print("eGetCarCount:"+eGetCarCount.toString());
						throw eGetCarCount;
					} finally {
						if(rsIn!=null)
							rsIn.close();
						if(stmtIn!=null)
							stmtIn.close();				
					}
					out.write("<tr>");	
					out.write("<td>"+groupId+"</td>");
					out.write("<td>"+descript+"</td>");
					out.write("<td>"+carTypeDesc+"</td>");
					out.write("<td>"+tFassName+"</td>");
					out.write("<td><label><input name='printRadio"+groupId+"' style='width:100%;' value='");
					out.write(perTimeCount+ "'/>");
					out.write("</label></td>");
					
					out.write("<td align='center'><input type='checkbox' name='checkBox"+groupId+"' id='checkBox"+groupId+"' ");
					if(auto.trim().equals("1"))
						out.println("checked='checked'");
					out.write(" />");	
					out.write("</label></td>");
					
					out.write("<td><input type='text' name='seqText"+groupId+"' id='seqText"+groupId+"' value='"+lastVin+"' style='width:110px;'/></td>");			
					out.write("<td><input type='text' name='printPage"+groupId+"' id='printPage"+groupId+"' value='"+printPage+"' size='2' maxlength='2'/></td>");
					out.write("<td><input type='text' name='printJh"+groupId+"' id='printJh"+groupId+"' value='0' size='3' maxlength='3'/></td>");
					out.write("<td><input type='button' name='printDel"+groupId+"' id='printDel"+groupId+"' value='删除' onclick='printDel("+groupId+")'/></td>");			
					out.write("<td><input type='button' name='printDel"+groupId+"' id='printDel"+groupId+"' value='复制' onclick='clone("+groupId+")'/></td>");			
					out.write("</tr>");
				}//end while printid;
		    }
		    catch(Exception e2) {
		    	e2.printStackTrace();
		 	} finally { 
			  	 if(rs!=null)
			   		 rs.close();
			   	 if(stmt!=null)
			   	 	stmt.close();
			   	 if(con!=null) 
			  		con.close();
		  	}
		  %>
		  <tr>
		  	<td colspan="11" align="center">
		  		<input type="submit" value="提交"/>
		  	</td>
		  </tr>
		    </table>
		   <input type="hidden" name ="changed"/>
		</form>
        <script src="<%=basePath%>js/jquery-1.11.0.min.js"></script>
    </body>
	<script language="javascript"> 
	    $(document).ready(function(){
	    	// 记录用户修改了那些项目
	    	$(':radio,:text,:checkbox').change(function(){
	    		// 所选项目的ID
	    		var groupId = $(this).attr("name").match(/\d+/);
	    		// 获得隐藏域对象
	    		var hidden = $('input[type=hidden]');
	    		// 隐藏域的内容
	    		var val = hidden.val();
	    		
	    		if(val.length > 0){
	    			val = val + ",";
	    		}
	    		hidden.val(val = val + groupId);
	    	});
	    });
        
        // 删除打印配置
        function printDel(groupId) {
           if(window.confirm("确定要删除当前架号信息吗？")) {
               var carid = document.getElementById('printJh'+groupId).value;
               var rq = document.getElementById('rq').value;
               var vincode = document.getElementById('seqText'+groupId).value;
               
               if(isNaN(carid)) {
                   alert("请输入数字");
               } else if(carid <= 0) {
                   alert("架号应该大于0");
               } else {
                   window.location="printDel.jsp?groupId="+groupId+"&carId="+carid+"&rq="+rq+"&vincode="+vincode;
               }
           }
        }
        
        // 确认复制
        function clone(groupId) {
           if(window.confirm("通过复制可生成新的打印配置组，确认要执行此操作吗？")) {
        	   window.location="cloneView.jsp?groupId="+groupId;
           }
        }
	</script>
</html>
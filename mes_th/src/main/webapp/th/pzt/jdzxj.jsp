<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.sql.*"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String groupId=request.getParameter("groupId");
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
    <link rel="stylesheet" type="text/css" href="../../cssfile/css.css">
		<script type="text/javascript"
			src="../../JarResource/META-INF/tag/taglib_common.js"></script>
			 <base href="<%=basePath%>">
    <title>捷达/宝莱/高尔夫线配货单左/右转向节带前滑打印</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	

  </head>
  <%
  	//取架数
     Connection con = null;
     Statement stmt = null;
     ResultSet rs = null;
     String sql = "";
     int temp =0;  //车数
     String printDate="";
     int js=0; //架数
     int perTimeCount=0; //每次数量；
     int tFassCount=0;  //每页数量；
     int maxPage=0;		//最大架号
     
     
     //sql = "select max(iBigNo) tt  from print_data  where iPrintGroupId = '1' and convert(varchar(10),dPrintTime,20)='"+sj+"'";
     //从printSet中取架数
     sql="select iBigNo,dprintDate,iCarNo,nPertimeCount,ntfassCount from printSet where iPrintGroupId="+groupId;
     try{
     con = Conn.getConn();
     stmt = con.createStatement();
     rs = stmt.executeQuery(sql);
     
     //System.out.println("1111111111111111111111111111"+rs.getString(1));
     //System.out.println("******************"+rs);
     if(rs.next())
     {
    	temp = rs.getInt(1); 	
		//temp++;
		printDate=rs.getString(2);
		js=rs.getInt(3);
		perTimeCount=rs.getInt(4);
		tFassCount=rs.getInt(5);
		printDate=printDate.substring(0,10);
     }
     else
     {
     	//temp =1;
     	System.out.println("后台表:printSet设置有误,没有id为："+groupId+"的数据");
     }
     //System.out.println("2222222222222222222222222"+rs.getString(1));
     //System.out.println("****"+rs.getRow());
     
     maxPage=perTimeCount/tFassCount;
    // 当架数==最大架数时 车号加1；
     if(maxPage<=js)
     {
     	temp++;
     }
     else if(temp==0)
     {
     	temp++;
     }

  }catch(Exception e){
    e.printStackTrace();
  }finally{
  
    if(stmt!=null)
    stmt.close();
    if(rs!=null)
    rs.close();
    if(con!=null) 
    con.close();
  
  }
  
   %>
  <body>
  
  
      <div align="center">
      <font size="+4" >捷达/宝莱/高尔夫线配货单左/右转向节带前滑打印</font>
      </div>
      <form id="form1" name="form1" method="post" action="">
     <table width="369" border="1" align="center">
    <tr>
      <td width="134"><label>
        <input type="radio" name="ls"  value="2" checked="checked" onclick="setls(2)"/>
      12辆份</label></td>
      <td width="111"><label>
        <input type="radio" name="ls" value="4" onclick="setls(4)"/>
      24辆份</label></td>
      <td width="102"><label>
        <input type="radio" name="ls" value="8" onclick="setls(8)" />
      48辆份</label></td>
    </tr>
    <tr>
      <td align="center">请输入日期</td>
      
      <td colspan="2"><label>
        <input type="text" name="rq" id="rq"  value="<%=sj %>"/>
      </label></td>
    </tr>
    <tr>
      <td align="center">请入车号</td>
      <td colspan="2"><label>
        <input type="text" name="ch" id="ch" />
      </label></td>
    </tr>
    <tr>
      <td align="center"><label>
        <input type="button" name="button" id="button" value="提交" onclick="openApp()"/>
      </label></td>
      <td colspan="2" align="center"><label>
        <input type="reset" name="button2" id="button2" value="重置" />
      </label></td>
    </tr>
  </table>
  
   <div id="d">
  <APPLET ID="JrPrt" name = "app" codebase="th/pzt" CODE = "JdzxjApplet"  
  	ARCHIVE = "jasperreports-3.1.4-applet.jar,jcommon-1.0.10.jar,jasperreports-2.0.5.jar"
  	 WIDTH = "0" HEIGHT = "0" MAYSCRIPT>
  <PARAM NAME = "type" VALUE="application/x-java-applet;version=1.2.2"/>
  <PARAM NAME = "scriptable" VALUE="true"/>
  <PARAM NAME = "REPORT_URL" VALUE =""/>
  </APPLET>
  </div>
</form>
</body>
</html>
<script language="javascript"> 
var ls = 2;
function setls(xx){
	ls=xx;
	//alert(ls);
}
function test(s){
    var rq=document.getElementById("rq").value;
    var ch=document.getElementById("ch").value;
    document.app.pp(rq,ch,s);
}

function openApp() 
{ 

    //var ls=document.getElementById("ls").value;
    var rq=document.getElementById("rq").value;
    var ch=document.getElementById("ch").value;
    var prtDate;
    var printPath;
    var js; //架数
    prtDate="<%=printDate%>";
    printPath="<%=basePath%>"+"servlets/jprint?";
    js="<%=js%>";
	
	//如果是第二天，车号变为1
	if(rq>prtDate)
		ch=1;
	//ls打印次数
    document.app.pp(rq,ch,ls,printPath);   
   	window.location.reload();
}

</script> 

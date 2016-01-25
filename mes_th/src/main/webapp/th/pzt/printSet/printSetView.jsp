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
     String printRadio="";
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
    	 <td  width="50">groupid</td>
		<td width="150">描述</td>
     	<td width="100">打印标题</td>
     	<td width="100">总成</td>
     	<td width="250">已接收数据</td>
     	<td width="150">底盘号</td>
     	<td width="50">份数</td>
		<td width="50">架号</td>
     	<td width="50">删除</td>
     </tr>
 <%
 	try
 	{
 		con = Conn.getConn();
     	stmt = con.createStatement();
     	//sql="select cCarTypeDesc,ctfassname,ibigno,nperTimeCount,nTfassCount,iPrintGroupId from printset";
     	sql="select distinct iPrintGroupId from printSet";
     	rs=stmt.executeQuery(sql);
		while (rs.next())
		{
			//groupid号；
			groupId=rs.getString(1);
			/*
			//起始车号
			System.out.println("bigno");
			bigNo=rs.getInt(3);
			if (bigNo==0)
			{
				bigNo++;
			}
			*/
			//已接收车辆数
			//sqlCarCount="select count(*)  from cardata a,cardata_d b where a.cCarNo=b.icarid and b.itfassnameid=4";
		
			try
			{
				
				//stmtCarCount=con.createStatement();
				//rsCarCount=stmtCarCount.executeQuery(sqlCarCount);
				
				strSql="select cDescrip,cCarTypeDesc,ctfassname,nPerTimeCount,nTFASSCount,id,cfactory,cCarType,cPrintRadio,cAuto,cseqno_a,npage,clastvin,icarno from printset where iPrintGroupid="+groupId+" order by id";
				stmtIn=con.createStatement();
				rsIn=stmtIn.executeQuery(strSql);
				if(rsIn.next())
				{
					
					//carCount=rs.getString(1);
					descript=rsIn.getString(1);
					carTypeDesc=rsIn.getString(2);
					tFassName=rsIn.getString(3);
					perTimeCount=rsIn.getInt(4); //打印总数
					tFassCount=rsIn.getInt(5);	
					printSetId=	rsIn.getString(6);
					factoryNo=rsIn.getString(7);
					carType=rsIn.getString(8);
					printRadio=rsIn.getString(9);
					if(printRadio==null ||printRadio.trim().equals(""))
						printRadio="0";
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
					
					//System.out.println(printSetId);						
				}

			}
			catch(Exception eGetCarCount)
			{
				System.out.print("eGetCarCount:"+eGetCarCount.toString());
				throw eGetCarCount;
			}
			finally
			{
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
			//System.out.println("aaaaaaaaaaaaaaaa"+ch);
			
			out.write("<td><label><input type='radio' name='printRadio"+groupId+"'  value='1' ");
			if(printRadio.trim().equals("1"))
				out.write("checked='checked'");
			out.write(" onclick='setls(1)'  />"+tFassCount+"辆份");
			
			out.write("<input type='radio' name='printRadio"+groupId+"'  value='2'");
			if(printRadio.trim().equals("2"))
				out.write("checked='checked'");
			out.write(" onclick='setls(2)' />"+tFassCount*2+"辆份");
			
			out.write("<input type='radio' name='printRadio"+groupId+"'  value='3' ");
			if(printRadio.trim().equals("3"))
				out.write("checked='checked'");
			out.write("onclick='setls(4)'  />"+perTimeCount+"辆份");
			out.write("<input type='checkbox' name='checkBox"+groupId+"'   id='checkBox"+groupId+"' ");
			if(auto.trim().equals("1"))
				out.println("checked='checked'");
			out.write(" /> 自动打印");	
			out.write("</label></td>");
			out.write("<td><input type='text' name='seqText"+groupId+"' id='seqText"+groupId+"' value='"+lastVin+"' /></td>");			
			out.write("<td><input type='text' name='printPage"+groupId+"' id='printPage"+groupId+"' value='"+printPage+"' size='2' maxlength='2'/></td>");
			out.write("<td><input type='text' name='printJh"+groupId+"' id='printJh"+groupId+"' value='0' size='3' maxlength='3'/></td>");
			out.write("<td><input type='button' name='printDel"+groupId+"' id='printDel"+groupId+"' value='删除' onclick='printDel("+groupId+")'/></td>");			
			out.write("</tr>");
		}//end while printid;
    }
    catch(Exception e2)
    {
    	e2.printStackTrace();
 	}
  	finally
  	{ 
  	 if(rs!=null)
   		 rs.close();
   	 if(stmt!=null)
   	 	stmt.close();
   	 if(con!=null) 
  		con.close();
  	}
  %>
  <tr>
  	<td colspan="7" align="center">
  		<input type="submit" value="提交"/>
  	</td>
  </tr>
    </table>

</form>
</body>
</html>
<script language="javascript"> 
var ls = 1;
function setls(xx){
	ls=xx;
	//alert(ls);
}
function test(s){
    var rq=document.getElementById("rq").value;
    var ch=document.getElementById("ch").value;
    document.app.pp(rq,ch,s);
}

function openApp(groupid) 
{ 
    //var ls=document.getElementById("ls").value; 
    //alert(groupid);
   //alert(ls);   
}
function printDel(groupId)
{
	var carid="";
	var rq="";
	var vincode="";
	carid=document.getElementById('printJh'+groupId).value;
	rq=document.getElementById('rq').value;
	vincode=document.getElementById('seqText'+groupId).value;
	if(isNaN(carid))	
		alert("请输入数字");
	else if(carid<=0)
	{
		alert("架号应该大于0");
	}
	else
	{
		window.location="printDel.jsp?groupId="+groupId+"&carId="+carid+"&rq="+rq+"&vincode="+vincode;
	}
}

</script> 
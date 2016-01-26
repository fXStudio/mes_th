<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.qm.th.helper.DataProcess" %>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES" />

<% 
Connection con=null;
ResultSet rs=null;
Statement stmt=null;
String strSql="";
Vector v_sql=new Vector();
String groupId="";
Boolean sign=true;
String printId="";
String printIds="";
String carId="";
String vinCode="";
String rq="";

Boolean successDel=true;
%>
<%
	groupId=request.getParameter("groupId");
	carId=request.getParameter("carId");
	rq=request.getParameter("rq");
	vinCode=request.getParameter("vincode");
	vinCode=vinCode.trim();
	strSql="select id from printSet where iPrintGroupId="+groupId;	
	try
	{
		con=Conn.getConn();
		stmt=con.createStatement();
		rs=stmt.executeQuery(strSql);
		while (rs.next())
		{
			printId=rs.getString(1).trim();
			printIds=","+printId+printIds;		
		}
		printIds=printIds.substring(1);
	}
	catch(Exception EgetPrintId)
	{	
		System.out.println(EgetPrintId.toString());
	}
	finally
	{
		if(rs!=null)
			rs.close();
		if(stmt!=null)
			stmt.close();
	}
	
	
	try
	{
		strSql="delete print_data where iPrintGroupID in ("+printIds+") and  icarno>="+carId+" and cremark='"+rq+"'";
		v_sql.addElement(strSql);
		carId=String.valueOf(Integer.valueOf(carId)-1);
		strSql="update printset set icarno="+carId+",imesseq="+carId+" ,clastvin='"+vinCode+"' where iprintgroupid="+groupId;
		v_sql.addElement(strSql);
		successDel=DataProcess.updateBatch(con,v_sql);
	}
	catch(Exception EgetPrintId)
	{	
		System.out.println(EgetPrintId.toString());
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
	if(successDel==true)
	{
%>
<script language="javascript">
	alert("É¾³ý³É¹¦");
	window.location="printSetView.jsp";
</script>
<%
	}
	if(successDel==false)
	{
%>
<script language="javascript">
		alert("É¾³ýÊ§°Ü!");
		window.location="printSetView.jsp";
	
</script>
<%
	}	
 %>

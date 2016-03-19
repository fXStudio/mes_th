<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ page import ="common.*" %>
<%@ page import = "java.sql.*" %>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES" />

<% 
	Connection con=null;
	ResultSet rs=null;
	Statement stmt=null;
	
	String changed = request.getParameter("changed");
	String strSql="";
	Boolean sign=true;
	Vector v_sql=new Vector();
	String groupId="";
	String groupCount="0";

try {
	strSql="select count(distinct iprintGroupid) from printSet";
	con=Conn.getConn();
	stmt=con.createStatement();
	rs=stmt.executeQuery(strSql);
	if(rs.next())
	{
		groupCount=rs.getString(1);		
	}		
} catch(Exception eGroupCount) {
	System.out.println(eGroupCount.toString());
}
finally {
	if(rs!=null)
		rs.close();
	if(stmt!=null)
		stmt.close();
	if(con!=null)
		con.close();
}

try {
	String auto[];
	String printRadio[];
	String seqNo[];
	String printPage[];
	String tempRadio="";
	String tempAuto="";
	String tempSeq="";
	String tempPrintPage="";
	int ii=0;
	
	auto =new String[Integer.valueOf(groupCount)];
	printRadio=new String[Integer.valueOf(groupCount)];
	seqNo=new String[Integer.valueOf(groupCount)];
	printPage=new String[Integer.valueOf(groupCount)];
	
	
	
	strSql="select distinct iprintGroupid from printSet where iprintGroupid in (" + changed + ")";
	con = Conn.getConn();
	stmt = con.createStatement();
	rs = stmt.executeQuery(strSql);
	while(rs.next()) {
		groupId=rs.getString(1);
		tempRadio="printRadio"+groupId;
		tempAuto="checkBox"+groupId;
		tempSeq="seqText"+groupId;
		tempPrintPage="printPage"+groupId;
		printRadio[ii]=request.getParameter(String.valueOf(tempRadio));
		auto[ii]=request.getParameter(String.valueOf(tempAuto));
		seqNo[ii]=request.getParameter(String.valueOf(tempSeq));
		printPage[ii]=request.getParameter(String.valueOf(tempPrintPage));
		if(auto[ii]==null||auto[ii].equals("")){
			auto[ii]="0";
		} else {
			auto[ii]="1";
		}
		strSql="update printSet set cPrintRadio='"+printRadio[ii]+"',cAuto='"+auto[ii]+"',cLastVin='"+seqNo[ii]+"' ,npage='"+printPage[ii]+"' where iPrintGroupId='"+groupId+"'";
		
		//System.out.println(strSql);
		v_sql.addElement(strSql);
		//对付吧
		strSql="delete printsetvin where printid in (select id from printset where iPrintGroupId='"+groupId+"')";
		v_sql.addElement(strSql);
		ii++;
	}
	
	sign=DataProcess.updateBatch(con,v_sql);
	if(sign)
	{
%>
<script language="javascript">
alert("保存成功");
window.location="printSetView.jsp";
</script>
<% 
	}
	else
	{
%>
<script language="javascript">
alert("保存失败");
window.location="printSetView.jsp";
</script>
<% 	
	}
}
catch(Exception eGetSql)
{
	System.out.println(eGetSql.toString());
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
<html>
  <head>
  	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
  </head>
<body> 
<br>aa 
<a href="printSetView.jsp">aa</a>
</body>
</html>
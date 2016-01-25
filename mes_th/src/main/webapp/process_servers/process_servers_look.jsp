<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<jsp:directive.page import="mes.beans.Act_Statement"/>
<%@page import="mes.framework.*" %>
<%@page import="mes.framework.dao.*" %>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html>
<%  
	//获取参数
	
	Connection conn=null;
	Statement stmt=null;
   	ResultSet rs=null;	
    String intpage=request.getParameter("intPage");
 	String processid=request.getParameter("processid");
	String sid=request.getParameter("sid");
	String eid=request.getParameter("eid");
	if (sid==null)
	sid="";
	String serverid="";
	String aliasname="";
	String actid="";
	String actname="";
	String processName="";
	String serverName="";
	String process_info = request.getParameter("process_info");
	process_info = process_info==null?"":new String(process_info);
   	
   	try{
	//获取连接
   	conn=Conn.getConn();
   	stmt=conn.createStatement();
	String strSql="";
	IDAO_Core sqlDao = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));
	if(sid!="")
	{
		//strSql="select NPROCESSID,NSID,NSERVERID,CALIASNAME,NACTID from PROCESS_SERVERS where NPROCESSID="+processid+" and nsid="+sid;
		strSql=sqlDao.getSQL_QueryProcessServerInfoForProcessidAndSid(processid,sid);
	}
	else
	{
		//strSql="select NPROCESSID,NSID,NSERVERID,CALIASNAME,NACTID from PROCESS_SERVERS where NPROCESSID="+processid;
		strSql=sqlDao.getSQL_QueryProcessServerInfoForProcessid(processid);
	}
	rs=stmt.executeQuery(strSql);

	if(rs.next())
	{
		sid=String.valueOf(rs.getInt(2));
		serverid=String.valueOf(rs.getString(3));
		aliasname=rs.getString(4);
		actid=String.valueOf(rs.getInt(5));
	}
	else{
	%>
	<script>
<!-- 
alert("没有此流程代码！");window.location.href='process_statement_view.jsp';
 -->
</script>

	<%
	}
	  rs=stmt.executeQuery(sqlDao.getSQL_QueryActStatement());
	  while(rs.next()){
	   Act_Statement as=new Act_Statement();
         as.setNactid(rs.getString(1));
         as.setCdescription(rs.getString(2));
         if(as.getNactid().equals(actid)){
            actname=as.getCdescription();
         }
	  }
	  rs=stmt.executeQuery(sqlDao.getSQL_QueryProcess(processid));
	  while(rs.next()){
	    processName=rs.getString(2);
	  }
	  rs=stmt.executeQuery(sqlDao.getSQL_QueryServiceForServiceid(new Integer(serverid)));
	  while(rs.next()){
	    serverName=rs.getString(2);
	  }
   %>

<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Language" content="zh-cn"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
<meta name="GENERATOR" content="Microsoft FrontPage 4.0"/>
<meta name="ProgId" content="FrontPage.Editor.Document"/>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>
<div class="title">查看流程服务信息</div>
<br>
<div align="center">

  <table width="557" class="tdnoborder">
	<tr>	
		<td width="90" >
		流程号:
		</td>
		<td width="437" >　
	<input type="text"  name="tfNPROCESSID" class="box1" value="<%=processid%> - <%=processName%>"  size=30 onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1' "readonly />
 	  </td>
    </tr> 

	<tr>	
		<td>
		运行顺序号：
		</td>
		<td >　
	<input  name="tfNSID" type="text" class="box1" onFocus="this.className='box3'" onBlur="this.className='box1'" onMouseOver="this.className='box3'" onMouseOut="this.className='box1'"  value="<%=sid%>" size=30 maxlength="10" readonly/>
	 	</td>

    </tr> 
	
	<tr>	
		<td>
		服务号：
		</td>
		<td >　
	<input  name="tfNSERVERID" type="text" class="box1" onFocus="this.className='box3'" onBlur="this.className='box1'" onMouseOver="this.className='box3'" onMouseOut="this.className='box1'" value="<%=serverid%> - <%=serverName%>" size=30 maxlength="10"readonly />
	 	</td>
    </tr> 
	
	<tr>	
		<td>
		服务别名：
		</td>
		<td>　
	<input  name="tfCALIASNAME" type="text" class="box1" onFocus="this.className='box3'" onBlur="this.className='box1'" onMouseOver="this.className='box3'" onMouseOut="this.className='box1'" value="<%=aliasname%>" size=30 maxlength="30"readonly />
	 	</td>
    </tr>
	
		<tr>	
		<td>
		异常处理：
		</td>
		<td >　
	<input  name="tfNACTID" type="text" class="box1" onFocus="this.className='box3'" onBlur="this.className='box1'" onMouseOver="this.className='box3'" onMouseOut="this.className='box1'" value="<%=actname%>" size=30 maxlength="10" readonly/>
	 	</td>
    </tr>
</table>
<table  class="tdnoborder">

    <tr>
      <td  class="tdnoborder">
      <!-- 
	  <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
onMouseOut ="this.className='btn2_mouseout'" >
	  <input type="button" class="btn2" value="返 回" name="B1" onclick="history.go(-1)">
	  </span>
	   -->
	   <mes:button id="B1" reSourceURL="../JarResource/" onclick="func()" value="返 回"/>
	  </td>
    </tr>
</table>
</div></body>
<script>
		
			function func(){
				var pageIndex = <%=intpage%>;
				var int_id='<%=eid%>';				
				window.location.href = 'process_servers_view.jsp?page='+ pageIndex+'&eid='+int_id+ '&process_info=<%=process_info%>';		
			}
	
	</script>
	
</html>





<%
	}catch(Exception e)
	{
		throw e;
	}finally{
		//释放资源
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(conn!=null)conn.close();
	}
%>









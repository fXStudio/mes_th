<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312" %>
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES" />
<%@ page import="mes.framework.*" %>
<%@ page import="mes.framework.forjsp.soa.*" %>
<%@ page import="mes.framework.dao.IDAO_Core" %>
<%@ page import="mes.framework.dao.DAOFactory_Core" %>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>

<html>
<%
	//获取参数
	String processid=request.getParameter("processid");
	String intpage=request.getParameter("intPage");
	String eid=request.getParameter("eid");
	String info = request.getParameter("info");
		info = info==null?"":info;
	
	if(processid==null||processid.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空！");window.location.href='adapter_manage.jsp?page=<%=intpage%>';
 -->
</script>
<%
		return;
	}
	
	Connection conn=null;
	Statement stmt=null;
   	ResultSet rs=null;
   	String sql="";
   	IDAO_Core dao=null;
   	DataServer_SOA ds=null;
   	try
   	{
		//获取连接
   		conn=Conn.getConn();
		dao=DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));;
		ds=new DataServer_SOA(conn);
		
   		stmt=conn.createStatement();
   		
   		String processname=ds.getProcessName_ProcessID(processid);
   		
		sql=dao.getSQL_QueryAdepterInfo(processid);
%>


<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Language" content="zh-cn"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
<meta name="GENERATOR" content="Microsoft FrontPage 4.0"/>
<meta name="ProgId" content="FrontPage.Editor.Document"/>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<div class="title">查看适配器信息</div>
<br/>
<body background="../images/background.jpg">
<div align="center">
<table  class="tbnoborder" border="1">
    <tr>
        <td colspan="6" align="center">流程:<%=processid%>-<%=processname%></td>
	</tr>
	<tr>	
		<td width="370">输入服务</td>
		<td width="90">输入参数</td>
		<td width="60">数据来源</td>
		<td width="70">输出服务</td>
        <td width="95">输出参数</td>
        <td>维护</td>
    </tr>		
<%  
		String i_serveralias="";
		String i_serverid="";
		String i_servername="";
		String i_parameter="";
		String sourceid="";
		String sourcedescription="";
		String o_serveralias="";
		String o_serverid="";
		String o_servername="";
		String o_parameter="";
	
		sql=dao.getSQL_QueryAdepterInfo(processid);
		rs=stmt.executeQuery(sql);
		while(rs.next())
		{
		
			i_serveralias="";
			i_serverid="";
			i_servername="";
			i_parameter="";
			sourceid="";
			sourcedescription="";
			o_serveralias="";
			o_serverid="";
			o_servername="";
			o_parameter="";
			
			i_serveralias=rs.getString(2);
			i_parameter=rs.getString(3);
			sourceid=rs.getString(4);
			o_serveralias=rs.getString(5);
			o_parameter=rs.getString(6);

			i_serverid=ds.getServerID_Processid_ServerAlias(processid,i_serveralias);
			if(i_serverid!=null&&!i_serverid.trim().equals(""))
				i_servername=ds.getServerName_ServerID(i_serverid);
			if(sourceid!=null&&!sourceid.trim().equals(""))	
				sourcedescription=ds.getSourceDescription_SourceID(sourceid);
			if(o_serveralias!=null&&!o_serveralias.trim().equals(""))
				o_serverid=ds.getServerID_Processid_ServerAlias(processid,o_serveralias);
			if(o_serverid!=null&&!o_serverid.trim().equals(""))
				o_servername=ds.getServerName_ServerID(o_serverid);
			
%>
	<tr>
	<td><%=i_serverid%>-<%=i_servername%>(<%=i_serveralias%>)</td>
	<td><%=i_parameter%></td>
	<td><%=sourcedescription%></td>
<% 
	if(sourceid.equals("1")) out.write("<td>&nbsp;&nbsp;</td>");
	else out.write("<td>"+o_serverid+"-"+o_servername+"("+o_serveralias+")</td>");
%>
	<td><%=o_parameter%></td>
	<td><a href='adapter_update.jsp?processid=<%=processid%>&aliasname=<%=i_serveralias%>&parameter=<%=i_parameter%>&eid=<%=eid %>&intPage=<%=intpage %>&info=<%=info%>'>维护</a></td>
	</tr>
<%
		}
		
%>		
</table>

<table class="tdnoborder">
    <tr>
      <td  align=center class="tdnoborder" >
      <!-- 
      <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onMouseOut ="this.className='btn2_mouseout'">
	  <input type="button"class="btn2" value="返 回" name="B1" onclick="history.go(-1)">
	  </span>
	   -->
	  <mes:button id="B1" reSourceURL="../JarResource/" onclick = "func()"  value="返 回"/>
	  </td>
    </tr>    
  </table>

</div>
</body>
<script>
		<!--
			function func(){
				var pageIndex = <%=intpage%>;
				var int_id='<%=eid%>';		
				window.location.href = 'adapter_manage.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';			
			}
		-->
	</script>
</html>

<%
	//释放资源
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (conn != null)
			conn.close();
	}
%>







<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES" />
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.mes.framework.*" %>
<%@ page import="com.qm.mes.framework.forjsp.soa.*" %>
<%@ page import="com.qm.mes.framework.dao.IDAO_Core" %>
<%@ page import="com.qm.mes.framework.dao.DAOFactory_Core" %>
<html>
<%  
	
	/* 
	 * 时间：2007-06-25
	 * 作者：吕智
	 * 业务描述：更新适配器信息
	 */
	//获取请求参数
	String processid=request.getParameter("processid");
	String aliasname=request.getParameter("aliasname");
	String parametername=request.getParameter("parameter");
	String intpage=request.getParameter("intPage");
	String info = request.getParameter("info");
		info = info==null?"":info;
	String eid=request.getParameter("eid");
	
	if(processid==null||processid.trim().equals("")||aliasname==null||aliasname.trim().equals("")||parametername==null||parametername.trim().equals(""))
	{
%>
<script>
<!-- 
alert("参数为空");window.location.href='adapter_manage.jsp';
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
   		dao=DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));
   		
   		ds=new DataServer_SOA(conn);
   		stmt=conn.createStatement();
   		
   		String processname="";
   		String serverid="";
   		String servername="";
   		String source="";
   		String o_aliasname="";
   		String o_parameter="";
   		
   		processname=ds.getProcessName_ProcessID(processid);
   		
   		serverid=ds.getServerID_Processid_ServerAlias(processid,aliasname);
   		
   		servername=ds.getServerName_ServerID(serverid);
   		
   		
   		sql=dao.getSQL_QueryAdapterInfo(processid,aliasname,parametername);
   		rs=stmt.executeQuery(sql);
   		if(rs.next())
   		{
   			source=rs.getString(1);
   			o_aliasname=rs.getString(2);
   			o_parameter=rs.getString(3);
   		}
   		if(rs!=null)rs.close();
   		if(o_aliasname==null)o_aliasname="";
   		
   		String sourceid="";
		String sourcedesc="";
		sql=dao.getSQL_QueryAllSourceInfos();
		rs=stmt.executeQuery(sql);
		java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
		String str_sourcesdesc = "";
		while(rs.next())
		{
			sourceid=rs.getString(1);
			sourcedesc=rs.getString(2);
			map.put(sourcedesc,sourceid);
			if(source.equals(sourceid))
			  str_sourcesdesc = rs.getString(2);
		}
		if(rs!=null)rs.close();
%>

<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<script>
<!-- 
function change()
{
	if(document.all.source.value=='1')
	{
		document.all.o_serveralias.value='';
		document.all.o_serveralias.readOnly=true;
	}else
	{
		document.all.o_serveralias.value='';
		document.all.o_serveralias.readOnly=false;
	}
}
 -->
</script>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<div class="title">更新适配器信息</div>
<body>

<div align=center>

<form method="post" action="adapter_updating.jsp">


<table class="tbnoborder" width="468">
<tr>
<td width="119">流程</td>
<td>
<input type="hidden" name="processid" value="<%=processid%>"  class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'" >
<%=processid%>-<%=processname%>
</td>
</tr>

<tr>
<td>服务</td>
<td>
<input type="hidden" name="i_serveralias" value="<%=aliasname%>" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'" >
<%=serverid%>-<%=servername%>(<%=aliasname%>)
</td>
</tr>

<tr>
<td>参数</td>
<td><input type="hidden" name="i_parameter" value="<%=parametername%>" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'" >
<%=parametername%>
</td>
</tr>

<tr>
<td>数据来源</td>
<td>
<mes:selectbox colorstyle="box_black" id="selectbox1" name="source" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="2" value="<%=str_sourcesdesc %>"/>
</td>
</tr>

<tr>
<td>输出服务</td>
<td>
<mes:inputbox id="o_serveralias" name="o_serveralias" size="36" value="<%=o_aliasname%>" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
</tr>

<tr>
<td>输出参数</td>
<td><mes:inputbox id="o_parameter" name="o_parameter" size="36" value="<%=o_parameter%>" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
</tr>
</table>
<br>
<table class="tbnoborder">
<input type = "hidden" name="intpage" value="<%=intpage%>">
<tr>

<td width="100" class="tdnoborder">
<!-- 
<span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onMouseOut="this.className='btn2_mouseout'" >
<input type="submit" class="btn2" value="提交">
</span>
  -->
<mes:button id="B1" reSourceURL="../JarResource/" value="提交" submit="true"/>
<input type = "hidden" name="info" value="<%=info%>">
<input type = "hidden" name="eid" value="<%=eid%>">
</td>

<td width="100" class="tdnoborder">
<!-- 
<span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onMouseOut="this.className='btn2_mouseout'" >
<input type="reset" class="btn2" value="重置">
</span>
  -->
<mes:button id="B2" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重置" />
</td>

<td width="100" class="tdnoborder">
<!-- 
<span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onMouseOut="this.className='btn2_mouseout'" >
<input type="button" class="btn2" value="返回" onclick="javascript:window.location.href='adapter_manage.jsp'">
</span>
  -->
<mes:button id="B3" reSourceURL="../JarResource/" value="返回" onclick = "func()"  />
</td>

</tr>
</table>
</form>
</div>
</body>

<script type="text/javascript">
function resetPara()
{
	document.getElementsByName("source")[0].value="";
	document.getElementsByName("o_serveralias")[0].value="";
	document.getElementsByName("o_parameter")[0].value="";
	document.getElementsByName("source")[0].focus();

}

function func(){
	var pageIndex = <%=intpage%>;
	var int_id='<%=eid%>';		
	window.location.href = 'adapter_manage.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';
	}
</script>
</html>
<%
	}
	catch(Exception e)
	{
		throw e;
	}
	finally
	{
		//释放资源
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(conn!=null)conn.close();
	}
%>
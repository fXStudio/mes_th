<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
<%@ page import="com.qm.mes.framework.dao.*"%>
<%@ page import="com.qm.mes.framework.DataBaseType"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%
	Connection con=null;
	Statement stmt=null;
	ResultSet rs = null; 
	String intpage=request.getParameter("intPage");	
	String info = request.getParameter("info");
		info = info==null?"":info;    
	try{
	
	con=Conn.getConn();
	stmt=con.createStatement();
//	String user_rolerank=user_rolerank=(String)session.getAttribute("user_rolerank");
%>


<html>
<head>
<link rel="stylesheet" type="text/css"  href="../cssfile/style.css">
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<script>
<!--
function check(thisform)
{  
	
  	if (thisform.functionname.value=='')
   	{
    	alert("请输入功能名称！");
		thisform.functionname.focus();
		return false;
   	} 
   	if(thisform.nodetype.value==3)
   	{
   		if (thisform.url.value=='')
   		{
	    	alert("请输入URL！");
			thisform.url.focus();
			return false;
		}
		
		if(thisform.state.value==2)
		{
			alert("请选择状态!");
			thisform.state.focus();
			return false;
		}
		
		if(thisform.safemarkcode.value=='')
		{
			alert("请输入安全访问标记!");
			thisform.safemarkcode.focus();
			return false;
		}
  	} 
  	if(thisform.nodetype.value=='')
  	{
  		alert("请选择节点类型！");
		thisform.url.focus();
		return false;
  	}	
  	
  	if(thisform.upnodeid.value==0)
  	{
  		alert("请选择上一级节点！");
		thisform.upnodeid.focus();
		return false;
	}
	if(thisform.flo_Order.value.match(/^[\d]{1,5}(\.[\d]{1,3}$|$)/)==null){
		alert("功能顺序号应为Float浮点类型数,如2,12.5!");
	    return false;
	}

  	return true;
}	

function editurl(thisform)
{
	if(thisform.nodetype.value==3)
	{	
		thisform.url.value='';
		thisform.url.disabled=false;
		thisform.safemarkcode.value='';
		thisform.safemarkcode.disabled=false;
		thisform.state.value=1;
		thisform.state.disabled=false;
		thisform.rank.disabled=false;
	}
	else
	{
		thisform.url.value='无';
		thisform.url.disabled=true;
		thisform.safemarkcode.value='无';
		thisform.safemarkcode.disabled=true;
		thisform.state.value=2;
		thisform.state.disabled=true;
		thisform.rank.disabled=true
		thisform.rank.value="1";
		

	}
}
-->	
</script>
</head>
<body onLoad="form1.functionname.focus()">
<div class="title">添加新记录</div>
<br>
<div align="center">
<form name=form1 method=get action="function_adding.jsp" >
  <table class="tbnoborder" border="1">
	<tr>
		<td>
		功能名称：
		</td>
        <td>
		<input type="text" name="functionname" size="50"  maxlength="30" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
		</td>     
    </tr>
    <tr>
		<td>
		级别：
		</td>
        <td >
		<span class="boxOut"><span class="boxIn">
			<select name="rank" disabled>
				<option value=1 selected>应用级</option>
                 <option value=2>开发级</option>
			</select>
		</span></span>
		</td>     
    </tr>
        <tr>
    	<td >
		节点类型
		</td>
		<td >
		<span class="boxOut"><span class="boxIn">
		<select name="nodetype"  onBlur="editurl(form1)"  >
		<option value=2 selected>节点</option>
		<option value=3>叶</option>
		</select>
		</span></span>
		</td>     
    </tr>
    <tr>
    	<td>
		URL：
		</td>
        <td >
		<input type="text" name="url" size="50" disabled value="无" maxlength="100" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
		</td>     
    </tr>

	<tr>
		<td >
		上一级节点
		</td>
		<td >
		<span class="boxOut"><span class="boxIn">
		<select name="upnodeid">
	<%  
	String sql= "";
	//sql="select nfunctionid,cfunctionname from data_function where cnodetype='2' or cnodetype='1' order by nfunctionid" ;
	IDAO_UserManager dao = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
	sql=dao.getSQL_QueryLastNodeForNodeType();
	rs=stmt.executeQuery(sql);
	
	while(rs.next())
	{
		out.write("<option value="+rs.getString(1)+">"+rs.getString(2)+"</option>");
	}
%>
		</select>
		</span></span>
		</td>     
	</tr>
    <tr>
    	<td>
		状态
		</td>
	    <td>
		<span class="boxOut"><span class="boxIn">
		<select name="state" disabled>
		<option value='1' >启用</option>
		<option value='0'>禁用</option>
		</select>
		</span></span>
		</td>	
    </tr>
    <tr>
    	<td>
		安全访问标记
		</td>
		<td>
		<input type="text" name="safemarkcode" maxlength="30" value="无" disabled class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
		</td>
    </tr>
    <tr>
        <td>
		备注
		</td>
		<td>
		<input type="text" name="note" maxlength="100" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
		</td>
    </tr>
     <tr>
        <td>
		功能顺序
		</td>
		<td>
		<input type="text" name="flo_Order" maxlength="100" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
		</td>
    </tr>
</table>
<br>
<table  class="tbnoborder">
<input type = "hidden" name="intpage" value="<%=intpage%>">
    <tr>
		<td width="100" class="tdnoborder" >
   			<mes:button id="button1" reSourceURL="../JarResource/" submit="true" value="添加" onclick="return check(form1)"/>
   		</td>
   
		<td width="100" class="tdnoborder" >
        	<mes:button id="button2" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重置"/>
		</td>

		<td  width="100" class="tdnoborder" >
			<mes:button id="button3" reSourceURL="../JarResource/" value="返回" onclick = "func()" />
		</td>
    </tr>
  </table>
</form>
</div>
</body>

<script type="text/javascript">
function resetPara()
{
	document.getElementsByName("functionname")[0].value="";
	document.getElementsByName("note")[0].value="";
	document.getElementsByName("flo_Order")[0].value="";
	document.getElementsByName("functionname")[0].focus();

}
function func(){
		var pageIndex = <%=intpage%>;
		window.location.href = "function_manage.jsp?page=" + pageIndex+ '&info=<%=info%>';
			}

</script>
</html>
<%
		//释放资源	
		}catch(Exception e)
		{		
			e.printStackTrace();
		}finally {			
			if(stmt!=null)stmt.close();
			if(con!=null)con .close();
			}		
	%>







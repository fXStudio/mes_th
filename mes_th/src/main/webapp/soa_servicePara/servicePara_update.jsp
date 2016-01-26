<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.mes.framework.dao.*"%>
<%@ page import="com.qm.mes.framework.DataBaseType"%>

<html>
<%  
	//获取参数
	
	Connection conn=null;
	Statement stmt=null;
   	ResultSet rs=null;	
    String intpage=request.getParameter("intPage");
 	String serviceid=request.getParameter("serviceid");
    String setparatype= request.getParameter("setparatype");
	String setparaname=request.getParameter("setparaname"); 
	String servicename = request.getParameter("servicename");
 	String aparatype="";
	String paradesc ="";
	String eid = null;
	eid=request.getParameter("eid");
	String info = request.getParameter("info");
		info = info==null?"":info;
	if(serviceid==null||setparatype==null||setparaname==null){
%>
<script type="text/javascript">
	alert("参数为空"); window.location.href="servicePara_manage.jsp";
</script>
<% }
    
    if(setparatype.equals("I"))
		aparatype="O";
	else
		aparatype="I";
	
	//获取连接
   	conn=Conn.getConn();
	if(conn==null)
	{
		out.println("数据库连接失败!");
	}
	else{
   		stmt=conn.createStatement();
	try{
	String sql="";
	//sql="select CDESCRIPTION from PARAMETER_STATEMENT where NSERVERID='"+serviceid+"' and CTYPE='"+setparatype+"' and CPARAMETER='"+setparaname+"'";
	IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));
	sql= dao.getSQL_QueryServicePara(Integer.parseInt(serviceid),setparatype,setparaname);
	rs=stmt.executeQuery(sql);
    java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
    map.put(setparatype,setparatype);
    map.put(aparatype,aparatype);
	if(rs.next())
	{
		paradesc = rs.getString(3);
	}
	else
	{
%>
<script>
<!-- 
alert("没有此参数！");window.location.href='servicePara_manage.jsp';
 -->
</script>
<%
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(conn!=null)conn.close();
	}
	
%>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>
<div class="title">更新服务参数信息</div>
<br>
<div align="center">
<form name="form1" method="get" action="servicePara_updating.jsp">
  <table class="tdnoborder" width="526">
    <tr>	
		<td width="125" >
			服务名：		
		</td>
		<td width="389" >
		<mes:inputbox id="servicename" name="servicename" value="<%=servicename%>" readonly="true" size="36" reSourceURL="../JarResource/" colorstyle="box_black" />      
		</td>
     </tr> 
	<tr>	
		<td width="125" >
			参数名：		
		</td>
		<td width="389" >
		<mes:inputbox id="paraname" name="paraname" value="<%=setparaname%>"  size="36" reSourceURL="../JarResource/" colorstyle="box_black" />      
		</td>
   </tr> 
   <tr>	
		<td  >
		参数类型：		
		</td>
		<td >
		<mes:selectbox colorstyle="box_black" id="selectbox1" name="paratype" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="2" value="<%=setparatype %>"/>
		</td>
    </tr> 
	
	<tr>	
		<td>
		参数描述：
		</td>
		<td>
		<mes:inputbox id="paradesc" name="paradesc" value="<%=paradesc%>" size="36"  reSourceURL="../JarResource/" colorstyle="box_black" />	 	
		</td>
    </tr> 
</table>
	
<table class="tbnoborder">
<input type = "hidden" name="intpage" value="<%=intpage%>">
<input type = "hidden" name="info" value="<%=info%>">
    <tr>
      <td width="111"  class="tdnoborder">
       <!-- 
      <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	 onMouseOut="this.className='btn2_mouseout'" >
        <input class="btn2"  type="button" name="button"  onclick="checkinput()" value="提交"/>
      </span>
        -->
      <mes:button id="button1" reSourceURL="../JarResource/" submit="true" onclick="checkinput()" value="提交"/>
      </td>
	  <td width="109"  class="tdnoborder">
	   <!-- 
	  <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	 onMouseOut="this.className='btn2_mouseout'" >
	    <input class="btn2"  type="button" name="button"  onclick="resetPara()" value="重置"/>
	  </span>
	    -->
	  <mes:button id="button2" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重置"/>
	  </td>
	  <td width="107"  class="tdnoborder">
	   <!-- 
	  <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	 onMouseOut="this.className='btn2_mouseout'" >
	    <input class="btn2"  type="button" name="button2"   onclick="window.location.href='servicePara_manage.jsp'" value="返回"/>
	  </span>
	    -->
	  <mes:button id="button3" reSourceURL="../JarResource/" onclick = "func()" value="返回"/>
	  </td>
    </tr>
  </table>
  <input type="hidden" name="serviceid" value="<%=serviceid%>"/>
  <input type="hidden" name="setparatype" value="<%=setparatype%>"/>
  <input type="hidden" name="setparaname" value="<%=setparaname%>"/>
  <input type="hidden" name="setparadesc" value="<%=paradesc%>"/>
  <input type="hidden" name="eid" value="<%=eid%>"/>
</form>
</div></body>


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
}
%>

<script type="text/javascript">
function checkinput()
{
	var setparatype = document.getElementsByName("setparatype")[0].value;
	var setparaname = document.getElementsByName("setparaname")[0].value;
	var paradesc = document.getElementsByName("setparadesc")[0].value;
	
	if(form1.paraname.value==""||form1.paratype.value==""||form1.paradesc.value=="")
	{
		alert("参数为空");
	}
	else{
		if((form1.paraname.value==setparaname)&&(form1.paratype.value==setparatype)&&(form1.paradesc.value==paradesc))
		{
			alert("该参数信息未更新");
		}
		else{
			form1.submit();
		}
	}
	


}
function resetPara()
{
	document.getElementsByName("paraname")[0].value="";
	document.getElementsByName("paratype")[0].value="I";//默认为参数类型为“输入”
	document.getElementsByName("paradesc")[0].value="";
	document.getElementsByName("paraname")[0].focus();
}
function func(){
	var pageIndex = <%=intpage%>;
	var int_id='<%=eid%>';		
	window.location.href = 'servicePara_manage.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';
	}
</script>
</html>

<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.mes.framework.dao.*"%>
<%@ page import="com.qm.mes.framework.DataBaseType"%>

<%    
	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0);
	String intpage=request.getParameter("intPage");	   
    Connection con = null;
	Statement stmt= null;
	ResultSet rs= null;
	String sql="";
	String info = request.getParameter("info");
		info = info==null?"":info;
	
	try{	
    	con = Conn.getConn();
		if(con==null)
			out.println("数据库连接失败.");
		else
		{
			stmt=con.createStatement();
			IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(con));
			sql=dao.getSQL_QueryAllNameSpaces("","");
			rs=stmt.executeQuery(sql);
			java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
			while(rs.next())
			{
				map.put(rs.getString(2),String.valueOf(rs.getInt(1)));
			}
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>服务定义</title>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>
<div align="center"><strong>添加服务</strong></div>
<br/>
<div align="center">
<form name="form1" method="get" action="service_adding.jsp" >
  <table class="tbnoborder" width="468">
    <tr>
      <td width="130">服务名：</td>
     <td>
     <mes:inputbox name="servicename" size="36" id="servicename" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
    </tr>
    <tr>
      <td>对应类名：</td>
      <td>
      <mes:inputbox name="classname"  size="36" maxlength="100" id="classname" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
    </tr>
    <tr>
      <td>业务描述：</td>
      <td>
      <mes:inputbox name="servicedesc" size="36" id="servicedesc" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
    </tr>
    <tr>
      <td>命名空间：</td>
      <td>
      <mes:selectbox colorstyle="box_black" id="selectbox1" name="namespace" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value=""/>
	  </td>
	 
    </tr>
	</table>
    <br>
    <table class="tbnoborder">
    <input type = "hidden" name="intpage" value="<%=intpage%>">
	<tr>
      <td class="tdnoborder" width="111" >
          <mes:button id="button3" reSourceURL="../JarResource/" value="提  交" submit="true"/>
      </td>
	  <td  class="tdnoborder" width="109" >
      <mes:button id="button1" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重置"/>
      </td>
	  <td class="tdnoborder" width="107" >
	   <mes:button id="button2" reSourceURL="../JarResource/" onclick = "func()" value="返回"/>
	  </td>
	  </tr>
  </table>
</form>
</div>
</body>

<script type="text/javascript">
function resetPara()
{
	document.getElementsByName("servicename")[0].value="";
	document.getElementsByName("classname")[0].value="";
	document.getElementsByName("servicedesc")[0].value="";
	document.getElementsByName("namespace")[0].value="";
	document.getElementsByName("servicename")[0].focus();

}
function func(){
		var pageIndex = <%=intpage%>;		
		window.location.href = "service_manage.jsp?page=" + pageIndex+ '&info=<%=info%>';		
			}

</script>
</html>
<%		
		}
	}catch(Exception e)
	{
		out.println("查询失败"+e);
		throw e;
	}finally{
		//释放资源
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(con!=null)con.close();
	}
%>
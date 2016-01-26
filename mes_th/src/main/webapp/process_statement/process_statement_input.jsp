<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page import="com.qm.mes.framework.*" %>
<%@page import="com.qm.mes.framework.dao.*" %>
<%  
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	String sql="";
	String intpage=request.getParameter("intPage");	 
	String process_info = request.getParameter("process_info");
	process_info = process_info==null?"":new String(process_info);  
	try{
	//获取连接
   	conn=Conn.getConn();
   	stmt=conn.createStatement();
	
	//sql="select NNAMESPACEID,CNAMESPACE from namespace_statement where NNAMESPACEID not in (select NNAMESPACEID from process_statement where nnamespaceid<>'')"; 
	IDAO_Core sqlDao = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));
	sql=sqlDao.getSQL_QueryNameSpaceForProcessStatement();
	rs=stmt.executeQuery(sql);
	java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
	while (rs.next())
	{
	map.put(rs.getString(2),String.valueOf(rs.getInt(1)));
	}
%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
<title>添加新记录</title>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>
<form name=form1 method=get action="process_statement_inputing.jsp">
<div class="title">添加业务流程定义</div>
<br>
<div align="center">

  <table class="tbnoborder" >


	<tr>	
		<td width="119"  >
		流程名：
		  <input type="hidden" name="tfNPROCESSID" value="0"/>
	  </td>
		<td width="413" >　
	<mes:inputbox  name="tfCPROCESSNAME" size="36" maxlength="30" id="tfCPROCESSNAME" reSourceURL="../JarResource/" colorstyle="box_black" />
 	  </td>

    </tr> 
	
	<tr>	
		<td  >
		业务描述：
		</td>
		<td >　
	<mes:inputbox  name="tfCDESCRIPTION" size="36" maxlength="100" id="tfCDESCRIPTION" reSourceURL="../JarResource/" colorstyle="box_black" />
	 	</td>

    </tr> 
	
	<tr>	
		<td  >
		命名空间：
		</td>
		<td >　
    <mes:selectbox colorstyle="box_black" id="selectbox1" name="tfCNAMESPACE" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value=""/>
	 	</td>
    </tr>
</table>
<br>
<table  class="tdnoborder">	 
    <input type = "hidden" name="intpage" value="<%=intpage%>">
    <tr>
      <td width="100" class="tdnoborder">
      <!-- 
	   <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onmouseout="this.className='btn2_mouseout'" >
         <input class="btn2" type="submit" value="提  交" name="B1" >
       </span>
       -->
       <mes:button id="B1" reSourceURL="../JarResource/" submit="true" value="提  交"/>
		</td>
		<td width="100" class="tdnoborder">		
		<!-- 
         <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onmouseout="this.className='btn2_mouseout'" >
           <input class="btn2" type="reset" value="重  置" name="B2" >
         </span>
        -->
        <mes:button id="B2" reSourceURL="../JarResource/" submit="false"  onclick="resetPara()" value="重  置"/>
	  </td>
      <td width="100" class="tdnoborder">
      <!-- 
	  <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onmouseout="this.className='btn2_mouseout'" >
          <input class="btn2" type="button" value="返 回" name="B3" onclick="window.location.href='process_statement_view.jsp'" >
      </span>
       -->
       <mes:button id="B3" reSourceURL="../JarResource/"  onclick = "func()"   value="返 回"/>
	  </td>
    </tr>
  </table>
</div></form>
</body>

<script type="text/javascript">
function resetPara()
{
	document.getElementsByName("tfCPROCESSNAME")[0].value="";
	document.getElementsByName("tfCDESCRIPTION")[0].value="";
	document.getElementsByName("tfCNAMESPACE")[0].value="";
    document.getElementsByName("tfCPROCESSNAME")[0].focus();
}
function func(){
		var pageIndex = <%=intpage%>;
		window.location.href = "process_statement_view.jsp?page=" + pageIndex+ '&process_info=<%=process_info%>';		
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

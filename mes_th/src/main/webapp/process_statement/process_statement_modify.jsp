<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page import="com.qm.mes.framework.*" %>
<%@page import="com.qm.mes.framework.dao.*" %>
<%
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	String strSql="";
    String intpage=request.getParameter("intPage");
	String processid=request.getParameter("processid");
	String processname="";
	String description="";
	String namespace="";
	String cnamespace="";
	String nnamespaceid=request.getParameter("nnamespaceid");
	String process_info = request.getParameter("process_info");
	process_info = process_info==null?"":new String(process_info);
	
	conn=Conn.getConn();
	if (conn==null)
	{
		out.println("数据库连接失败!");
	}
	else
	{
		IDAO_Core sqlDao = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));
		//strSql="select NPROCESSID,CPROCESSNAME,CDESCRIPTION,CNAMESPACE from PROCESS_STATEMENT where NPROCESSID='"+processid+"'";
		strSql=sqlDao.getSQL_QueryProcessStatementInfo(processid,nnamespaceid);
		
		try
		{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(strSql);
			if(rs.next())
			{
				processname=rs.getString(2);
				description=rs.getString(3);
				if (nnamespaceid.equals("null")){
		           cnamespace="";
		        } else{
		           cnamespace=rs.getString(4);
		        }
			}
			else
			{
				out.println("无此流程号: "+ processid);
			}
			strSql="select nnamespaceid from namespace_statement where cnamespace='"+cnamespace+"'";
			stmt=conn.createStatement();
			rs=stmt.executeQuery(strSql);
			if(rs.next()){
				namespace=rs.getString(1);
				}
             //strSql="select NNAMESPACEID,CNAMESPACE from namespace_statement where NNAMESPACEID not in (select NNAMESPACEID from process_statement where nnamespaceid<>'')"; 
	        strSql=sqlDao.getSQL_QueryNameSpaceForProcessStatement(nnamespaceid);
	        stmt=conn.createStatement();
	        rs=stmt.executeQuery(strSql);	
	        java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
	        map.put(cnamespace,namespace);
	        while(rs.next())
	        {
	          map.put(rs.getString(2),String.valueOf(rs.getInt(1)));
	        }
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
<title>添加新记录</title>
<script language="javascript">
function checkForm(formname)
{
	if(formname.tfNPROCESSID.value=="")
	{
		alert("请输入流程号！")
		formname.tfNPROCESSID.focus()
		return false 
	}
	if(formname.tfCPROCESSNAME.value=="")
	{
		alert("请输入流程名！")
		formname.tfCPROCESSNAME.focus()
		return false
	}
	if(formname.tfCDESCRIPTION.value=="")
	{
		alert("请输入业务描述!")
		formname.tfCDESCRIPTION.focus()
		return false
	}
	return true
}
</script>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>

<body background="../images/background.jpg" >
<form name=form1 method=get action="process_statement_modifying.jsp" onSubmit="return   checkForm(this)">
<div class="title">更改流程定义信息</div>
<br>
<div align="center">

  <table class="tbnoborder">
	<tr>	
		<td width="119" >
		流程号：
		</td>
		<td  width="413">　
	<mes:inputbox  name="tfNPROCESSID" size="36" maxlength="10" id="tfNPROCESSID" reSourceURL="../JarResource/" colorstyle="box_black" readonly="true" value="<%=processid%>" />
	 	</td>
    </tr> 

	<tr>	
		<td  >
		流程名：
		</td>
		<td >　
	<mes:inputbox  name="tfCPROCESSNAME" id="tfCPROCESSNAME"  value="<%=processname%>" size="36" maxlength="30"  reSourceURL="../JarResource/" colorstyle="box_black"/>
	 	</td>

    </tr> 
	
	<tr>	
		<td  >
		业务描述：
		</td>
		<td >　
	<mes:inputbox  name="tfCDESCRIPTION" id="tfCDESCRIPTION"  value="<%=description%>" size="36" maxlength="100" reSourceURL="../JarResource/" colorstyle="box_black"/>
	 	</td>
    </tr> 
	
	<tr>	
		<td  >
		命名空间：
		</td>
		<td >　
		<mes:selectbox colorstyle="box_black" id="selectbox1" name="tfCNAMESPACE" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value="<%=cnamespace %>"/>
	 	</td>
    </tr>
</table>
<br>
<table  class="tdnoborder">
<input type = "hidden" name="intpage" value="<%=intpage%>">
<input type = "hidden" name="process_info" value="<%=process_info%>">
    <tr>
      <td width="100" class="tdnoborder">
      <!-- 
	   <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onmouseout="this.className='btn2_mouseout'" >
        <input class="btn2" type="submit" value="提  交" name="B1" >
       </span>
       -->
       <mes:button id="B1" reSourceURL="../JarResource/" submit="true" value="提  交"/>
	  <td width="100"class="tdnoborder">
	  <!-- 
       <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onmouseout="this.className='btn2_mouseout'" >
        <input class="btn2" type="reset" value="重  置" name="B2" >
       </span>
       -->
       <mes:button id="B2" reSourceURL="../JarResource/" submit="false" onclick="resetPara()" value="重  置"/>
	  </td>
      <td  width="100" class="tdnoborder">
      <!-- 
	   <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onmouseout="this.className='btn2_mouseout'" >
        <input class="btn2" type="button" value="返 回" name="B3" onclick="window.location.href='process_statement_view.jsp'" >
       </span>
       -->
       <mes:button id="B3" reSourceURL="../JarResource/" onclick = "func()"  value="返 回"/>
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
	var int_id=<%=processid%>;		
	window.location.href = 'process_statement_view.jsp?page='+ pageIndex+'&eid='+int_id+ '&process_info=<%=process_info%>';
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
	}
 %>




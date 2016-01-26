<%@ page language="java" import="java.sql.*,com.qm.mes.beans.Process" contentType="text/html;charset=gb2312"%>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="com.qm.mes.beans.Act_Statement"/>
<jsp:directive.page import="com.qm.mes.beans.*"/>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%@page import="com.qm.mes.framework.*" %>
<%@page import="com.qm.mes.framework.dao.*" %>

<%
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	String strSql="";
    String intpage=request.getParameter("intPage");
    String eid=request.getParameter("eid");
	String processid=request.getParameter("processid");
	String sid=request.getParameter("sid");
	String serverid="";
	String aliasname="";
	String actid="";
	String actname="";
	String serverName="";
	String processName="";
	String process_info = request.getParameter("process_info");
	process_info = process_info==null?"":new String(process_info);
	List<Act_Statement> list=new ArrayList<Act_Statement>();
	try
		{
	    conn=Conn.getConn();
		IDAO_Core sqlDao = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));
		//strSql="select NPROCESSID,NSID,NSERVERID,CALIASNAME,NACTID from PROCESS_SERVERS where NPROCESSID='"+processid+"' and NSID='"+sid+"'";
		strSql=sqlDao.getSQL_QueryProcessServerInfoForProcessidAndSid(processid,sid);
		
			stmt=conn.createStatement();
			rs=stmt.executeQuery(strSql);
			if(rs.next())
			{
				sid=String.valueOf(rs.getInt(2));
				serverid=String.valueOf(rs.getInt(3));
				aliasname=rs.getString(4);
				actid=String.valueOf(rs.getInt(5));

			}
			else
			{
				out.println("无此流程号: "+ processid);
			}
			java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
			rs=stmt.executeQuery(sqlDao.getSQL_QueryActStatement());
			 while(rs.next()){
               Act_Statement as=new Act_Statement();
               as.setNactid(rs.getString(1));
               as.setCdescription(rs.getString(2));
               if(as.getNactid().equals(actid)){
                  actname=as.getCdescription();
                  map.put(actname,actid);
               }else{
                  map.put(as.getCdescription(),as.getNactid());
               }
             }
             
             String sqlService=sqlDao.getSQL_QueryAllServices(null,null);
             java.util.HashMap<Comparable,String> map1 = new java.util.HashMap<Comparable,String>();
             rs=stmt.executeQuery(sqlService);
             while(rs.next()){
               Service ser=new Service();
               ser.setId(rs.getString(1));
               ser.setName(rs.getString(2));
               if(ser.getId().equals(serverid)){
                  serverName=ser.getName();
                  map1.put(serverName,serverid);
               }else{
                  map1.put(ser.getName(),ser.getId());
               }
            }
            String sqlProcess=sqlDao.getSQL_QueryAllProcessStatementIds(null,null);
            java.util.HashMap<Comparable,String> map2 = new java.util.HashMap<Comparable,String>();
            rs=stmt.executeQuery(sqlProcess);
            while(rs.next()){
               Process pro=new Process();
               pro.setId(rs.getString(1));
               pro.setName(rs.getString(2));
               if(pro.getId().equals(processid)){
                  processName=pro.getName();
                  map2.put(processName,processid);
               }else{
               map2.put(pro.getName(),pro.getId());
               }
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
		formname.tfNPRCESSID.focus()
		return false 
	}
	if(formname.tfNSID.value=="")
	{
		alert("请输入运行顺序号！")
		formname.tfNSID.focus()
		return false
	}
	if(formname.tfCSERVERID.value=="")
	{
		alert("请输入服务号!")
		formname.tfCSERVERID.focus()
		return false
	}
	if(formname.tfCALIASNAME.value=="")
	{
		alert("请输入服务别名!")
		formname.tfCALIASNAME.focus()
		return false
	}
	if(formname.tfNACTID.value=="")
	{
		alert("请输入异常处理!")
		formname.tfNACTID.focus()
		return false
	}
	if(isNaN(formname.tfCSERVERID.value))
	{
		alert("服务号应该为数值")
		return false
	}
	if(isNaN(formname.tfNACTID.value))
	{
		alert("异常处理应该为数值")
		return false
	}
}
</script>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>

<body>
<form name=form1 method=post action="process_servers_modifying.jsp" onSubmit="return   checkForm(this)">
<div class="title">更新业务流程服务</div>
<br>
<div align="center">

  <table class="tbnoborder" width="560" >
	<tr>	
	<td >
		流程名：
		</td>
		<td >　
		<mes:selectbox colorstyle="box_black" id="selectbox1" name="tfNPROCESSID" map="<%=map2%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value="<%=processName %>"/>
		</td>
    </tr> 

	<tr>	
		<td  >
		运行顺序号：
		</td>
		<td >　
	    <mes:inputbox  name="tfNSID" id="tfNSID"  value="<%=sid%>" size="36" maxlength="10" readonly="true" reSourceURL="../JarResource/" colorstyle="box_black" />
	 	</td>

    </tr> 
	
	<tr>	
	<td  >
		服务名：
		</td>
		<td >　
		<mes:selectbox colorstyle="box_black" id="selectbox2" name="tfCSERVERID" map="<%=map1%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value="<%=serverName %>"/>
		</td>
    </tr> 
	
	<tr>	
		<td  >
		服务别名：
		</td>
		<td >　
	    <mes:inputbox  name="tfCALIASNAME" id="tfCALIASNAME" reSourceURL="../JarResource/" colorstyle="box_black" value="<%=aliasname%>" size="36" maxlength="30" />
	 	</td>
    </tr>
	 
	<tr>	
		<td  >
		异常处理：
		</td>
		<td >　
		<mes:selectbox colorstyle="box_black" id="selectbox3" name="tfNACTID" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="3" value="<%=actname %>"/>
	 	</td>

    </tr> 

</table>
<table  class="tdnoborder">
<input type = "hidden" name="intpage" value="<%=intpage%>">
<input type = "hidden" name="process_info" value="<%=process_info%>">
<input type = "hidden" name="eid" value="<%=eid%>">
    <tr>
      <td width="100" class="tdnoborder">
      <!-- 
	   <span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'" onmouseout="this.className='btn2_mouseout'" >
         <input class="btn2" type="submit" value="提  交" name="B1" >
       </span>
        -->
       <mes:button id="B1" reSourceURL="../JarResource/" submit="true" value="提  交"/>
      </td>
	  <td  width="100" class="tdnoborder">
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
         <input class="btn2" type="button" value="返 回" name="B3" onclick="window.location.href='process_servers_view.jsp'" >
      </span>
       -->
      <mes:button id="B3" reSourceURL="../JarResource/" onclick = "func()"   value="返 回"/>
	  </td>
    </tr>
  </table>
</div></form>
</body>

<script type="text/javascript">
function resetPara()
{
	document.getElementsByName("tfNPROCESSID")[0].value="";
	document.getElementsByName("tfNSID")[0].value="";
	document.getElementsByName("tfCSERVERID")[0].value="";
	document.getElementsByName("tfCALIASNAME")[0].value="";
	document.getElementsByName("tfNACTID")[0].value="";
	document.getElementsByName("tfNPROCESSID")[0].focus();
}
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







<%@ page language="java" import="java.sql.*,java.util.List,java.util.ArrayList" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<%@page import="mes.framework.*" %>
<%@page import="mes.framework.dao.*" %>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%  
	//获取参数
	
	Connection conn=null;
	Statement stmt=null;
   	ResultSet rs=null;	
    String intpage=request.getParameter("intPage");
 	String processid=request.getParameter("processid");
	String processname="";
	String description="";
	String namespace="";
	String nnamespaceid=request.getParameter("nnamespaceid");
	String serviceid="";
	String process_info = request.getParameter("process_info");
	process_info = process_info==null?"":new String(process_info);
   	try{
	//获取连接
   	conn=Conn.getConn();
   	stmt=conn.createStatement();

%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Language" content="zh-cn"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
<meta name="GENERATOR" content="Microsoft FrontPage 4.0"/>
<meta name="ProgId" content="FrontPage.Editor.Document"/>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<body>

<%
		IDAO_Core sqlDao = DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));
	String strSql="";

	//strSql="select a.NPROCESSID,a.CPROCESSNAME,a.CDESCRIPTION,b.CNAMESPACE from PROCESS_STATEMENT a ,namespace_statement b where a.NPROCESSID='"+processid+"' and a.nnamespaceid=b.nnamespaceid";
	//if (nnamespaceid == null && (nnamespaceid.equals(""))){
	//strSql=sqlDao.getSQL_QueryProcessStatementInfo(processid,null);
	//}else{
	strSql=sqlDao.getSQL_QueryProcessStatementInfo(processid,nnamespaceid);
	//}
	rs=stmt.executeQuery(strSql);


	if(rs.next())
	{
		processname=rs.getString(2);
		description=rs.getString(3);
		if (nnamespaceid.equals("null")){
			namespace="";
		}
		else{
			namespace=rs.getString(4);
		}
	}
	String sql=sqlDao.getSQL_QueryProcessItem(processid);
    	List<String> list=new ArrayList<String>();
	    rs=stmt.executeQuery(sql);
	    while(rs.next()){
	    serviceid=rs.getString(3);
	    list.add(serviceid);
	    }
%>

<div class="title">查看流程定义信息</div>
<br>
<div align="center">

  <table class="tdnoborder" width="560">
	<tr>	
		<td width="180" >
		流程号：		</td>
		<td width="368" >　
	<input  name="tfNPROCESSID" type="text" class="box1" onFocus="this.className='box3'" onBlur="this.className='box1'" onMouseOver="this.className='box3'" onMouseOut="this.className='box1'" value="<%=processid%>"  size=30 maxlength="10" readonly/>
 	  </td>
    </tr> 

	<tr>	
		<td  >
		流程名：
		</td>
		<td >　
	<input  name="tfCPROCESSNAME" type="text" class="box1" onFocus="this.className='box3'" onBlur="this.className='box1'" onMouseOver="this.className='box3'" onMouseOut="this.className='box1'"  value="<%=processname%>" size=30 maxlength="30" readonly/>
	 	</td>

    </tr> 
	
	<tr>	
		<td  >
		业务描述：
		</td>
		<td >　
	<input  name="tfCDESCRIPTION" type="text" class="box1" onFocus="this.className='box3'" onBlur="this.className='box1'" onMouseOver="this.className='box3'" onMouseOut="this.className='box1'" value="<%=description%>" size=30 maxlength="100" readonly/>
	 	</td>
    </tr> 
	
	<tr>	
		<td  >
		命名空间：
		</td>
		<td >　
	<input  name="tfCNAMESPACE" type="text" class="box1" onFocus="this.className='box3'" onBlur="this.className='box1'" onMouseOver="this.className='box3'" onMouseOut="this.className='box1'" value="<%=namespace%>" size=30 maxlength="30" readonly/>
	 	</td>
    </tr>

  </table>
  <br/>
  <table class="tdnoborder" width="560">
   <%
    	

	    for(int i=0;i<list.size();i++){
        	sql=sqlDao.getSQL_QueryServiceForServiceid(Integer.parseInt(list.get(i)));	
    	    rs=stmt.executeQuery(sql);
	        while (rs.next())
	        {
	        %>
   <tr>
   <td width="180">拥有服务名：&nbsp;&nbsp;<%=i+1 %></td>
	<td>
	<a href="#" onClick="window.location.href='../soa_service/service_manage.jsp?info=<%=rs.getInt(1) %>&method=ById';return false;"><%=rs.getString(2) %></a>   
    </td>
   </tr>
     <%
	        }
	    }
     %>
  </table>
  <br>
  <table  class="tdnoborder">
    <tr>
      <td class="tdnoborder">
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
		<!--
			function func(){
				var pageIndex = <%=intpage%>;
				var int_id=<%=processid%>;		
				window.location.href = 'process_statement_view.jsp?page='+ pageIndex+'&eid='+int_id + '&process_info=<%=process_info%>';		
			}
		-->
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








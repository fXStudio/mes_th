<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*" %>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html><!-- InstanceBegin template="/Templates/查看摸板1.dwt.jsp" codeOutsideHTMLIsLocked="true" --> 
 <!-- InstanceBeginEditable name="获得连接" -->
<%@page import="com.qm.mes.util.tree.*"%>
 <jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
 
  <%@page import="com.qm.mes.framework.dao.*,com.qm.mes.framework.DataBaseType"%>
  <!-- InstanceEndEditable -->
 <!-- InstanceBeginEditable name="获得参数并验证" --><%
 	String roleno = request.getParameter("roleno");
 	String intpage=request.getParameter("intPage");
 	String info = request.getParameter("info");
		info = info==null?"":info;
	if(roleno==null){
		out.println("<script>alert(\"参数为空\"); window.location.href=\"role_manage.jsp\";</script>");
		return;
	} %> <!-- InstanceEndEditable -->
	<%String userid=(String)session.getAttribute("userid");
	Map<Comparable,String> map = new HashMap<Comparable,String>();
	Connection conn=null;
  	try{//获取连接zzzzz
		conn=Conn.getConn();
		Statement stmt = conn.createStatement();
		String sql="";
		%><!-- InstanceBeginEditable name="设置SQL语句" --><%
	sql = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(conn)).getSQL_QueryRoleAndUser(roleno);
%><!-- InstanceEndEditable --><%
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()){
		%><!-- InstanceBeginEditable name="获得查询结果" --><%
			map.put("roleno",rs.getString("nroleno"));
			map.put("rolename",rs.getString("crolename"));
			map.put("rank",rs.getString("crank"));
			map.put("welcomepage",rs.getString("cwelcomepage"));
			map.put("note",rs.getString("cnote"));
		%><!-- InstanceEndEditable --><%
		}else{
			out.println("<script>alert(\"没有查询结果！\");window.location.href='service_manage.jsp';</script>");
			if(conn!=null)conn.close();
			return;
		}stmt.close();
		Connection con = null;
	    BuildTree bct=null;
	    try{
		  con = Conn.getConn();
		  bct = new BuildTree( con, userid,"update",roleno);
	    }catch(SQLException sqle){
		  System.out.println(sqle);
    	}finally{
		  if(con!=null)con.close();
	    }
	    String no = map.get("roleno");
	    java.util.HashMap<Comparable,String> map1 = new java.util.HashMap<Comparable,String>();
		map1.put("开发级","0");
		map1.put("应用级","1");
		String rolerank = map.get("rank").equals("0")?"开发级":"应用级";
	    %>
	<head>
		<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<!-- InstanceBeginEditable name="标题" -->
		<title>更新角色定义信息</title>
<!-- InstanceEndEditable -->
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
	</head>
	<body>
	<div class="title"><!-- InstanceBeginEditable name="标题2" -->更新角色定义信息<!-- InstanceEndEditable --></div>
	<br>
	<div align="center"><!-- InstanceBeginEditable name="内容1" -->
	
	<form id="form1" action="role_updating.jsp" method="get" onSubmit="return checkinput();">
    <table width="468" class="tbnoborder" border="1">
    <input type = "hidden" name="info" value="<%=info%>">
		<tr>
        <td align="center" width="145">角色号：</td>
        <td width="311">
        <mes:inputbox readonly="true" size="36" id="roleno" name="roleno" reSourceURL="../JarResource/" colorstyle="box_black"  value="<%=no%>"/>
        </td>
      </tr>
	<tr>
        <td align="center" width="145">角色名：</td>
        <td width="311">
        <mes:inputbox name="rolename" id="rolename"  size="36" maxlength="30" reSourceURL="../JarResource/" colorstyle="box_black"/>
      </td>
      </tr>
	  <%if(((String)session.getAttribute("user_rolerank")).equals("1")){%>
	 	 <input type="hidden" name="rank" value="1"/>
	  <%}else{%>
	<tr>
        <td align="center" width="145">级别：</td>
        <td width="311">
        <mes:selectbox colorstyle="box_black" id="selectbox1" name="rank" map="<%=map1%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="2" value="<%=rolerank %>"/>
      </td></tr><%}%>
	<tr>
        <td align="center" width="145">权限：</td>
        <td width="311" style="padding:20px 20px 20px 50px ">
			<%=bct.getHtmlCode()%>
      </td>
      </tr>
	<tr>
        <td align="center" width="145">首页：</td>
        <td width="311"><input name="welcomepage" size="48" type="text"  class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'" maxlength="100"onkeyup="this.value=this.value.replace(/\b[\d]*/,'');"  onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/\b[\d]*/,''))">
      </td>
      </tr>
	<tr>
        <td align="center" width="145">描述：</td>
        <td width="311">
        <mes:inputbox name="note" id="note" size="36"  maxlength="100" reSourceURL="../JarResource/" colorstyle="box_black" />
      </td>
      </tr>
    </table>
    <table class="tbnoborder">
    <input type = "hidden" name="intpage" value="<%=intpage%>">
      <tr>
	 	 <td width="111"  class="tdnoborder">
			<mes:button id="button1" reSourceURL="../JarResource/" submit="true" value="提交"/>
		</td>
		<td width="109"  class="tdnoborder">
      		<mes:button id="button2" reSourceURL="../JarResource/" submit="false"  onclick="resetPara()" value="重置"/>
	  	</td>
        <td  class="tdnoborder" colspan="2"  >
        <mes:button id="button3" reSourceURL="../JarResource/"  value="返 回"  onclick="func()"/>
        </td>
      </tr>
    </table>
	</form>
<script type="text/javascript">
var re = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/; 
function checkinput(){
	if(!re.test(document.getElementById("rolename").value))
    {
	    alert("角色名应由字母、数字和汉字组成!");
	    return false;
	}
	if(!re.test(document.getElementById("note").value))
	{
		alert("描述应由字母、数字和汉字组成!");
		return false;
	}
	var rolename = document.getElementsByName("rolename")[0];
	var welcomepage = document.getElementsByName("welcomepage")[0];
	var note = document.getElementsByName("note")[0];
	if(rolename.value==""||welcomepage.value==""||note.value=="")	{
		alert("缺少参数!");
		return false;
	}
	else
		return true;
}

function resetPara(){
//alert('1');
	var rolename = document.getElementsByName("rolename")[0];
	var rank = document.getElementsByName("rank")[0];
	var welcomepage = document.getElementsByName("welcomepage")[0];
	var note = document.getElementsByName("note")[0];
	
	rolename.value="<%=map.get("rolename")%>"
	rank.value="<%=map.get("rank")%>"
	welcomepage.value="<%=map.get("welcomepage")%>"
	note.value="<%=map.get("note")%>"
	rank.selectedIndex = "<%=map.get("rank")%>";

	rolename.focus();
	
}resetPara();
function func(){
	var pageIndex = <%=intpage%>;
	var int_id=<%=roleno%>;		
	window.location.href = 'role_manage.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';
	}
</script>
	<!-- InstanceEndEditable --></div>
</body><!-- InstanceEnd --></html>
<%	
	}catch(Exception e)
	{
		throw e;
	}finally{
		//释放资源
		if(conn!=null)conn.close();
	}
%>










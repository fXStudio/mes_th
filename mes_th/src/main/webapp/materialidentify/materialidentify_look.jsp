<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="com.qm.mes.system.factory.*" %>
<%@page import="com.qm.mes.system.elements.*" %>
<html>
 <jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
 <%
 	String element_name = request.getParameter("element_name");
	if(element_name==null){
		out.println("<script>alert(\"参数为空\"); window.location.href=\"materialidentify_view.jsp\";</script>");
		return;
	} %>
	<%
	Connection conn=null;
	Statement stmt=null;
  	try{//获取连接
		conn=Conn.getConn();
		stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		IMaterialidentifyFactory factory;
		factory = (IMaterialidentifyFactory) FactoryAdapter.getFactoryInstance(IMaterialidentifyFactory.class.getName());
		%>
		<%
		IMaterialidentify midentify=factory.queryElement(new Integer(element_name),conn);
        %>
		  <%
		if(midentify==null){
			out.println("<script>alert(\"没有查询结果！\");window.location.href='materialidentify_view.jsp';</script>");
		}
		%>
	<head>
		<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
		<title>物料类型信息</title>
	</head>
	<body>
	<div class="title">查看物料类型信息</div>
	<br>
	<div align="center">
    <table class="tbnoborder">
      <tr>
        <td width="180">元素号 - 物料标识名：</td>
        <td width="266"><input readonly size="50" type="text" value="<%=midentify.getId()%> - <%=midentify.getName() %>" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
        </td>
      </tr>
       <tr>
        <td>描述信息：</td>
        <td><input readonly size="50" type="text" value="<%=midentify.getDescription()==null?"":midentify.getDescription() %>" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
        </td>
      </tr>
       <tr>
        <td>修改时间：</td>
        <td><input readonly size="50" type="text" value="<%=midentify.getUpdateDateTime() %>" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
        </td>
      </tr>
       <tr>
        <td>操作用户号：</td>
        <td><input readonly size="50" type="text" value="<%=midentify.getUpdateUserId() %>" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
        </td>
      </tr>
       <tr>
        <td>物料标识长度：</td>
        <td><input readonly size="50" type="text" value="<%=midentify.getCodeLength() %>" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
        </td>
      </tr>
      <tr>
        <td>版本号：</td>
        <td><input readonly size="50" type="text" value="<%=midentify.getVersion() %>" class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'">
        </td>
      </tr>
    </table>
    <table class="tbnoborder">
      <tr>
        <td  class="tdnoborder" colspan="2"  ><span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	onMouseOut ="this.className='btn2_mouseout'">
          <input class="btn2" type="button"  value="返 回"  onClick="history.go(-1)">
        </span></td>
      </tr>
    </table>
	</div>
</body>
</html>
<%	//释放资源
	if(stmt!=null)stmt.close();
	if(conn!=null)conn.close();
	}catch(Exception e)
	{
		if(conn!=null)conn.close();
		throw e;
	}
%>









